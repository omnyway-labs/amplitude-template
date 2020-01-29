(ns {{namespace}}.amplify.pubsub
  (:require
   ["@aws-amplify/pubsub" :default PubSub]
   ["@aws-amplify/pubsub/lib/Providers" :default MqttOverWSProvider]))

(def endpoint "wss://iot.eclipse.org:443/mqtt")

(defn subscribe [topic]
  (.subscribe PubSub topic))

(defn publish [topic data]
  (-> (.publish PubSub topic
                (clj->js {:msg data}))))

(defn init! []
  (.addPluggable PubSub
                 (MqttOverWSProvider.
                  (clj->js {:aws_pubsub_region "us-west-2"
                            :aws_pubsub_endpoint endpoint})))
  (.configure PubSub))
