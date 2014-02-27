(ns my-test0.components.datastore
;; This is datomic specific functionality

(comment
  (:require [datomic.api :as d]
            [clojure.java.io :as io])
  (:import datomic.Util)
  )

  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Datastore functions for Datomic Datastore
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(comment
  (defn get-connection
    [connection-string]
    (d/connect connection-string))

  (defn read-all
    [file]
    (Util/readAll (io/reader file)))

  (defn transact-all
    [connection file]
    (doseq [txd (read-all file)]
      (d/transact connection txd))
    :done)

  (defn delete
    [connection-string]
    (d/delete-database connection-string))

  (defn create
    [connection-string]
    (d/create-database connection-string))

  (defn load-schema
    [connection-string]
    (transact-all (get-connection connection-string)
                  (io/resource "data/schema.edn")))

  (defn load-data
    [connection-string path-to-data]

    (transact-all (get-connection connection-string)
                  (io/resource path-to-data)))

  (defn reset
    [connection-string]
    (delete connection-string)
    (create connection-string)
    (load-schema connection-string))

  (defn stop
    [connection]
    (d/release connection))

  )

;; These methods will need to be implemented
;; specific to the datastore

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Reset
(defn reset
  "Deletes and recreates the datastore with the correct schema.
   Does not import any seed data."
  [connection]
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Load Data
(defn load-data
  "Seed the datastore with data."
  [connection-string path-to-data]
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Stop
(defn stop
  "Release any resources associated to the datastore connection."
  [connection]
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Get Connection
(defn get-connection
  "Return a connection to the datastore"
  [connection-string]
  )
