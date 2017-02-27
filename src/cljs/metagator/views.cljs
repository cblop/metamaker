(ns metagator.views
  (:require [re-frame.core :as re-frame]
            [re-com.core :refer [input-text input-textarea title v-box h-box gap label button single-dropdown horizontal-tabs vertical-pill-tabs md-circle-icon-button]]
            [reagent.core :as reagent]))

(defn filename []
  (let [fname (re-frame/subscribe [:fname])]
    (fn []
      [input-text
       :model fname
       :on-change #(re-frame/dispatch [:fname-change %])
       :width "80%"])))

(defn dataset-name []
  (let [dname (re-frame/subscribe [:dname])]
    (fn []
      [v-box
       :gap "10px"
       :children [[label
                   :label "Name of the dataset:"]
                  [input-text
                   :model dname
                   :on-change #(re-frame/dispatch [:dname-change %])
                   :width "40%"]]])))

(defn description []
  (let [description (re-frame/subscribe [:description])]
    (fn []
      [v-box
       :gap "10px"
       :children [[label
                   :label "Description of the dataset:"]
                  [input-textarea
                   :model description
                   :on-change #(re-frame/dispatch [:update-description])
                   :width "90%"]]])))

(defn fbutton []
  (let [fname (re-frame/subscribe [:fname])]
    (fn []
      [button
       :label "Fetch"
       :class "btn-primary"
       :on-click #(re-frame/dispatch [:fetch @fname])])))

(defn rows-meta [type]
  (let [types (re-frame/subscribe [:data-types])
        metas (if (= type :file) (re-frame/subscribe [:get-file-meta]) (re-frame/subscribe [:get-meta]))
        cats (vec (set (map :cat-a @types)))
        file (if (= type :file) true false)
        catmap (map (fn [x i] (hash-map :id i :label x)) cats (range (count cats)))
        id-for-label (fn [label] (:id (first (filter #(= (:label %) label) catmap))))
        ]
    (fn []
      [:div
       (for [[i m] (map-indexed vector @metas)]
         [h-box
          :padding "20px"
          :style {:background-color (if (= type :file) "#dfd" "#eef")
                  :margin-bottom "10px"}
          :children [
                     [v-box
                      :width "20%"
                      :children
                      [
                       [label
                        :label "Category:"]
                       [single-dropdown
                        :model (id-for-label (:cat-a m))
                        :on-change (if file #(re-frame/dispatch [:set-file-cat-a % i]) #(re-frame/dispatch [:set-cat-a % i]))
                        :width "90%"
                        :choices catmap
                        ]]]
                     (if (:cat-a m)
                       (let [cat-b-filter (re-frame/subscribe [:cat-b-for-cat-a (:cat-a m)])
                             selected-cat-b (if file (re-frame/subscribe [:selected-file-cat-b @cat-b-filter i]) (re-frame/subscribe [:selected-cat-b @cat-b-filter i]))]
                         [v-box
                          :width "20%"
                          :children
                          [
                           [label
                            :label "Sub-category:"]
                           [single-dropdown
                            :model @selected-cat-b
                            :on-change (if file #(re-frame/dispatch [:set-file-cat-b % (:cat-a m) i]) #(re-frame/dispatch [:set-cat-b % (:cat-a m) i]))
                            :width "90%"
                            :choices @cat-b-filter
                            ]]]))
                     (if (:cat-b m)
                       (let [cat-c-filter (re-frame/subscribe [:cat-c-for-cat-b (:cat-b m)])
                             selected-cat-c (if file (re-frame/subscribe [:selected-file-cat-c @cat-c-filter i]) (re-frame/subscribe [:selected-cat-c @cat-c-filter i]))]
                         (if (seq @cat-c-filter)
                           [v-box
                            :width "20%"
                            :children
                            [
                             [label
                              :label "Sub-sub-category:"]
                             [single-dropdown
                              :model @selected-cat-c
                              :on-change (if file #(re-frame/dispatch [:set-file-cat-c % (:cat-b m) i]) #(re-frame/dispatch [:set-cat-c % (:cat-b m) i]))
                              :width "90%"
                              :choices @cat-c-filter
                              ]]])))
                     (if (:cat-c m)
                       [v-box
                        :width "30%"
                        :children
                        [
                         [label
                          :label "Label (optional):"]
                         [input-text
                          :model (if (:label m) (:label m) "")
                          :on-change (if file #(re-frame/dispatch [:set-file-label % i]) #(re-frame/dispatch [:set-label % i]))
                          :width "90%"
                          ]]])
                     [v-box
                      :justify :end
                      :children [
                                 [button
                                  :label "Delete"
                                  :class "btn-danger"
                                  :on-click (if file #(re-frame/dispatch [:delete-file-row i]) #(re-frame/dispatch [:delete-row i]))]]]
                     ]
          ])]
      )))

(defn metadata []
  (fn []
    [v-box
     :children [
                [title
                 :level :level3
                 :label "File Metadata:"]
                [label
                 :label "This metadata is added to all rows in the table."]
                [gap
                 :size "20px"]
                [rows-meta :file]
                [gap
                 :size "20px"]
                [button
                 :label "Add Metadata"
                 :class "btn-primary"
                 :on-click #(re-frame/dispatch [:add-file-meta])]
                ]]))

(defn dtype []
  (let [selected (re-frame/subscribe [:selected-tab])
        types (re-frame/subscribe [:types])
        type (re-frame/subscribe [:selected-type])]
    (fn []
      [v-box
       ;; :padding "20px"
       :width "100%"
       :children [
                  [title
                   :level :level3
                   :label "Data Type:"]
                  [single-dropdown
                   :model @type
                   :width "40%"
                   :style (if @type {:background-color "#bbffbb"
                                     :padding "5px"} {:padding "5px"})
                   :on-change #(re-frame/dispatch [:update-type %])
                   :choices @types]
                  ]])))

(defn column []
  (fn []
    [v-box
     :padding "20px"
     :children [
                [dtype]
                ;; [metadata]
                [gap
                 :size "20px"]
                [title
                 :level :level3
                 :label "Metadata:"]
                [rows-meta :column]
                [gap
                 :size "20px"]
                [button
                 :label "Add Metadata"
                 :class "btn-primary"
                 :on-click #(re-frame/dispatch [:add-meta])]
                ]]))

(defn columns []
  (let [headers (re-frame/subscribe [:row-tabs])
        selected (re-frame/subscribe [:selected-tab])]
    (fn []
      [v-box
       :width "90%"
       :children [
                  [gap
                   :size "20px"]
                  [metadata]
                  [gap
                   :size "40px"]
                  [horizontal-tabs
                   :model @selected
                   :tabs @headers
                   :on-change #(re-frame/dispatch [:change-tab %])]
                  [column]
                  ]])))


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
     :margin "0 0 50px 0"
     :children [
                [button
                 :style {:width "200px"}
                 :label "Create Metadata!"
                 :class "btn-success"]]]))

(defn main-panel []
  (let [heads (re-frame/subscribe [:row-tabs])]
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
                    [gap
                     :size "10px"]
                    [dataset-name]
                    [gap
                     :size "10px"]
                    [description]
                    (if @heads
                      [:div
                       [columns]
                       [gap
                        :size "20px"]
                       [go-button]])
                    ]
                   ]
                  ]])))
