(ns {{namespace}}.views.core
  (:require
   [clojure.string :as str]
   [reagent.core :as r]
   [re-com.core
    :refer [h-box v-box
            progress-bar
            typeahead
            md-circle-icon-button
            box title
            flex-child-style
            gap]]
   [{{namespace}}.util :refer [log >> <<] :as u]
   [{{namespace}}.views.bootstrap :as b]))

(defn icon-button [id f]
  [md-circle-icon-button
   :md-icon-name (str "zmdi zmdi-" (name id))
   :size         :smaller
   :style {:border "none"}
   :on-click f])

(defn nav-button [id f]
  [md-circle-icon-button
   :md-icon-name (str "zmdi zmdi-" (name id))
   :size         :smaller
   :style {:border "none"
           :color "white"}
   :on-click f])

;; transducer to get text out of file object.
(def extract-result
  (map #(-> % .-target .-result js->clj)))

(defn upload-box [file data]
  (let [as-file  (fn [f]
                   (-> (str/split f #"\\")
                       (last)))]
    [:input {:type      "file"
             :width     "100px"
             :accept    "image/png, image/jpeg"
             :on-change (fn [e]
                          (let [fname (as-file (-> e .-target .-value))
                                fdata (-> e .-target .-files (aget 0))]
                            (reset! file fname)
                            (reset! data fdata)))}]))

(defn show-progress-bar []
  (let [progress (<< [:upload-image-progress])]
    (when (> progress 0)
      [progress-bar
       :model    (or progress 0)
       :width    "300px"])))

(defn drop-zone []
  (let [prevent-default #(.preventDefault %)
        cause-upload #(as-> (.. % -dataTransfer -files (item 0)) file
                        (>> [:upload-image file]))]
    [:div {:style {:width 120
                   :height 64
                   :background-color "lightyellow"}
           :onDragOver prevent-default
           :onDrop (juxt prevent-default cause-upload)}]))


(defn input-completion [choices model]
  (let [status          (r/atom nil)
        on-change-value (r/atom nil)
        suggestions-for-search (fn [s]
                                 (->> (for [n choices
                                            :when (re-find (re-pattern (str "(?i)" s)) n)]
                                        {:name n :more :stuff})
                                      (take 5)
                                      (into [])))]
    [typeahead
     :class "form-control typeahead border-primary"
     :model                model
     :data-source          suggestions-for-search
     :suggestion-to-string #(:name %)
     :render-suggestion    (fn [{:keys [name]}]
                             [:span
                              {:style {:width "200px" :padding "8px"}} name])
     :width                "250px"
     :on-change            #(reset! on-change-value %)
     :change-on-blur?      true
     :immediate-model-update? false
     :rigid?               true]))


(defn header-keys [data]
  (-> data first (dissoc :metadata) keys))

(defn show-table [data delete-fn]
  (let [keys (header-keys data)]
    [b/card {:class "shadow-sm rounded"}
     [b/table {:size "sm" :hover true
              :responsive true
              :borderless false
              :striped false}
     [:thead {:class "thead-light"}
      [:tr
       (for [h (map #(-> % name str/capitalize) keys)]
         [:th h])
       [:th {:width "5px"} ""]
       [:th {:width "5px"} ""]]]
     [:tbody
      (for [row data]
        [:tr
         (for [k keys] [:td (get row k)])
         [:td {:width "5px"} [icon-button
                              :notifications #()]]
         [:td {:width "5px"} [icon-button :delete
                              #(delete-fn row)]]])]]]))


(defn submit-search [data]
  (>> [:submit-search data]))

(defn search-form [placeholder-text search-fn]
  (let [search-term (r/atom nil)]
    [b/input-group
       [b/input {:placeholder placeholder-text}]
       [b/input-group-addon {:addonType "append"}
        [md-circle-icon-button
         :md-icon-name (str "zmdi zmdi-search")
         :style {:border "none" :color "bg-primary"}
         :on-click search-fn]]]))


(defn paginate []
  [:div {:align "right"}
   [b/pagination
   [b/pagination-item
    [b/pagination-link {:first true :href "#"}]]
   [b/pagination-item
    [b/pagination-link {:previous true :href "#"}]]
   [b/pagination-item
    [b/pagination-link {:href "#"} 1]]
   [b/pagination-item
    [b/pagination-link {:href "#"} 2]]
   [b/pagination-item
    [b/pagination-link {:href "#"} 3]]
   [b/pagination-item
    [b/pagination-link {:next true :href "#"}]]
   [b/pagination-item
    [b/pagination-link {:last true :href "#"}]]]])

(def collapse (r/atom true))

(defn navbar []
  (let [current-tab (<< [:current-tab])]
    [b/navbar {:color "dark"
               :dark false
               :expand "md"
               :fixed "sticky-top"
               :inverse true
               :toggle true}
     [b/navbar-toggler {:onClick #(reset! collapse %) :class "mr-2" }]

     [b/collapse {:isOpen @collapse :navbar true}
      [gap :size "20%"]
      [gap :size "5px"]
      (nav-button :notifications #(>> [:show-tab "alerts"]))
      [gap :size "10px"]
      (nav-button :account-circle #())
      [gap :size "10px"]
      (nav-button :sign-in #(>> [:sign-out]))]]))

(defn toggle-link [cur id]
  (if (= cur id) {}
      {:href "#"
       :onClick #(>> [:show-tab id])}))

(defn sidebar [menus]
  (let [container (<< [:current-tab])]
    [:div {:class "sidebar is-open"}
     [:div {:class "sidebar-header"}
      [:h4 "{{namespace}}"]]
     [:br]
     [:div {:class "side-menu"}
      [b/nav {:vertical true :class "list-unstyled pb-3"}
       (for [[i {:keys [label submenu]}] menus]
         [:div
          (if (empty? submenu)
            [b/nav-item [b/nav-link (toggle-link container i)
                         label]]
            [b/nav-item {:class "sidebar-main-item"}
             [:b label]])

          (for [m submenu]
            [b/nav-item {:key submenu :class "items-menu"}
             [b/nav-link (toggle-link container  m)
              (name m)]])])]]]))
