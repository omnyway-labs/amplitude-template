(ns {{namespace}}.amplify.storage
  (:require
   ["@aws-amplify/storage"
    :default Storage]
   [{{namespace}}.util :refer [log >> pct]]))

(def config
  {:level "private"})

(defn get-file [file]
  (.get Storage file)
  :uploaded)

(defn put-file [fname file on-success on-failure]
  (log :put-file fname file)
  (.. Storage
      (put fname file
           (clj->js {"progressCallback"
                     (fn [progress]
                       (let [p (pct (-> progress .-loaded)
                                    (-> progress .-total))]
                         (>> [:upload-image-progress p])))}))
      (then on-success)
      (catch on-failure))
  :ok)

(defn init! []
  (.configure Storage
              (clj->js config)))
