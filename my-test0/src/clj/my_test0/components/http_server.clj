(ns my-test0.components.http-server
  (:require [org.httpkit.server :as server]))

(defn start
  [config datastore make-handler-fn]
  (server/run-server (make-handler-fn datastore)
                     (select-keys config [:port])))

