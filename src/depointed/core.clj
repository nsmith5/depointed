(ns depointed.core
  (:require [depointed.server :as server])
  (:gen-class))

(defn -main [& args]
  (server/run))