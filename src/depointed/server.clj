(ns depointed.server
  (:require [clojure.string :as string] 
            [org.httpkit.server :refer [run-server]]
            [ring.util.request :as request]
            [depointed.spa :as spa]
            [depointed.api :as api]))

; Default ring handler
(defn default-handler
    "Mux requests between the SPA and API handlers"
    [req]
    (let [path (request/path-info req)]
        (if (string/starts-with? path "/api")
            (api/handler req)
            (spa/handler req))))

; Default server configuration
(def default-config {:port 3000})

(defn run
    "Run httpkit server"
    ([] (run default-handler))
    ([handler] (run handler default-config))
    ([handler config] (run-server handler config)))
