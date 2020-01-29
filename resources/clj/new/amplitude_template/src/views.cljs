(ns {{namespace}}.views
  (:require
   [clojure.string :as str]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [re-com.core :as rc
    :refer [h-box gap hyperlink v-box
            input-text
            button label]]
   [{{namespace}}.util :refer [log >> <<]]
   [{{namespace}}.views.core :as core]
   [{{namespace}}.views.container :as container]
   [{{namespace}}.views.bootstrap :as b]))

(defn fetch-data []
  (>> [:list-kvs])
  (<< [:kvs]))

(defn list-kvs-container []
  [v-box
   :size "auto"
   :padding "10px"
   :margin "10px"
   :children
   [(core/search-form "Filter KV..." #())
    [gap :size "20px"]
    (if-let [data (fetch-data)]
      (core/show-table data
                       (fn [row]
                         (>> [:delete-kv (:id row)])))
      [label :label "No Data"])
    [gap :size "20px"]
    [h-box
     :children
     [[gap :size "80%"]
      [:div {:align "right"}
       (core/paginate)]]]]])

(defn form* []
  (let [key       (r/atom nil)
        value     (r/atom nil)
        file      (r/atom nil)
        data      (r/atom nil)]
    [v-box
     :align :baseline
     :gap "20px"
     :class "form-control form-group row shadow-sm"
     :children
     [[:h4 "New Key Value"]
      [h-box
       :gap "10px"
       :align :center
       :children
       [[label
         :label "Key"
         :width "100px"
         :class (b/class :label)]
        [input-text
         :class     (b/class :form)
         :model     key
         :width     "300px"
         :on-change #(reset! key %)]]]
      [h-box
       :gap "10px"
       :align :center
       :children
       [[label
         :label "Value"
         :width "100px"
         :class (b/class :label)]
        [input-text
         :class     (b/class :form)
         :model     value
         :width     "300px"
         :on-change #(reset! value %)]]]

      [h-box
       :height   "50px"
       :gap      "50px"
       :align    :center
       :children
       [[label
         :label "Attachment"
         :width "60px"
         :class (b/class :label)]
        (core/upload-box file data)
        (core/show-progress-bar)]]

      [h-box
       :height   "50px"
       :gap      "50px"
       :align    :center
       :children
       [[gap :size "120px"]
        [button
         :label     "Submit"
         :class    "btn btn-primary"
         :on-click   (fn []
                       (when @file
                         (>> [:upload-image @file @data]))
                       (>> [:post-kv
                            {:key   @key
                             :value @value}])
                       (>> [:show-tab :list]))]]]]]))

(defn create-kv-container []
  [v-box
   :size "auto"
   :gap "20px"
   :align :baseline
   :margin "30px"
   :children [(form*)]])

(defn users-container []
  [:h1 "users"])

(defn roles-container []
  [:h1 "roles"])

(def containers
  {:list   #'list-kvs-container
   :create #'create-kv-container
   :users  #'users-container
   :roles  #'roles-container})

(def menus
  {:data  {:label   "KV"
           :icon    :notes
           :submenu [:list :create]}
   :users {:label   "Authorization"
           :submenu [:users :roles]}})

(defn init []
  (container/init! containers)
  [h-box
   :padding "0px"
   :margin "0px"
   :children
   [(core/sidebar menus)
    [b/container {:class "content is-open" :fluid true}
     (core/navbar)
     [:div {:style {:padding-bottom 72
                    :padding 10}}
      [container/current]]]]])
