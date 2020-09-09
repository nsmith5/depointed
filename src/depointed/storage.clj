(ns depointed.storage
  (:require [clojure.java.io :as io]))

(defn store-labelled-image
  "Store labelled image bucket"
  [label img]
  (let [clock (java.time.Clock/systemUTC)
        now (str (.instant clock))
        path (format "data/%s/%s.png" label now)]
    (io/make-parents path)
    (io/copy img (io/file path))))
