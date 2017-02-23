(ns metagator.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))


(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))


(re-frame/reg-sub
 :db
 (fn [db]
   db))

(re-frame/reg-sub
 :rows
 (fn [db]
   (:rows db)))

(re-frame/reg-sub
 :selected-tab
 (fn [db]
   (:selected-tab db)))

(re-frame/reg-sub
 :selected-type
 (fn [db]
   (let [type (nth (:row-types db) (:selected-tab db))
         match (first (filter #(= (:label %) type) (:types db)))]
     (if match (:id match) nil))))

(re-frame/reg-sub
 :cat-label
 (fn [db [_ id]]
   (let [cats (vec (set (map :parent (:datatypes db))))
         catmap (map (fn [x i] (hash-map :id i :label x)) cats (range (count cats)))
         label-for-id (fn [id] (:label (first (filter #(= (:id %) id) catmap))))]
     (label-for-id id)
     ;; cats
     )
   ))

(re-frame/reg-sub
 :meta-for-label
 (fn [db [_ meta-label]]
   (let [matches (filter #(= (:label %) meta-label) (:datatypes db))]
     (first matches))))

(re-frame/reg-sub
 :metas-for-cat
 (fn [db [_ cat-label]]
   (let [matches (filter #(= (:parent %) cat-label) (:datatypes db))]
     (map (fn [x i] (hash-map :id i :label x)) (map :label matches) (range (count matches))))
   ;; cat-label
   ;; (map (fn [x i] (hash-map :id i :label x)) (map :label (filter #(= (:parent %) cat-label) (:datatypes db))))
   ;; (map (fn [x i]))(:datatypes db)
   ))

(re-frame/reg-sub
 :get-meta
 (fn [db]
   (let [row (:selected-tab db)]
     (nth (:metas db) row))))


(re-frame/reg-sub
 :selected-meta
 (fn [db [_ metas i]]
   (let [metam (re-frame/subscribe [:get-meta]) ; metadata for all rows
         m (:label (:metadata (nth @metam i))) ; metadata label for this row
         match (:id (first (filter #(= m (:label %)) metas))) ; index of metadata label in dropdown that matches
         ]
     match
     )))

(re-frame/reg-sub
 :row-tabs
 (fn [db]
   (let [heads (:headers db)]
     (if heads
       (map (fn [x i] (hash-map :id i :label x)) heads (range (count heads)))
       nil
       ))))

(re-frame/reg-sub
 :data-types
 (fn [db]
   (:datatypes db)))

(re-frame/reg-sub
 :types
 (fn [db]
   (:types db)))

(re-frame/reg-sub
 :description
 (fn [db]
   (:description db)))

(re-frame/reg-sub
 :fname
 (fn [db]
   (:fname db)))

