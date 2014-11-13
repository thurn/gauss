(defproject clojurescript "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src"]
  :dependencies [
    [org.clojure/clojure "1.6.0"]
    [org.clojure/clojurescript "0.0-2311" :exclusions [org.apache.ant/ant]]
    [org.clojure/core.async "0.1.338.0-5c5012-alpha"]
    [com.cemerick/piggieback "0.1.3"]
    [weasel "0.4.0-SNAPSHOT"]
  ]
  :main ^:skip-aot clojurescript.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [
    [lein-cljsbuild "1.0.3"]
    [cider/cider-nrepl "0.7.0"]
  ]
  :global-vars {*print-length* 100}
  :cljsbuild {
    :builds [{
      :id "whitespace"
      :source-paths ["src"]
      :compiler {
        :output-to "resources/public/whitespace/whitespace.js"
        :output-dir "resources/public/whitespace/"
        :optimizations :whitespace
        :pretty-print true
        :source-map "resources/public/whitespace/sourcemap.js"}
    } {
      :id "simple"
      :source-paths ["src"]
      :compiler {
        :output-to "resources/public/simple/simple.js"
        :output-dir "resources/public/simple/"
        :optimizations :simple
        :pretty-print false
        :source-map "resources/public/simple/sourcemap.js"}
    } {
      :id "advanced"
      :source-paths ["src"]
      :compiler {
        :output-to "resources/public/advanced/advanced.js"
        :output-dir "resources/public/advanced/"
        :externs ["resources/externs/pixi.externs.js"]
        :optimizations :advanced
        :pretty-print false
        :source-map "resources/public/advanced/sourcemap.js"}
    }]}
)
