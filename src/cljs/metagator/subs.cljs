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
 :ftypes
 (fn [db]
   (:ftypes db)))

(re-frame/reg-sub
 :description
 (fn [db]
   (:description db)))

(re-frame/reg-sub
 :ftype
 (fn [db]
   (:ftype db)))


(re-frame/reg-sub
 :ftype2
 (fn [db]
   (:ftype2 db)))

(re-frame/reg-sub
 :ftype3
 (fn [db]
   (:ftype3 db)))

(re-frame/reg-sub
 :fname
 (fn [db]
   (:fname db)))
