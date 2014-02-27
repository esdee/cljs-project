(ns my-test0.middleware)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Wrap Datastore Connection
(defn wrap-datastore-connection
  [handler connection]
  (fn [request]
    (handler (assoc request
               :connection connection))))

