(ns metagator.query
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [metagator.parser :as parser]
            [reagent.core :as reagent]))


(defn title []
  (fn []
    [re-com/title
     :label "MetaMaker"
     :level :level1]))

(defn dataset-drop []
  [re-com/h-box
   :justify :center
   :gap "10px"
   :children [[re-com/label
               :label "Datasets:"]
              [re-com/selection-list
               :width "300px"
               :max-height "100px"
               :choices (reagent/atom [
                                       {:id 0 :label "refit"}
                                       {:id 1 :label "enliten"}
                                       {:id 2 :label "APATSCHE"}
                                       ])
               :model (reagent/atom (set ["0"]))
               :on-change #()]
              ]])

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

(defn cat-select []
  (let [trips (re-frame/subscribe [:selected-cats])]
    [:div
     (for [i (range (count @trips))]
       [re-com/v-box
        :children [
                   [re-com/h-box
                    :justify :center
                    :gap "10px"
                    :children [[re-com/label
                                :label "Category"]
                               [cat-a-drop i]
                               [re-com/label
                                :label "Subcategory"]
                               [cat-b-drop i]
                               ;; [re-com/label
                               ;;  :label "Filter"]
                               ;; [text-filter]
                               ]]
                   [re-com/gap
                    :size "20px"]]])]))

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

