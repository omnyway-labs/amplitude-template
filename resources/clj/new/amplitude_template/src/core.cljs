(ns {{namespace}}.core
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [{{namespace}}.db :as db]
   [{{namespace}}.amplify :as amplify]
   [{{namespace}}.views :as views]
   [{{namespace}}.subs :as subs]
   [{{namespace}}.events :as events]
   [{{namespace}}.util :refer [>>!]]
   [{{namespace}}.amplify.auth :refer [wrap-auth]]))

(def debug?
  ^boolean goog.DEBUG)

(defn dev-setup []
  (when debug?
    (println "dev mode")))

(def authenticated-view (wrap-auth views/init))

(defn ^:export init []
  (>>! [:initialize-db])
  (rf/clear-subscription-cache!)
  (amplify/init)
  (r/render [authenticated-view]
            (.getElementById js/document "app")))
