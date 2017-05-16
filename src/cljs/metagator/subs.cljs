(ns metagator.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :dname
 (fn [db]
   (:dataset-name db)))

(re-frame/reg-sub
 :db
 (fn [db]
   db))

(re-frame/reg-sub
 :datasets
 (fn [db]
   (:datasets db)))

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
   (let [cats (vec (set (map :cat-a (:datatypes db))))
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
 :cat-b-for-cat-a
 (fn [db [_ cat-label]]
   (let [matches (filter #(= (:cat-a %) cat-label) (:datatypes db))]
     (map (fn [x i] (hash-map :id i :label x)) (vec (set (map :cat-b matches))) (range (count matches))))
   ))


(re-frame/reg-sub
 :cat-c-for-cat-b
 (fn [db [_ cat-label]]
   (let [matches (filter #(= (:cat-b %) cat-label) (:datatypes db))
         ]
     (if (seq (remove nil? (map :cat-c matches)))
       (map (fn [x i] (hash-map :id i :label x)) (map :cat-c matches) (range (count matches)))))
   ))

(re-frame/reg-sub
 :get-meta
 (fn [db]
   (let [row (:selected-tab db)]
     (nth (:metas db) row))))

(re-frame/reg-sub
 :get-file-meta
 (fn [db]
   (:file-metas db)))

(re-frame/reg-sub
 :selected-cat-b
 (fn [db [_ metas i]]
   (let [metam (re-frame/subscribe [:get-meta]) ; metadata for all rows
         m (:cat-b (nth @metam i)) ; metadata label for this row
         match (:id (first (filter #(= m (:label %)) metas))) ; index of metadata label in dropdown that matches
         ]
     match
     )))

(re-frame/reg-sub
 :selected-file-cat-b
 (fn [db [_ metas i]]
   (let [metam (re-frame/subscribe [:get-file-meta]) ; metadata for all rows
         m (:cat-b (:metadata (nth @metam i))) ; metadata label for this row
         match (:id (first (filter #(= m (:label %)) metas))) ; index of metadata label in dropdown that matches
         ]
     match
     )))

(re-frame/reg-sub
 :selected-cat-c
 (fn [db [_ metas i]]
   (let [metam (re-frame/subscribe [:get-meta]) ; metadata for all rows
         m (:cat-c (nth @metam i)) ; metadata label for this row
         match (:id (first (filter #(= m (:label %)) metas))) ; index of metadata label in dropdown that matches
         ]
     match
     )))

(re-frame/reg-sub
 :selected-file-cat-c
 (fn [db [_ metas i]]
   (let [metam (re-frame/subscribe [:get-file-meta]) ; metadata for all rows
         m (:cat-c (:metadata (nth @metam i))) ; metadata label for this row
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

(defn match-meta [a b]
  (and
   (= (:label a) (:label b))
   (= (:cat-c a) (:cat-c b))
   (= (:cat-b a) (:cat-b b))
   (= (:cat-a a) (:cat-a b))
   ))

(re-frame/reg-sub
 :p-for-meta
 (fn [db [_ meta]]
   (let [matches (filter #(match-meta meta %) (:datatypes db))
         p (:p (first matches))]
     p)))

(re-frame/reg-sub
 :types
 (fn [db]
   (:types db)))


(re-frame/reg-sub
 :selected-sets
 (fn [db]
   (:selected-sets db)))

(re-frame/reg-sub-raw
 :current-tab
 (fn [db _]
   (reaction (:current-tab @db))))

(re-frame/reg-sub
 :description
 (fn [db]
   (:description db)))

(re-frame/reg-sub
 :fname
 (fn [db]
   (:fname db)))

(re-frame/reg-sub
 :srate
 (fn [db]
   (:srate db)))

(re-frame/reg-sub
 :x
 (fn [db]
   (:x db)))

(re-frame/reg-sub
 :y
 (fn [db]
   (:y db)))

(re-frame/reg-sub
 :sparql
 (fn [db]
   (:sparql db)
   (let [cat-as (:cat-qa db)
         cat-bs (:cat-qb db)
         filtered (:filtered-cats db)
         ]
     (if-not (and (nil? (second cat-bs)) (nil? (first cat-bs)))
       (str "PREFIX csv:<http://www.ntnu.no/ub/data/csv#>\n"
            "PREFIX ssn:<http://purl.oclc.org/NET/ssnx/ssn#>\n"
            "PREFIX seas:<https://w3id.org/seas#>\n"
            "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>\n"
            "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n"
            "SELECT *\n"
            "WHERE {{"
            "SELECT ?f ?x WHERE {\n"
            "                    ?f rdfs:label \"" (:dataset db) "\" .\n"
            "                    ?f csv:hasColumn ?c .\n"
            "                    ?c csv:mapsTo " (:p (nth (:cat-bs db) (first cat-bs))) " .\n"
            "                    ?c csv:hasIndex ?x .}\n"
            "} UNION {\n"
            "  SELECT ?y WHERE {\n"
            "                    ?f rdfs:label \"" (:dataset db) "\" .\n"
            "                   ?f csv:hasColumn ?cy .\n"
            "                   ?cy csv:mapsTo " (:p (nth (:cat-bs db) (second cat-bs))) " .\n"
            "                   ?cy csv:hasIndex ?y .}}}\n"
            )
       "")
     )
   ))

(re-frame/reg-sub
 :selected-cats
 (fn [db]
   (:cat-qa db)))

(re-frame/reg-sub
 :selected-cat-a
 (fn [db [_ i]]
   (nth (:cat-qa db) i)))

(re-frame/reg-sub
 :cat-as
 (fn [db]
   (:cat-as db)))

(re-frame/reg-sub
 :chart-data
 (fn [db]
   (:chart-data db)))

(re-frame/reg-sub
 :data-urls
 (fn [db]
   (let [resp (js->clj (.parse js/JSON (get (:response db) "out")) :keywordize-keys true)
         results (get-in resp [:results :bindings])]
     (into [] (set (map #(hash-map :label (get-in % [:label :value]) :url (get-in % [:url :value])) results)))
     )))

(re-frame/reg-sub
 :filtered-cats
 (fn [db [_ i]]
   (nth (:filtered-cats db) i)))


(re-frame/reg-sub
 :cat-bs
 (fn [db]
   (:cat-bs db)))

(re-frame/reg-sub
 :selected-cat-qa
 (fn [db [_ i]]
   (nth (:cat-qa db) i)))

(re-frame/reg-sub
 :selected-cat-qb
 (fn [db [_ i]]
   (nth (:cat-qb db) i)))
