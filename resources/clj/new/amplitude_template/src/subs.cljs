(ns {{namespace}}.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub :name
            #(:name % ))

(rf/reg-sub :aws-creds
            #(get-in % [:creds :aws-creds]))

(rf/reg-sub :aws-config
            #(get-in % [:creds :aws-config]))

(rf/reg-sub :auth-state
            #(:auth-state %))

(rf/reg-sub :initialized-app?
            (comp boolean :initialized-app?))

(rf/reg-sub :current-tab
            #(or (:current-tab %) "list"))

(rf/reg-sub :current-zoom
            #(:current-zoom %))

(rf/reg-sub :upload-image-progress
            #(:upload-image-progress %))

(rf/reg-sub :streaming-endpoint
            #(:streaming-endpoint %))

(rf/reg-sub :kvs
            #(:kvs %))
