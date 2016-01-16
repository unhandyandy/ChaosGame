(defproject chaos-game "0.1.0-SNAPSHOT"
  :description "Math Ideas: Chaos Game"
  :url "http://"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [seesaw "1.4.5"]]
  :main ^:skip-aot chaos-game.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
