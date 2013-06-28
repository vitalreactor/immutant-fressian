# immutant-fressian

Adds a :fressian codec option to immutant.  Works out of the box with
messaging and caches.

Author: Ian Eslick

## Usage

In project.clj

    (:dependencies [immutant-fressian "0.2.0"])

Require immutant-fressian.core from some file at startup

    (require [immutant-fressian.core :as ifr])

Loading that namespace extends the codec and messaging multimethods
and provides some utilities for registering global handlers or passing
them into a simple fressian encode/decode API.

Anywhere in your code after the extension is required...

    (msg/publish "/queue/test" {:test "This is my test object"}
                 :encoding :fressian)

    (cache/create "test" :mode :local
                         :persist true
                         :encoding :fressian)

If you are using Datomic, you can encode/decode entities and databases by...

    (require [immutant-fressian.entity :as ifre])

    (ifre/set-datomic-connection <connection>)
    (ifre/add-datomic-handlers)

    (msg/public "/queue/test" [(d/db <conn>) (d/entity (d/db <conn>) <id>)]
                :encoding :fressian)


## License

Copyright © 2013 Vital Reactor, LLC

Distributed under the Eclipse Public License, the same as Clojure.
