(ns my-test0.core
    (:require-macros [cljs.core.async.macros :refer [go]])
    (:require [cljs.reader :as reader]
              [cljs.core.async :as async :refer [put! chan alts!]]
              [clojure.data :as data]))
