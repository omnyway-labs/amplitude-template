(ns {{namespace}}.amplify.graphql
  (:refer-clojure :exclude [get list])
  (:require
   [clojure.string :as str]
   [clojure.walk :as walk]
   ["@aws-amplify/api"
    :as api
    :default API]
   [{{namespace}}.util :refer [log >>] :as u]
   ["/graphql/queries" :as queries]
   ["/graphql/mutations" :as mutations]
   ["/graphql/subscriptions" :as subscriptions]))

(defn- invoke
  ([method]
   (invoke method nil u/flog u/flog))
  ([method param]
   (invoke method param u/flog u/flog))
  ([method param on-success]
   (invoke method param on-success u/flog))
  ([method param on-success on-error]
   (let [op (if (empty? param)
              (api/graphqlOperation method)
              (api/graphqlOperation method (clj->js param)))]
     (-> (.. API (graphql op))
         (.then on-success)
         (.catch on-error)))))

(defn pascalize [s]
  (->> (str/split (name s) #"-")
       (map str/capitalize)
       (str/join "")))

(defn- qname [op entity pluralize?]
  (if pluralize?
    (str (name op) (pascalize entity) "s")
    (str (name op) (pascalize entity))))

(defn- lookup [op entity]
  (condp = op
    :list   (aget queries   (qname :list entity true))
    :create (aget mutations (qname :create entity false))
    :delete (aget mutations (qname :delete entity false))
    :get    (aget mutations (qname :get entity false))))

(defn create! [entity param]
  (-> (lookup :create entity)
      (invoke {:input param})))

(defn list [entity event-fx]
  (-> (lookup :list entity)
      (invoke {}
              (fn [r]
                (let [data (-> (walk/keywordize-keys (js->clj r))
                               (get-in [:data
                                        (keyword (qname :list entity true))
                                        :items]))]
                  (>> [event-fx data])
                  data)))))

(defn get [entity param]
  (-> (lookup :get entity)
      (invoke param)))

(defn delete! [entity param]
  (-> (lookup :delete entity)
      (invoke {:input param})))

(defn gen-query [spec]
  :wip)

(defn init! []
  :ok)
