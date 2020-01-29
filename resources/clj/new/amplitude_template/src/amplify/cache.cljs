(ns {{namespace}}.amplify.cache
  (:require
   ["@aws-amplify/cache"
    :defaut Cache]))

(def config
  {:itemMaxSize 3000
   :defaultPriority 4})

(defn put [k v]
  (.setItem Cache k v))

(defn get [k]
  (.getItem Cache k))

(defn delete! [k]
  (.removeItem Cache k))

(defn clear! []
  (.clear Cache))

(defn keys []
  (.getAllKeys Cache))

(defn size []
  (.getCacheCurSize Cache))

(defn init! []
  (.configure Cache
              (clj->js config)))
