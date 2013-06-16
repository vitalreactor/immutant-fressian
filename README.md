# immutant-fressian

Extends immutant with a fressian codec. Natively supports
fressian encoding for messaging.  May also work for caches.

Author: Ian Eslick

## Usage

   ;; In project.clj
   (:dependencies [immutant-fressian "0.1.0"])

   ;; In some file loaded at startup
   (require [immutant-fressian.core])

   ;; Anywhere in your code after the extension is required...
   (msg/publish "/queue/test" {:test "This is my test object"}
        :encoding :fressian)


## License

Copyright © 2013 Vital Reactor, LLC

Distributed under the Eclipse Public License, the same as Clojure.
