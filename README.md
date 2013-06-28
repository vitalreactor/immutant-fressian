# immutant-fressian

Adds a :fressian codec option to immutant.  Works out of the box with
messaging and caches.

Author: Ian Eslick

## Usage

In project.clj

    (:dependencies [immutant-fressian "0.2.0"])

Require immutant-fressian.core in your immutant init.clj

    (require [immutant-fressian.core :as ifr])

Loading that namespace extends the Immutant codecs and the messaging
multimethods. Anywhere in your code after the extension is required...

    (msg/publish "/queue/test" {:test "This is my test object"}
                 :encoding :fressian)

    (cache/create "test" :mode :local
                         :persist true
                         :encoding :fressian)

immutant-fressian is built on the
[fressian-clojure](https://github.com/vitalreactor/fressian-clojure)
library which wraps the Java Fressian API and provides hooks for
registering one or more handlers for your own data types.  The best
place to add hooks is a namespace required in your init.clj file or in
that file directly.

You can follow the included example of fressian-encoding Datomic
Entities and Database values in [entity.clj](https://github.com/vitalreactor/immutant-fressian/blob/master/src/immutant_fressian/entity.clj):


    (require [immutant-fressian.entity :as ifre])

    (ifre/set-datomic-connection <connection>)
    (ifre/add-datomic-handlers)

    (msg/public "/queue/test" [(d/db <conn>) (d/entity (d/db <conn>) <id>)]
                :encoding :fressian)

## License

Copyright © 2013 Vital Reactor, LLC

Distributed under the Eclipse Public License, the same as Clojure.
