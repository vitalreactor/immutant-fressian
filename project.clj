(defproject immutant-fressian "0.2.0"
  :description "Adds :fressian to the encoding options for Immutant Messages"
  :url "http://github.com/vitalreactor/immutant-fressian"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [fressian-clojure "0.2.0"]]
  :immutant {:nrepl-port 4005})
