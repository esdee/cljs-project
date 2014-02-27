(defproject my-test0 "0.1.0"
  :description "FIXME: write a desription"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :dependencies  [;; Clojure
                  [org.clojure/clojure "1.5.1"]
                  [ring/ring "1.2.1"]
                  [fogus/ring-edn "0.2.0"]
                  [compojure "1.1.6"]
                  [com.stuartsierra/component "0.2.1"]
                  [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                  ;; Clojurescript
                  [org.clojure/clojurescript "0.0-2156"]
                  [weasel "0.1.0"]]

  :profiles
  {:dev {:source-paths ["src/clj" "dev"]
         :resource-paths ["resources/dev"]
         :dependencies [[org.clojure/tools.namespace "0.2.4"]
                        [com.cemerick/piggieback "0.1.3"]]
         :plugins [[lein-cljsbuild "1.0.2"]
                   [cider/cider-nrepl "0.1.0-SNAPSHOT"]]
         :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}
  
  :cljsbuild
  {:builds [{:id "dev"
             :source-paths ["src/clj" "src/cljs" "dev/brepl"]
             :compiler {:output-to "resources/dev/public/js/main.js"
                        :output-dir "resources/dev/public/js/out"
                        :optimizations :whitespace
                        :source-map "resources/dev/public/js/main.js.map"}}]})
