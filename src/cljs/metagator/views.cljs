(ns metagator.views
  (:require [re-frame.core :as re-frame]
            [re-com.core :refer [input-text input-textarea title v-box h-box gap label button single-dropdown horizontal-tabs vertical-pill-tabs]]
            [reagent.core :as reagent]))

(defn filename []
  (let [fname (re-frame/subscribe [:fname])]
    (fn []
      [input-text
       :model fname
       :on-change #()
       :width "80%"])))

(defn description []
  (let [description (re-frame/subscribe [:description])]
    (fn []
      [v-box
       :gap "10px"
       :children [[label
                   :label "Description of the dataset:"]
                  [input-textarea
                   :model description
                   :on-change #()
                   :width "90%"]]])))

(defn fbutton []
  (fn []
    [button
     :label "Fetch"
     :class "btn-primary"
     :on-click #()]))

(defn rows []
  (let [width "12.5%"]
    (fn []
      [v-box
       :gap "10px"
       :margin "0 20% 0 0"
       :children [
                  [h-box
                   :justify :center
                   :children [
                              [title
                               :level :level2
                               :label "Rows"]]]
                  [gap
                   :size "5px"]
                  [h-box
                   :margin "0 0 0 -10%"
                   :gap "10px"
                   :children [
                              [v-box
                               :gap "10px"
                               :margin "41px 0 0 0"
                               :children [
                                          [label
                                           :class "row-header"
                                           :label "Data Type:"]
                                          [label
                                           :class "row-header"
                                           :label "Reading Type:"]
                                          [label
                                           :class "row-header"
                                           :label "Source:"]
                                          [label
                                           :class "row-header"
                                           :label "Unit:"]
                                          [label
                                           :class "row-header"
                                           :label "Frequency:"]
                                          ]]
                              [v-box
                               :gap "10px"
                               :children [
                                          [label
                                           :class "column-header"
                                           :label "Time"]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "time (mysql)"}]]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "Time"}]]
                                          [label
                                           :class "table-data na"
                                           :label "N/A"]
                                          [label
                                           :class "table-data na"
                                           :label "N/A"]
                                          [label
                                           :class "table-data na"
                                           :label "N/A"]
                                          ]
                               ]
                              [v-box
                               :gap "10px"
                               :children [
                                          [label
                                           :class "column-header"
                                           :label "Unix"]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "integer"}]]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "Unix timestamp"}]]
                                          [label
                                           :class "table-data na"
                                           :label "N/A"]
                                          [label
                                           :class "table-data na"
                                           :label "N/A"]
                                          [label
                                           :class "table-data na"
                                           :label "N/A"]
                                          ]
                               ]
                              [v-box
                               :gap "10px"
                               :children [
                                          [label
                                           :class "column-header"
                                           :label "Appliance_1"]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "integer"}]]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "Power"}]]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "Fridge 1"}]]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "Watts"}]]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "Every minute"}]]
                                          ]
                               ]
                              [v-box
                               :gap "10px"
                               :children [
                                          [label
                                           :class "column-header"
                                           :label "Appliance_2"]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "integer"}]]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "Power"}]]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "Heater 3"}]]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "Watts"}]]
                                          [single-dropdown
                                           :model 0
                                           :on-change #()
                                           :width "100%"
                                           :choices [{:id 0 :label "Every minute"}]]
                                          ]
                               ]
                              ]]
                  ]])))

(defn metadata []
  (fn []
    [v-box
     :children [
                [title
                 :level :level3
                 :label "Column Metadata:"]
                [label
                 :label "This metadata is added to all rows in the column."]
                [gap
                 :size "20px"]
                [button
                 :label "Add"
                 :class "btn-primary"
                 :on-click #()]
                ]]))

(defn rows-meta []
  (fn []
    ))

(defn column []
  (fn []
    [v-box
     :padding "20px"
     :children [
                [metadata]
                [gap
                 :size "20px"]
                [title
                 :level :level3
                 :label "Value Metadata:"]
                [label
                 :label "This metadata describes the type of values in each row of the column."]
                ]]))

(defn columns []
  (fn []
    [v-box
     :width "90%"
     :children [
                [title
                 :level :level2
                 :label "Columns:"]
                [gap
                 :size "10px"]
                [horizontal-tabs
                 :model (reagent/atom ::tab1)
                 :tabs [
                        {:id ::tab1 :label "Time"}
                        {:id ::tab2 :label "Unix"}
                        {:id ::tab3 :label "Appliance_1"}
                        {:id ::tab4 :label "Appliance_2"}
                        {:id ::tab5 :label "Appliance_3"}
                        ]
                 :on-change #()]
                [column]
                ]]))

(defn file-type []
  (let [choices (re-frame/subscribe [:ftypes])
        ftype (re-frame/subscribe [:ftype])
        ftype2 (re-frame/subscribe [:ftype2])
        ftype3 (re-frame/subscribe [:ftype3])
        f1 (nth @choices @ftype)
        f2 (nth (:types f1) @ftype2)
        f3 (nth (:types f2) @ftype3)
        ]
    (fn []
      [h-box
       :gap "10px"
       :children [
                  [h-box
                   :gap "10px"
                   :children [
                              [label
                               :label "It contains"
                               :style {:margin-top "10px"}]
                              [single-dropdown
                               :choices @choices
                               :model @ftype
                               :on-change #(re-frame/dispatch [:ftype %])]
                              (if f2
                                [label
                                 :label (:post f1)
                                 :style {:margin-top "10px"}])
                              (if f2
                                [single-dropdown
                                 :choices (:types f1)
                                 :model @ftype2
                                 :on-change #(re-frame/dispatch [:ftype2 %])])
                              (if (:post f2)
                                [label
                                 :label (:post f2)
                                 :style {:margin-top "10px"}])
                              (if f3
                                [single-dropdown
                                 :model @ftype3
                                 :on-change #(re-frame/dispatch [:ftype3 %])
                                 :choices (:types f3)]
                                [input-text
                                 :model ""
                                 :on-change #()])
                              ]]
                  ]
       ])))

(defn page-title []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [title
       :label @name
       :level :level1])))

(defn go-button []
  (fn []
    [h-box
     :justify :center
     :width "80%"
     :children [
                [button
                 :style {:width "200px"}
                 :label "Create Metadata!"
                 :class "btn-success"]]]))

(defn main-panel []
  (fn []
    [v-box
     :height "100%"
     :gap "20px"
     :children [
                [h-box
                 :justify :center
                 :children [
                            [page-title]]]
                [v-box
                 :margin "0 0 0 15%"
                 :gap "10px"
                 :children
                 [
                  [label
                   :label "Path to remote CSV file:"]
                  [h-box
                   :gap "5px"
                   :children [
                              [filename]
                              [fbutton]]]
                  ;; [gap
                  ;;  :size "10px"]
                  ;; [file-type]
                  [gap
                   :size "10px"]
                  [description]
                  [columns]
                  [gap
                   :size "20px"]
                  [go-button]
                  ]
                 ]
                ]]))
