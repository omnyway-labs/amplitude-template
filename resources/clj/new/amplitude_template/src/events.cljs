(ns {{namespace}}.events
  (:require
   [re-frame.core :as rf]
   [{{namespace}}.db :as db]
   [{{namespace}}.util :refer [log >>] :as u]
   [{{namespace}}.amplify :as amplify]
   [{{namespace}}.amplify.auth :as auth]
   [{{namespace}}.amplify.graphql :as gql]
   [{{namespace}}.amplify.analytics :as analytics]
   [{{namespace}}.amplify.storage :as storage]))

(rf/reg-event-db
  :initialize-db
  (fn [_ _]
    db/default-db))


;; Interceptors

;; Effects

(rf/reg-fx
  :setup-hub-listener
  (fn []
    (js/console.log "Top of setup-hub-listener fx")
    (let [data (amplify/setup-hub-listener)]
      (rf/dispatch [:update-auth-state data]))))

(rf/reg-fx
  :fetch-aws-current-creds
  (fn []
    (js/console.log "fetch-aws-current-creds-handler")
    (let [creds (amplify/fetch-creds)]
      (rf/dispatch
       [:set-aws-creds creds]))))

(rf/reg-fx
  :gen-aws-config
  (fn [aws-creds] (amplify/make-config aws-creds)))

(rf/reg-fx
 :upload-image
 (fn [{:keys [file data on-success on-failure]}]
   (log file)
   (storage/put-file file data
                     on-success
                     on-failure)))

(rf/reg-fx
  :sign-in
  (fn [{:keys [username password on-success on-failure]}]
    (auth/sign-in username password on-success on-failure)))

(rf/reg-fx
 :list-kvs
 (fn [{:keys [model]}]
   (gql/list "kv" :list-kvs-result)))


;; Events

(rf/reg-event-fx
  :init-hub-listener
  (fn [coeffect _]
    (js/console.log "Inside setup-hub-listener event-fx")
    {:setup-hub-listener nil}))

(rf/reg-event-fx
  :update-auth-state
  (fn [{:keys [db]} [_ auth-state]]
    (let [base-resp {:db (assoc-in db [:auth-state]  auth-state)}]
      (if (= auth-state "signIn")
        (merge base-resp {:fetch-aws-current-creds nil})
        base-resp))))

(rf/reg-event-fx
  :set-aws-creds
  (fn [{:keys [db]} [_ aws-creds]]
    {:db             (assoc-in db [:creds :aws-access] aws-creds)
     :gen-aws-config aws-creds}))

(rf/reg-event-fx
  :set-current-user
  (fn [{:keys [db]} [_ user]]
    {:db (assoc db :user user)}))

;; View events

(rf/reg-event-fx
 :show-tab
 [rf/trim-v]
 (fn [{:keys [db]} [destination]]
   {:db (assoc db :current-tab destination)}))

(rf/reg-event-fx
  :zoom-in
  [rf/trim-v]
  (fn [{:keys [db]} [object-key]]
    {:db (assoc db :current-zoom object-key)}))

(rf/reg-event-fx
  :zoom-out
  (fn [{:keys [db]} _]
    {:db (dissoc db :current-zoom)}))

(rf/reg-event-fx
  :show-screen
  [rf/trim-v]
  (fn [{:keys [db]} [screen]]
    {:db (assoc db :screen screen)}))

(rf/reg-event-fx
  :hide-screen
  (fn [{:keys [db]} _]
    {:db (dissoc db :screen)}))

(rf/reg-event-fx
  :upload-image
  [rf/trim-v]
  (fn [_ [file data]]
    {:upload-image {:file file
                    :data data
                    :on-success #(>> [:upload-image-success %])
                    :on-failure #(>> [:upload-image-failure %])}}))

(rf/reg-event-fx
  :upload-image-success
  [rf/trim-v]
  (fn [_ [stuff]]
    (log stuff)
    {}))

(rf/reg-event-fx
  :upload-image-failure
  [rf/trim-v]
  (fn [_ [thing]]
    (log "upload-image-failure" thing)))

(rf/reg-event-fx
  :upload-image-progress
  [rf/trim-v]
  (fn [{:keys [db]} [pct]]
    {:db (assoc db :upload-image-progress pct)}))

;; auth stuff
(rf/reg-event-fx
  :sign-out
  (fn [{:keys [db]} [_]]
    (log :sign-out)
    (auth/sign-out)
    db))

;; graphql

(rf/reg-event-fx
  :post-kv
  (fn [{:keys [db]} [_ data]]
    (->> (merge data
                {:datetime (u/datetime)})
         (gql/create! "kv"))
    {:db db}))


(rf/reg-event-fx
 :list-kvs
 (fn [{:keys [db]} [_]]
   (gql/list "kv" :list-kvs-result)
   {:db db}))

(rf/reg-event-fx
  :list-kvs-result
  (fn [{:keys [db]} [_ data]]
    {:db (assoc db :kvs data)}))

(rf/reg-event-fx
  :delete-kv
  (fn [{:keys [db]} [_ id]]
    (gql/delete! "kv" {:id id})
    {:db (u/dissoc-in db :kvs id)}))
