(ns dvlopt.fdat.nippy-test

  {:author "Adam Helinski"}

  (:require [clojure.test      :as t]
            [dvlopt.fdat       :as fdat]
            [dvlopt.fdat.nippy :as fdat.nippy]
            [dvlopt.fdat-test  :as fdat-test]
            [taoensso.nippy    :as nippy]))




;;;;;;;;;; Ser/de


(defn rebuild-n

  ""

  [imeta n]

  (fdat-test/recall-serde imeta
                          n
                          nippy/freeze
                          nippy/thaw))




;;;;;;;;;;


(def f-data
     (nippy/freeze fdat-test/f))


(def f-recalled
     (nippy/thaw f-data))




(def sq-data
     (nippy/freeze fdat-test/sq))


(def sq-rebuild
     (nippy/thaw sq-data))




;;;;;;;;;;


(t/deftest build

  (t/is (= 12
           (fdat-test/f 3)
           (f-recalled 3)
           ((rebuild-n f-recalled
                       10) 3))
        "Rebuilding a function")


  (t/is (= (take 100
                 fdat-test/sq)
           (take 100
                 sq-rebuild)
           (take 100
                 (rebuild-n sq-rebuild
                            10)))
        "Rebuilding an infinite sequence"))
