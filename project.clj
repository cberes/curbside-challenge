(defproject curbside-challenge "0.1.0-SNAPSHOT"
  :description "Solution for challenge at http://challenge.shopcurbside.com/"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-http "2.0.0"]
                 [cheshire "5.5.0"]]
  :main ^:skip-aot curbside-challenge.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
