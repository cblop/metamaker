(ns metagator.events
    (:require [re-frame.core :as re-frame]
              [metagator.parser :as parser]
              [metagator.types :refer [detect-type]]
              [metagator.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :ftype
 (fn [db [_ type]]
   (assoc db :ftype type)))


(re-frame/reg-event-db
 :ftype2
 (fn [db [_ type]]
   (assoc db :ftype2 type)))

(re-frame/reg-event-db
 :fname-change
 (fn [db [_ fname]]
   (assoc db :fname fname)))


(re-frame/reg-event-db
 :fetch
 (fn [db [_ fname]]
   (do
     (parser/parse-sample fname 100)
     db)))

(re-frame/reg-event-db
 :set-types
 (fn [db [_ rows]]
   (let [types (map detect-type (first rows))]
     (assoc db :row-types types))))

(re-frame/reg-event-db
 :update-type
 (fn [db [_ type]]
   (let [tab (:selected-tab db)
         new-type (first (filter #(= (:id %) type) (:types db)))]
     ;; (re-frame/dispatch [:set-selected-type type])
     (assoc db :row-types (assoc (vec (:row-types db)) tab (:label new-type)))
     )))

(re-frame/reg-event-db
 :set-selected-type
 (fn [db [_ type]]
   (assoc db :selected-type type)))

(re-frame/reg-event-db
 :set-headers
 (fn [db [_ rows]]
   (assoc db :headers (first rows))))

(re-frame/reg-event-db
 :add-meta
 (fn [db _]
   (let [tab (:selected-tab db)]
     (assoc-in db [:metas tab] (conj (nth (:metas db) tab) {:category nil :metadata nil})))))

(re-frame/reg-event-db
 :set-category
 (fn [db [_ cat-id row-id]]
   (let [tab (:selected-tab db)
         cat-label (re-frame/subscribe [:cat-label cat-id])]
     (assoc-in db [:metas tab row-id :category] @cat-label))))

;; (re-frame/reg-event-db
;;  :set-metadata
;;  (fn [db _]
;;    db))

(re-frame/reg-event-db
 :set-metadata
 (fn [db [_ meta-id cat row-id]]
   (let [tab (:selected-tab db)
         metas (re-frame/subscribe [:metas-for-cat cat])
         label (:label (nth @metas meta-id))
         meta-data (re-frame/subscribe [:meta-for-label label])
         ]
     (assoc-in db [:metas tab row-id :metadata] @meta-data)
     ;; db
     )))

(defn drop-nth [coll n]
  (keep-indexed #(if (not= %1 n) %2) coll))

(re-frame/reg-event-db
 :delete-row
 (fn [db [_ row-id]]
   (let [tab (:selected-tab db)]
     (assoc-in db [:metas tab] (drop-nth (nth (:metas db) tab) row-id)))))

(re-frame/reg-event-db
 :set-label
 (fn [db [_ label-data row-id]]
   (let [tab (:selected-tab db)
         ]
     (assoc-in db [:metas tab row-id :label] label-data)
     ;; db
     )))

(re-frame/reg-event-db
 :change-tab
 (fn [db [_ tab]]
   (assoc db :selected-tab tab)))

(re-frame/reg-event-db
 :create-metas
 (fn [db [_ rows]]
   (assoc db :metas (vec (repeat (count (first rows)) [])))))

(re-frame/reg-event-db
 :set-rows
 (fn [db [_ rows]]
   (do
     (re-frame/dispatch [:set-headers rows])
     (re-frame/dispatch [:create-metas rows])
     (re-frame/dispatch [:set-types (rest rows)])
     (assoc db :rows (rest rows)))))



(re-frame/reg-event-db
 :ftype3
 (fn [db [_ type]]
   (assoc db :ftype3 type)))
