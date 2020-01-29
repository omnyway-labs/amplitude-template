(ns {{namespace}}.views.container
  (:require
   [reagent.core :as r]
   [{{namespace}}.util :refer [log >> <<]]))

(def state (atom nil))

(def window-dims*
  (r/track
   (fn []
     (r/with-let [dims (r/atom nil)
                  handler #(let [w (.-innerWidth js/window)
                                 h (.-innerHeight js/window)]
                             (r/rswap! dims assoc :w w :h h))
                  _ (handler)
                  _ (.addEventListener js/window "resize" handler)]
       @dims
       (finally (.removeEventListener js/window "resize" handler))))))

(defn current []
  (let [dom-node* (r/atom nil)]
    (fn []
      (let [_ @window-dims*]
        [:div {:ref #(reset! dom-node* %)}
         (when @dom-node*
           (let [width  (.-clientWidth @dom-node*)
                 height (.-clientHeight @dom-node*)
                 id    (<< [:current-tab])]
             (if id
               [(get @state (keyword id))]
               [:div.uh-oh])))]))))

(defn init! [containers]
  (reset! state containers))
