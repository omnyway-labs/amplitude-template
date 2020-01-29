(ns {{namespace}}.amplify.rest
  (:refer-clojure :exclude [get])
  (:require
   ["@aws-amplify/api"
    :as api
    :default API]
   [{{namespace}}.amplify.auth :as auth]
   [{{namespace}}.util :refer [log >>]]))

(def config
  {:endpoints [{:name     "configurator"
                :endpoint "https://{{namespace}}.execute-api.us-west-2.amazonaws.com/beta"}]})

(def cors-headers
  {"Access-Control-Allow-Origin"  "*"
   "Access-Control-Allow-Headers" "Access-Control-Allow-Origin"})

(defn post [api-name path headers body]
  (>> [:api-post-state :start])
  (-> (.post API (name api-name)
             path
             (clj->js {:headers (merge cors-headers
                                       {"Authorization" (auth/get-token)})
                       :body body}))
      (.then
       (fn [response]
         (>> [:api-post-state :stop])
         (log response)
         response))))

(defn get
  ([api-name path]
   (get api-name path cors-headers {}))
  ([api-name path headers body]
   (-> (.get API (name api-name)
             path
             (clj->js {:headers  headers
                       :body     body
                       :response true}))
       (.then
        (fn [response]
          (log response)
          response)))))

(defn init! []
  (.configure API (clj->js config)))
