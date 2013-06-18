(ns immutant-fressian.core-test
  (:require [clojure.test :refer :all]
            [immutant.messaging :as msg]
            [immutant.codecs :as codec]
            [immutant-fressian.core :refer :all]))

(defn f-able? [obj]
  (= (codec/decode (codec/encode obj :fressian) :fressian) obj))

(deftest simple-encoding
  (testing "Test basic codec"
    (is (f-able? 1))
    (is (f-able? 1.0))
    (is (f-able? 'a))
    (is (f-able? "test"))
    (is (f-able? [1 2 3]))
    (is (f-able? {:test 20}))
    (is (f-able? #{1 2 3}))))

(defn send-a-message [obj]
  (let [p (promise)
        qname "/queue/test"
        _ (msg/start qname)
        l (msg/listen  qname (fn [val] (deliver p val)))]
    (msg/publish qname obj);; :encoding :fressian)
    (let [result @p]
      (msg/unlisten l)
      (msg/stop qname)
      result)))

(defn msg-able? [obj]
  (= obj (send-a-message obj)))

(deftest simple-messaging
  (testing "Test encode/decode round trips via messaging"
    (is (msg-able? 1))
    (is (msg-able? 'test))
    (is (msg-able? :foo))
    (is (msg-able? {:test 10}))
    (is (msg-able? [1 2 3]))
    (is (msg-able? '(a b c)))))
     
(deftest caching 
  (testing "Test cache storage"
    (is (= 1 1))))
