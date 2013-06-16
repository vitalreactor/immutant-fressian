(defproject immutant-fressian "0.1.0"
  :description "Adds :fressian to the encoding options for Immutant Messages"
  :url "http://github.com/vitalreactor/immutant-fressian"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [com.datomic/datomic-pro "0.8.3993"
                  :exclusions [org.slf4j/slf4j-nop org.slf4j/slf4j-log4j12]]
                 [fressian-clojure "0.1.0"]]
  :immutant {:nrepl-port 4005})
