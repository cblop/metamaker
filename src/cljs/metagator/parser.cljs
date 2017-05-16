(ns metagator.parser
  (:require
   [re-frame.core :as re-frame]
   [cljsjs.papaparse]))


(set! (.-SCRIPT_PATH js/Papa) "js/papaparse.min.js")

;; (defn stepfn [results parser]
;;   (if (< (rand) 0.00001)
;;     (println (str "Row data:" (js->clj (first (.-data results)))))))

(def line-no (atom 0))
(def chart-data (atom {:type "line"
                       :data {:labels []
                              :datasets [{:data []
                                          :label "Value"}]}}))

(mod 5 1)

(defn stepfn [results parser]
  ;; (println results)
  (let [clj-results (first (:data (js->clj results :keywordize-keys true)))
        x-index (js/parseInt @(re-frame/subscribe [:x]))
        y-index (js/parseInt @(re-frame/subscribe [:y]))
        srate (js/parseInt @(re-frame/subscribe [:srate]))
        ]
    ;; (println (str "Row data:" clj-results))
    (if (and (>= (count clj-results) y-index) (> @line-no 0) (= 0 (mod @line-no srate)))
      (do
        (reset! chart-data (assoc-in @chart-data [:data :labels] (conj (:labels (:data @chart-data)) (nth clj-results x-index))))
        (reset! chart-data (assoc-in @chart-data [:data :datasets 0 :data] (conj (:data (first (:datasets (:data @chart-data)))) (nth clj-results y-index))))
        (re-frame/dispatch [:set-chart-data @chart-data])
        ;; (re-frame/dispatch [:add-chart-data {:label (nth clj-results t-index) :data (nth clj-results v-index)}])
        ))
    (reset! line-no (inc @line-no))
    )
  )


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

(defn parse-remote-sample [fname size]
  (.parse js/Papa fname
          (clj->js {
                    :download true
                    :dynamicTyping true
                    ;; :step stepfn
                    :complete complete
                    ;; :header true
                    ;; :worker true
                    :preview size
                    })))


(defn parse-local [fname]
  (do
    (reset! chart-data {:type "line"
                        :data {:labels []
                               :datasets [{:data []
                                           :label "Value"}]}})
    (.parse js/Papa fname
            (clj->js {
                      :download false
                      :dynamicTyping true
                      :step stepfn
                      ;; :complete complete
                      :header false
                      ;; :worker true
                      ;; :preview size
                      }))))


(defn parse-stream [fname]
  (do
    (reset! chart-data {:type "line"
                        :data {:labels []
                               :datasets [{:data []
                                           :label "Value"}]}})
    (.parse js/Papa fname
            (clj->js {
                      :download true
                      :dynamicTyping true
                      :step stepfn
                      ;; :complete complete
                      :header false
                      ;; :worker true
                      ;; :preview size
                      }))))

;; (clj->js {:download true
;;           :dynamic-typing true
;;           :header true
;;           :worker true
;;           :preview 100})
