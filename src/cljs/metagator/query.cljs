(ns metagator.query
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [metagator.parser :as parser]
            [cljsjs.chartjs]
            [reagent.core :as reagent]))


(defn title []
  (fn []
    [re-com/title
     :label "MetaMaker"
     :level :level1]))

(defn localfile-b []
  (let [fname (re-frame/subscribe [:fname])]
    (fn []
      [:input {:type "file" :id "file" :name "file"
               :on-change #(re-frame/dispatch [:upload-file %])}])))

(defn dataset-drop []
  (let [datasets (re-frame/subscribe [:datasets])
        selected (re-frame/subscribe [:selected-sets])]
    [re-com/h-box
     :justify :center
     :gap "10px"
     :children [[re-com/label
                 :label "Datasets:"]
                [re-com/selection-list
                 :width "300px"
                 :max-height "100px"
                 :choices datasets
                 :multi-select? false
                 :model selected
                 :on-change #(re-frame/dispatch [:set-dataset %])]
                [re-com/md-circle-icon-button
                 :md-icon-name "zmdi-refresh"
                 ;; :style {:margin-top "10px"}
                 :emphasise? true
                 :on-click #(re-frame/dispatch [:get-datasets])]
                ]]))

(defn sparql-text []
  (let [sparql (re-frame/subscribe [:sparql])]
    [re-com/h-box
     :justify :center
     :children [
                [re-com/input-textarea
                 :model sparql
                 :width "60%"
                 :rows 10
                 :style {:font-family "monospace"}
                 :on-change #(re-frame/dispatch [:update-sparql %])]]]))

(defn send-query []
  [re-com/h-box
   :justify :center
   :children [
              [re-com/button
               :label "Send Query"
               :class "btn-success"
               :on-click #(re-frame/dispatch [:send-sparql])]]])

(defn cat-a-drop [i]
  (let [selected-cat-a (re-frame/subscribe [:selected-cat-qa i])
        cat-as (re-frame/subscribe [:cat-as])]
    [re-com/h-box
     :justify :center
     :gap "10px"
     :children [
                [re-com/single-dropdown
                 :model selected-cat-a
                 :on-change #(re-frame/dispatch [:change-cat-a i %])
                 :choices cat-as
                 :width "150px"]]]))

(defn cat-b-drop [i]
  (let [selected-cat-b (re-frame/subscribe [:selected-cat-qb i])
        filtered-cats (re-frame/subscribe [:filtered-cats i])]
    [re-com/h-box
     :justify :center
     :gap "10px"
     :children [
                [re-com/single-dropdown
                 :model selected-cat-b
                 :on-change #(re-frame/dispatch [:change-cat-b i %])
                 :choices filtered-cats
                 :width "150px"]]]))

(defn text-filter []
  [re-com/input-text
   :model ""
   :on-change #()])


(defn sample-rate []
  (let [srate (re-frame/subscribe [:sample-rate])]
    [re-com/h-box
     :gap "10px"
     :children [
                [re-com/label :label "Sample every "]
                [re-com/input-text
                 :model srate
                 :on-change #(re-frame/dispatch [:update-srate])]
                [re-com/label :label " readings."]
                ]]))

(defn cat-select []
  (let [trips (re-frame/subscribe [:selected-cats])]
    [:div
     (for [i (range (count @trips))]
       [re-com/v-box
        :children [
                   [re-com/h-box
                    :justify :center
                    :gap "10px"
                    :children [
                               (if (= i 0) [re-com/label
                                            :label "X axis:"])
                               (if (= i 1) [re-com/label
                                            :label "Y axis:"])
                               [re-com/label
                                :label "Category"]
                               [cat-a-drop i]
                               [re-com/label
                                :label "Subcategory"]
                               [cat-b-drop i]
                               ;; (if (> i 0)
                               ;;   [re-com/v-box
                               ;;    :justify :end
                               ;;    :children [
                               ;;               [re-com/button
                               ;;                :label "Delete"
                               ;;                :class "btn-danger"
                               ;;                :on-click #(re-frame/dispatch [:delete-q-row i])]
                               ;;               ]])
                               ;; [re-com/label
                               ;;  :label "Filter"]
                               ;; [text-filter]
                               ]]
                   [re-com/gap
                    :size "20px"]]])]))

(defn chart-inner []
  (let [chart (atom nil)
        update (fn [comp]
                 (set! (.-labels (.-data @chart)) (clj->js (:labels (reagent/props comp))))
                 ;; (set! (.-data (first (.-datasets (.-data @chart)))) (clj->js (:data (reagent/props comp))))
                 (set! (.-datasets (.-data @chart)) (clj->js (:datasets (reagent/props comp))))
                 ;; (set! (.-data @chart) (clj->js (reagent/props comp)))
                 (println (.-data @chart))
                 (println (reagent/props comp))
                 (.update @chart)
                 )
                 ;; (.addData @chart (:data (reagent/props comp)) (:labels (reagent/props comp))))
        ]
    (reagent/create-class
     {:reagent-render (fn [] [:canvas {:id "chart" :width 600 :height 400}])
      :component-did-mount (fn [comp]
                             (let [context (.getContext (.getElementById js/document "chart") "2d")
                                   data {:type "bar"
                                         :data {:labels []
                                                :datasets [{:data []
                                                            :label "Values"}]}}
                                   obj (js/Chart. context (clj->js data))
                                   ]
                               (do
                                 (reset! chart obj)
                                 ;; (update comp)
                                 ))
                             (update comp))
      :component-did-update update
      :display-name "chart-inner"})))

(defn add-triple []
  [re-com/h-box
   :justify :center
   :children [
              [re-com/md-circle-icon-button
               :md-icon-name "zmdi-plus"
               :emphasise? true
               :on-click #(re-frame/dispatch [:add-triple])]]])

(defn get-readings []
  [re-com/h-box
   :justify :center
   :children [
              [re-com/button
               :label "Get Readings"
               :class "btn-success"
               :on-click #()]]])


