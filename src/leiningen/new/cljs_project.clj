(ns leiningen.new.cljs-project
  (:require [leiningen.new.templates :refer [renderer
                                             name-to-path
                                             ->files
                                             sanitize-ns]]
            [leiningen.core.main :as main]))

(def render (renderer "cljs-project"))

(defn cljs-project
  [name]
  (let [data {:name name
              :ns-name (sanitize-ns name)
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' cljs-project project.")
    (->files data
            [".gitignore" (render ".gitignore" data)]
            ["project.clj" (render "project.clj" data)]
            ["resources/public/html/index.html" (render "resources/public/html/index.html" data)]
            ;; Files for REPL env
            ["dev/user.clj" (render "dev/user.clj" data)]
            ["dev/brepl/connect.cljs" (render "dev/brepl/connect.cljs" data)]
            ;; Test files
            ["test/{{sanitized}}/core_test.clj" (render "core_test.clj" data)]
            ;; Clojure files
            ["src/clj/{{sanitized}}/core.clj" (render "core.clj" data)]
            ["src/clj/{{sanitized}}/routes.clj" (render "routes.clj" data)]
            ["src/clj/{{sanitized}}/middleware.clj" (render "middleware.clj" data)]
            ;; Components
            ["src/clj/{{sanitized}}/components/datastore.clj" (render "components/datastore.clj" data)]
            ["src/clj/{{sanitized}}/components/http_server.clj" (render "components/http_server.clj" data)]
            ["src/clj/{{sanitized}}/components/system.clj" (render "components/system.clj" data)]
            ;; Clojurescript files
            ["src/cljs/{{sanitized}}/core.cljs" (render "core.cljs" data)])))
