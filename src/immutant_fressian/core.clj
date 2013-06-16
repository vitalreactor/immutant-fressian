(ns immutant-fressian.core
  (:require [org.fressian.clojure :as fr]
            [immutant.codecs :as core]
            [immutant.messaging.codecs :as mc])
  (:import [javax.jms BytesMessage TextMessage]))

;;
;; Add fressian encoding to Immutant
;;

(defmethod core/encode :fressian
  [data & args]
  (fr/encode data))

(defmethod core/decode :fressian
  [data & args]
  (try
    (and data (fr/decode data))
    (catch Throwable e
      (throw (RuntimeException.
              (str "Invalid fressian-encoded data (type=" (class data) "): " data)
              e)))))

;;
;; Add dispatch support for messaging
;;

(defmethod mc/encode :fressian [^javax.jms.Session session message options]
  (doto (.createBytesMessage session)
    (.writeBytes (core/encode message :fressian))
    (.setStringProperty mc/encoding-header-name "fressian")))

(defmethod mc/decode :fressian [message]
  "Turn a binary fressian into a clojure data structure"
  (let [bytes (byte-array (.getBodyLength message))]
    (.readBytes message bytes)
    (core/decode bytes  :fressian)))
