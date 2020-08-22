(ns strokeless.core
  (:require [org.httpkit.server :refer [run-server]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.util.response :refer [resource-response]]
            [clojure.data.json :as json]
            [compojure.core :refer [defroutes GET POST]])
  (:gen-class))

(defn upload [req]
  (clojure.java.io/copy 
    (:body req)
    (java.io.File. "upload.png")) 
  {:body req})

(defroutes routes
  (POST "/upload" [] upload)
  (GET "/" [] (resource-response "public/index.html")))

(def app
  (-> routes
      (wrap-resource "public")))

(defn server [] (run-server app {:port 3000}))

(defn -main [& args]
  (server))
