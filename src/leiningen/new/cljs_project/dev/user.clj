(ns user
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.namespace.repl :as repl]
            [cemerick.piggieback :as p]
            [weasel.repl.websocket :as weasel-ws]
            [{{ns-name}}.components.system :as system]
            [{{ns-name}}.routes :as routes]))

(def system nil)

(def config {:port 8000})
;; :connection-string "datomic:free://localhost:4334/om_async"
;; :path-to-seed-data "data/initial.ednan"

(defn init []
  (alter-var-root #'system
                  (fn [_]
                    (system/app config routes/make-handler))))

(defn start []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root #'system
                  (fn [s] (when s (component/stop s)))))

(defn go []
  (init)
  (start))

(defn reset []
  (stop)
  (repl/refresh :after 'user/go))

(defn browser-repl
  []
  (p/cljs-repl
   :repl-env (weasel-ws/repl-env :ip "127.0.0.1"
                                 :port 9001)))
