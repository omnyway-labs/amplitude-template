(ns {{namespace}}.util
  (:require
   [cljs.reader :as reader]
   [goog.i18n.DateTimeFormat :as dtf]
   [cljs-time.format :as f]
   [cljs-time.core :as t]
   [reagent.core :as r]
   [re-com.validate :refer [date-like?]]
   [re-frame.core :as rf]
   [goog.date.Date]
   [goog.date.DateTime]))

(def log js/console.log)
(def >> rf/dispatch)
(def >>! rf/dispatch-sync)
(def << (comp deref rf/subscribe))

(defn browser []
  (-> js/goog .-labs .-userAgent .-browser))

(defn chrome? []
  (.isChrome (browser)))

(defn date->string [date]
  (if (date-like? date)
    (f/unparse (f/formatter "dd MMM, yyyy") date)
    "no date"))

(defn pct [x y]
  (.round js/Math
          (* (/ x y) 100.0)))

(defn as-datetime [ts]
  (-> (f/formatter "yyyy-MM-dd HH:mm:ss")
      (f/unparse ts)))

(defn now []
  (t/now))

(defn datetime []
  (as-datetime (now)))

(defmacro promise->
  [promise & body]
  `(.catch
    (-> ~promise
        ~@(map (fn [expr] (list '.then expr)) body))
    (fn [error#]
      (prn "Promise rejected " {:error error#}))))

(defn flog [r] (log :flog r) r)


(defn pluralize
  [items & [word ending1 ending2 :as opts]]
  (let [n (count items)
        plural (case (count opts)
                 1 "s"
                 2 ending1
                 3 ending2)
        singular (case (count opts)
                   (list 1 2) ""
                   3 ending1)]
    (str n " " word (if (== 1 n) singular plural))))

(defn date-format [date fmt & [tz]]
  (let [formatter (goog.i18n.DateTimeFormat. fmt)]
    (if tz
      (.format formatter date tz)
      (.format formatter date))))

(def days-map
  {:Su "S" :Mo "M" :Tu "T" :We "W" :Th "T" :Fr "F" :Sa "S"})

(def as-days
  (let [days (-> days-map keys set)]
    (-> (map #(% {:Su 7 :Sa 6 :Fr 5 :Th 4 :We 3 :Tu 2 :Mo 1})
             days)
        set)))

(defn select-date [date]
  (as-days (t/day-of-week date)))

(defn maybe-keyword [s]
  (if (keyword? s) s (keyword s)))

(defn read-string [s]
  (reader/read-string s))

(defn dissoc-in [m key id]
  (->> (get m key)
       (remove #(= (:id %) id ))
       (assoc m key)))
