(ns strokeless.storage
  (:import (com.google.cloud.storage BlobId BlobInfo Storage 
                                     StorageOptions Storage$BlobTargetOption)))

; Global Google Cloud Storage client
(def client
  (.getService (StorageOptions/getDefaultInstance)))

; Strokeless default bucket
(def bucket-name "strokeless-data")

; no-options is a short hand for passing no addtional options to
; object creation methods
(def no-options (into-array Storage$BlobTargetOption []))

(defn store-labelled-image
  "Store labelled image bucket"
  [label img]
  (do ; First Upload image
      (println label)
      (let [clock (java.time.Clock/systemUTC)
            now (str (.instant clock))
            path (str label "/" now ".png")
            id (BlobId/of bucket-name path)
            info (.build (.setContentType (BlobInfo/newBuilder id) "image/png"))]
        (.create client info img no-options))
      ; Then sync CSV labal file
      (println "TODO: Implement csv creation thing")))
