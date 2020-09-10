(ns depointed.api
    (:require [clojure.data.json :as json]
              [ring.middleware.params :refer [wrap-params]]
              [ring.util.response :as response]
              [ring.util.request :as request]
              [depointed.storage :as storage]))

(defn upload 
    "Handle upload of new labelled imaged" 
    [req]
    (let [label (get (:query-params req) "label")
          image (.bytes (:body req))]
         (storage/store-labelled-image label image))
    (-> {"msg" "Image accepted"}
        (json/write-str)
        (response/response)
        (response/status 201)
        (response/content-type "application/json")))

(defn train 
    "Update model by training against uploaded images" 
    [req]
    (-> {:error "Model training not implimented yet"}
        (json/write-str)
        (response/response)
        (response/status 501)
        (response/content-type "application/json")))

(defn predict
    "Predict character from image using model" 
    [req]
    (-> {:error "Model training not implimented yet"}
        (json/write-str)
        (response/response)
        (response/status 501)
        (response/content-type "application/json")))

(defn handler
    [req]
    (let [path (request/path-info req)]
        (cond
            (= "/api/upload" path) ((wrap-params upload) req)
            (= "/api/train" path) (train req)
            (= "/api/predict" path) (predict req)
            :else (response/not-found "Endpoint not found"))))
