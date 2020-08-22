(ns strokeless.core
  (:require [org.httpkit.server :refer [run-server]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [ring.util.response :refer [resource-response content-type]]
            [compojure.core :refer [defroutes GET POST]])
  (:gen-class))

; Handle image upload
(defn upload [req]
  (clojure.java.io/copy (:body req) (java.io.File. "upload.png")) 
  { :status 201
    :headers { "Content-Type" "application/json" }
    :body "accepted"})

; Mux routes
(defroutes routes
  (GET "/" [] (content-type (resource-response "public/index.html") "text/html"))
  (GET "/teach" [] (content-type (resource-response "public/teach.html") "text/html"))
  (POST "/upload" [] upload))

; Middleware layers
(def app
  (-> routes
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)))

; Start up server on port 3000
(defn server [] (run-server app {:port 3000}))

(defn -main [& args]
  (server))
