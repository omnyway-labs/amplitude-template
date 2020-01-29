(ns {{namespace}}.amplify.analytics
  (:require
   ["@aws-amplify/analytics"
    :refer [AWSKinesisProvider]
    :default Analytics]
   [{{namespace}}.util :refer [log]]))

(def config
  {:AWSKinesis
   {:region        "us-west-2"
    :bufferSize    1000
    :flushSize     100
    :flushInterval 5000
    :resendLimit   5}})

(defn find-stream-name []
  ;; specific to the stream
  "common-logs")

(defn publish-kinesis! [data]
  (.record Analytics
           (clj->js {:data       data
                     :streamName (find-stream-name)})
           "AWSKinesis"))

(defn- enable! []
  (.enable Analytics))

(defn- disable! []
  (.disable Analytics))

(defn record! [name data]
  (log :data data)
  (.record Analytics
           (clj->js {:name       name
                     :attributes (clj->js data)})))

(defn init! []
  (.addPluggable Analytics (AWSKinesisProvider.))
  (enable!)
  (.configure Analytics (clj->js config)))
