(ns my-test0.components.system
  (:require [com.stuartsierra.component :as component]
            [my-test0.components.http-server :as server]
            [my-test0.components.datastore :as datastore]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Datastore
(defrecord Datastore [connection-string path-to-seed-data connection]
  component/Lifecycle
  (start [component]
    (println "Resetting Datastore")
    (datastore/reset connection-string)
    (when path-to-seed-data
      (println "  - Datastore Seeding from " path-to-seed-data)
      (datastore/load-data connection-string path-to-seed-data)
      (println "  - Datastore Seeded"))
    (assoc component :connection (datastore/get-connection connection-string)))
  (stop [component]
    (println "Stopping Datastore")
    (datastore/stop (:connection component))
    (println "  - Datastore stopped")
    (dissoc component :connection)))

(defn datastore
  [config]
  (map->Datastore config))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Http Server
(defrecord HttpServer [config make-handler-fn server datastore]
    component/Lifecycle
      (start [component]
            (println "Starting HTTP Server")
            (let [stop-fn (server/start config datastore make-handler-fn)]
                    (println "  - HTTP Server started on port " (:port config))
                          (assoc component :server {:stop-fn stop-fn})))
        (stop [component]
              (println "Stopping HTTP Server")
              (when-let [stop-fn (-> component :server :stop-fn)]
                      (stop-fn)
                            (println "  - HTTP Server stopped"))
              (dissoc component :server)))

(defn http-server [config make-handler-fn]
    (map->HttpServer {:config config :make-handler-fn make-handler-fn}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; App
(defrecord App [config datastore http-server]
  component/Lifecycle
  (start [this]
    (component/start-system this))
  (stop [this]
    (component/stop-system this)))

(defn app [config make-handler-fn]
  (-> (map->App {:config config
                 :datastore (datastore config)
                 :http-server (http-server config make-handler-fn)})
      (component/system-using {:http-server [:datastore]})))
