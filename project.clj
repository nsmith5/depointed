(defproject strokeless "0.1.0-SNAPSHOT"
  :description "Chinese character recognition without knowing the stroke order"
  :url "https://strokeless.nfsmith.ca"
  :license {:name "GPL 3.0"
            :url "https://www.gnu.org/licenses/gpl-3.0.en.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :main ^:skip-aot strokeless.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
