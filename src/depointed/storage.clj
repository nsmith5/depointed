(ns depointed.storage
  (:import (com.google.cloud.storage BlobId BlobInfo Storage 
                                     StorageOptions Storage$BlobTargetOption
                                     Storage$BucketGetOption Storage$BlobListOption )))

; Global Google Cloud Storage client
(def client
  (.getService (StorageOptions/getDefaultInstance)))

; depointed default bucket
(def bucket-name "depointed-data")

; no-x-options is a short hand for passing no addition options
; to certain Storate APIs.
(def no-blob-options (into-array Storage$BlobTargetOption []))
(def no-bucket-options (into-array Storage$BucketGetOption []))
(def no-list-options (into-array Storage$BlobListOption []))

(defn is-image?
  [name]
  (boolean (re-matches #".*\.png" name)))

(defn as-csv-line
  "Take an object name and return the line of a label csv"
  [name]
  (let [label (first (clojure.string/split name #"/"))
        gs-uri (str "gs://" bucket-name "/" name)]
    (str gs-uri "," label)))

(defn csv
  "Create CSV of labels from all image objects in bucket"
  [objects]
  (clojure.string/join "\n" (map as-csv-line (filter is-image? objects))))

(defn objects
  "Enumarates all objects in a the bucket"
  []
  (let [bucket (.get client bucket-name no-bucket-options)
        objects (.list bucket no-list-options)]
    (map (fn [obj] (.getName obj)) (.iterateAll objects))))

(defn update-label-csv
  "Store csv of image labels"
  []
  (let [path "label.csv"
        id (BlobId/of bucket-name path)
        info (.build (.setContentType (BlobInfo/newBuilder id) "text/csv"))]
    (.create client info (.getBytes (csv (objects)) "UTF-8") no-blob-options)))

(defn store-labelled-image
  "Store labelled image bucket"
  [label img]
  (do ; First Upload image
      (let [clock (java.time.Clock/systemUTC)
            now (str (.instant clock))
            path (str label "/" now ".png")
            id (BlobId/of bucket-name path)
            info (.build (.setContentType (BlobInfo/newBuilder id) "image/png"))]
        (.create client info img no-blob-options))
      ; Then sync CSV labal file
      (update-label-csv)))
