(ns immutant-fressian.entity
  (:require [datomic.api :as d]
            [org.fressian.clojure :as fr]
            [immutant.messaging :as msg])
  (:import [org.fressian.handlers WriteHandler ReadHandler]))

;; Set the same connection in every peer
(defonce *connection* nil)
(defn set-datomic-connection [conn]
  (alter-var-root #'*connection* (fn [old] conn)))

(defn datomic-handlers []
  [[datomic.db.Db "db"
    (reify WriteHandler
      (write [_ w db]
        (let [t (or (d/as-of-t db) (d/basis-t db))]
          (.writeTag w "db" 1)
          (.writeInt w t))))
    (reify ReadHandler
      (read [_ rdr tag component-count]
        (let [t (.readInt rdr)]
          (d/as-of @(d/sync *connection* t) t))))]
   [datomic.query.EntityMap "ent"
    (reify WriteHandler
      (write [_ w e]
        (let [db (d/entity-db e)
              id (:db/id e)]
          (.writeTag w "ent" 2)
          (.writeObject w db)
          (.writeInt w id))))
    (reify ReadHandler
      (read [_ rdr tag component-count]
        (let [db (.readObject rdr)
              id (.readInt rdr)]
          (when *connection*
            (d/entity db id)))))]])

(defn add-datomic-handlers []
  (fr/add-handlers (datomic-handlers)))

(defn remove-datomic-handlers []
  (fr/rem-handler datomic.db.Db "db")
  (fr/rem-handler datomic.query.EntityMap "ent"))
