(ns strokeless.spa
    (:require [ring.util.response :as response]
              [ring.middleware.content-type :refer [wrap-content-type]]
              [ring.middleware.not-modified :refer [wrap-not-modified]]
              [ring.middleware.resource :refer [wrap-resource]]))

(defn ->html
    "Set content-type to `text/html`"
    [handler]
    (response/content-type handler "text/html"))

(defn index
    [req]
    (-> "public/index.html"
        (response/resource-response)
        (->html)))

(def handler
    (-> index
        (wrap-resource "public")
        (wrap-content-type)
        (wrap-not-modified)
        (wrap-content-type)))