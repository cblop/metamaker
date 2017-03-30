(ns metagator.repl
  (:require [cljsjs.papaparse]
            [re-frame.core :as re-frame]))

(def db (re-frame/subscribe [:db]))

(:name @db)
(:description @db)

(:url @db)

(:metas @db)

(:datatypes @db)

(:datatypes @db)
(vec (set (map #(get % :parent) (:data-types @db))))
(map :parent (:datatypes @db))
(first (:datatypes @db))
(def metas (re-frame/subscribe [:metas-for-cat "time"]))
(def gmetas (re-frame/subscribe [:get-meta]))
;; (def gmetas (re-frame/subscribe [:selected-meta [] 0]))
(def cat-label (re-frame/subscribe [:cat-label 0]))

@gmetas

@cat-label

@metas

(def rows (re-frame/subscribe [:rows]))
(def types (re-frame/subscribe [:datatypes]))

(= (type "hello") js/String)

(first (:rows @db))
(:row-types @db)
(:metas @db)
(:headers @db)
(:fname @db)

(first rows)

(defn stepfn [results parser]
  (println (str "Row data:" (first (.-data results)))))

(.parse js/Papa "http://mist.cs.bath.ac.uk/refit-cleaned/CLEAN_House1.csv"
        (clj->js {:download true
                  :chunk stepfn
                  ;; :dynamic-typing true
                  :header true
                  ;; :worker true
                  :preview 100
                  }))


(.parse js/Papa "/home/_mthom/DM4T/refit-clean/CLEAN_House1.csv"
        (clj->js {:download true
                  :chunk stepfn
                  :dynamic-typing true
                  :header true
                  :worker true}))

;; @types

;; (:datatypes @db)

;; (:ftype2 @db)
