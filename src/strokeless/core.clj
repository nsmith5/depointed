(ns strokeless.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.util.response :refer [resource-response]]
            [compojure.core :refer [defroutes GET]])
  (:gen-class))

(defn hello-handler [req]
  {:body "Hello World!"})

(defroutes routes
  (GET "/hello" [] hello-handler)
  (GET "/" [] (resource-response "public/index.html")))

(def app
  (-> routes
      (wrap-resource "public")))

(defn server [] (run-jetty app {:join? false, :port 3000}))

(defn -main [& args]
  (server))
