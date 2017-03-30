(ns metagator.events
    (:require [re-frame.core :as re-frame]
              [ajax.core :refer [GET POST]]
              [metagator.parser :as parser]
              [metagator.meta :refer [make-triples]]
              [metagator.types :refer [detect-type]]
              [metagator.db :as db]))

(def HOST "http://mist.cs.bath.ac.uk:3000")

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
 :dname-change
 (fn [db [_ dname]]
   (assoc db :dataset-name dname)))

(re-frame/reg-event-db
 :upload-file
 (fn [db [_ fname]]
   (let [url (first (array-seq (.-files (.getElementById js/document "file"))))]
     (do
       (parser/parse-sample url 10)
       (assoc db :url (str "http://www.cs.bath.ac.uk/dm4t/" (.-name url)))))))

(re-frame/reg-event-db
 :fetch
 (fn [db [_ fname]]
   (do
     (parser/parse-sample fname 10)
     db)))

(re-frame/reg-event-db
 :set-types
 (fn [db [_ rows]]
   (let [types (map detect-type (first rows))]
     (assoc db :row-types types))))

(re-frame/reg-event-db
 :update-description
 (fn [db [_ description]]
   (assoc db :description description)))

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
     (assoc-in db [:metas tab] (conj (nth (:metas db) tab) {:cat-a nil :cat-b nil :cat-c nil :label nil})))))

(re-frame/reg-event-db
 :add-file-meta
 (fn [db _]
   (assoc db :file-metas (conj (:file-metas db) {:cat-a nil :cat-b nil :cat-c nil :label nil}))))

(re-frame/reg-event-db
 :set-cat-a
 (fn [db [_ cat-id row-id]]
   (let [tab (:selected-tab db)
         cat-label (re-frame/subscribe [:cat-label cat-id])]
     (assoc-in db [:metas tab row-id :cat-a] @cat-label))))

(re-frame/reg-event-db
 :set-cat-b
 (fn [db [_ cat-b cat-a row-id]]
   (let [tab (:selected-tab db)
         cat-bs (re-frame/subscribe [:cat-b-for-cat-a cat-a])
         cat-label (:label (nth @cat-bs cat-b))
         ]
     (assoc-in db [:metas tab row-id :cat-b] cat-label))))

(re-frame/reg-event-db
 :set-cat-c
 (fn [db [_ cat-c cat-b row-id]]
   (let [tab (:selected-tab db)
         cat-cs (re-frame/subscribe [:cat-c-for-cat-b cat-b])
         cat-label (:label (nth @cat-cs cat-c))
         ]
     (assoc-in db [:metas tab row-id :cat-c] cat-label))))

(re-frame/reg-event-db
 :set-file-cat-a
 (fn [db [_ cat-id row-id]]
   (let [cat-label (re-frame/subscribe [:cat-label cat-id])]
     (assoc-in db [:file-metas row-id :cat-a] @cat-label))))


(re-frame/reg-event-db
 :set-file-cat-b
 (fn [db [_ cat-id cat-a row-id]]
   (let [cat-label (re-frame/subscribe [:cat-label cat-id])]
     (assoc-in db [:file-metas row-id :cat-b] @cat-label))))

(re-frame/reg-event-db
 :set-file-cat-c
 (fn [db [_ cat-id cat-b row-id]]
   (let [cat-label (re-frame/subscribe [:cat-label cat-id])]
     (assoc-in db [:file-metas row-id :cat-c] @cat-label))))

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


(re-frame/reg-event-db
 :set-file-metadata
 (fn [db [_ meta-id cat row-id]]
   (let [
         metas (re-frame/subscribe [:metas-for-cat cat])
         label (:label (nth @metas meta-id))
         meta-data (re-frame/subscribe [:meta-for-label label])
         ]
     (assoc-in db [:file-metas row-id :metadata] @meta-data)
     ;; db
     )))

(defn drop-nth [coll n]
  (keep-indexed #(if (not= %1 n) %2) coll))

(re-frame/reg-event-db
 :delete-row
 (fn [db [_ row-id]]
   (let [tab (:selected-tab db)]
     (assoc-in db [:metas tab] (vec (drop-nth (nth (:metas db) tab) row-id))))))


(re-frame/reg-event-db
 :delete-file-row
 (fn [db [_ row-id]]
   (assoc db :file-metas (vec (drop-nth (:file-metas db) row-id)))))

(re-frame/reg-event-db
 :set-label
 (fn [db [_ label-data row-id]]
   (let [tab (:selected-tab db)
         ]
     (assoc-in db [:metas tab row-id :label] label-data)
     ;; db
     )))

(re-frame/reg-event-db
 :set-file-label
 (fn [db [_ label-data row-id]]
   (assoc-in db [:file-metas row-id :label] label-data)
   ;; db
   ))

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
 :load-turtle-handler
 (fn [db [_ response]]
   (println response)
   db))

(re-frame/reg-event-db
 :bad-response
 (fn [db [_ response]]
   (println response)
   db))

(defn download [fname text]
  (let [element (.createElement js/document "a")
        ]
    (do
      (.setAttribute element "href" (str "data:text/plain;charset=utf-8," (js/encodeURIComponent text)))
      (.setAttribute element "download" fname)
      (set! (.-display (.-style element)) "none")
      (.appendChild (.-body js/document) element)
      (.click element)
      (.removeChild (.-body js/document) element)
      )))

(re-frame/reg-event-db
 :download-turtle
 (fn [db _]
   (let [hmap {:name (:dataset-name db)
               :description (:description db)
               :url (:url db)
               :columns (:metas db)
               }]
     (download (str (:dataset-name db) ".ttl") (make-triples hmap)))

   db))

(re-frame/reg-event-db
 :create-metadata
 (fn [db _]
   (let [metas (map :metadata (:metas db))
         hmap {:name (:dataset-name db)
               :description (:description db)
               :url (:url db)
               :columns (:metas db)
               }]
     (println (make-triples hmap))
     (POST (str HOST "/add/") {:params {:file (make-triples hmap)}
                               :headers {:content-type "application/json"
                                         :access-control-allow-origin "*"
                                         :access-control-allow-methods "GET, POST"
                                         :access-control-allow-headers "X-Custom-Header"
                                         }
                               :format :json
                               :handler #(re-frame/dispatch [:load-turtle-handler %1])
                               :bad-response #(re-frame/dispatch [:bad-response %1])})
     db)))
