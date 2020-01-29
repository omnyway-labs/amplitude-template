(ns {{namespace}}.amplify
  (:require
   ["@aws-amplify/core"
    :refer [Hub]
    :default Amplify]
   ["aws-sdk" :as AWS]
   [{{namespace}}.util :refer [>>!]]
   ["/aws-exports.js" :default awsmobile]
   [{{namespace}}.amplify.auth :as auth]
   [{{namespace}}.amplify.rest :as rest]
   [{{namespace}}.amplify.graphql :as graphql]))

(def aws-region "us-west-2")

(defn setup-hub-listener []
  (.listen Hub "auth"
           (fn [data]
             (.-event (.-payload data)))))

(defn fetch-creds []
  (auth/current-creds))

(defn make-config [creds]
  (AWS/Config. (clj->js
                {:credentials creds :region aws-region})))

(defn ^:export init []
  (.configure Amplify awsmobile)
  (rest/init!)
  (graphql/init!)
  (>>! [:init-hub-listener]))
