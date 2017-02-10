(ns metagator.events
    (:require [re-frame.core :as re-frame]
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
 :ftype3
 (fn [db [_ type]]
   (assoc db :ftype3 type)))
