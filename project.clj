(defproject depointed "0.1.0-SNAPSHOT"
  :description "Lookup unicode characters by drawing them"
  :url "https://depointed.nfsmith.ca"
  :license {:name "GPL 3.0"
            :url "https://www.gnu.org/licenses/gpl-3.0.en.html"}
  :dependencies [[http-kit "2.3.0"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "1.0.0"] 
                 [ring/ring-core "1.6.3"]
                 [com.google.cloud/google-cloud-storage "1.111.2"]]
  :main ^:skip-aot depointed.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
