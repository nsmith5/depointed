(ns strokeless.core
  (:require [strokeless.server :as server])
  (:gen-class))

(defn -main [& args]
  (server/run))