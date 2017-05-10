(ns metagator.parser
  (:require
   [re-frame.core :as re-frame]
   [cljsjs.papaparse]))

(set! (.-SCRIPT_PATH js/Papa) "js/papaparse.min.js")

;; (defn stepfn [results parser]
;;   (if (< (rand) 0.00001)
;;     (println (str "Row data:" (js->clj (first (.-data results)))))))


(defn stepfn [results parser]
  (println (str "Row data:" (js->clj (first (.-data results))))))


(defn complete [results, parser]
  (let [clj-results (:data (js->clj results :keywordize-keys true))]
    (do
      (println (str "Table data:" (js->clj results :keywordize-keys true)))
      (re-frame/dispatch [:set-rows clj-results])
      )))


(defn parse-sample [fname size]
  (.parse js/Papa fname
          (clj->js {
                    :download false
                    :dynamicTyping true
                    ;; :step stepfn
                    :complete complete
                    ;; :header true
                    ;; :worker true
                    :preview size
                    })))


(defn parse-stream [fname]
  (.parse js/Papa fname
          (clj->js {
                    :download false
                    :dynamicTyping true
                    :step stepfn
                    ;; :complete complete
                    ;; :header true
                    ;; :worker true
                    ;; :preview size
                    })))

;; (clj->js {:download true
;;           :dynamic-typing true
;;           :header true
;;           :worker true
;;           :preview 100})
