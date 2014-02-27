(ns {{ns-name}}.routes
  (:require [ring.util.response :refer [file-response]]
            [ring.middleware.edn :refer [wrap-edn-params]]
            [compojure.core :refer [defroutes GET POST PUT]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [{{ns-name}}.middleware :as middleware]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Generate Response
(defn generate-response
  [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/edn"}
   :body (pr-str data)})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Index
(defn index
  []
  (file-response "public/html/index.html" {:root "resources"}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; App Routes
(defroutes routes
  (GET "/" [] (index))
  (route/files "/" {:root "resources/public"}))

(defn make-handler
  [datastore]
  (-> routes
      (middleware/wrap-datastore-connection (:connection datastore))
      (wrap-edn-params)))


