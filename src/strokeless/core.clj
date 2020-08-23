(ns strokeless.core
  (:require [org.httpkit.server :refer [run-server]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [ring.middleware.params :refer [wrap-params]] 
            [ring.util.response :refer [resource-response content-type]]
            [clojure.data.json :as json]
            [compojure.core :refer [defroutes GET POST]]
            [strokeless.storage :refer [store-labelled-image]])
  (:gen-class))

; Handle image upload
(defn upload 
  "Handle upload of new labelled imaged" 
  [req]
  (do (let [label (get (:params req) "label")]
         (store-labelled-image label (.bytes (:body req))))
       {:status 201
        :headers {"Content-Type" "application/json"}
        :body (json/write-str {:msg "Image accepted"})}))

; Retrain model
(defn train [req]
  { :status 500
    :headers {"Content-Type" "application/json"}
    :body (json/write-str {:error "Model training no implimented yet"})})

; Predict Character from image 
(defn predict [req]
  { :status 500
    :headers { "Content-Type" "application/json" }
    :body (json/write-str {:error "Prediction Not implemented"})})

; Mux routes
(defroutes routes
  ; UI
  (GET "/" [] (content-type (resource-response "public/index.html") "text/html"))
  (GET "/teach" [] (content-type (resource-response "public/teach.html") "text/html"))
  ; API 
  (POST "/upload" [] upload)
  (POST "/predict" [] predict)
  (POST "/train" [] train))

; Middleware layers
(def app
  (-> routes
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)
      (wrap-params)))

; Start up server on port 3000
(defn server [] (run-server app {:port 3000}))

(defn -main [& args]
  (server))
