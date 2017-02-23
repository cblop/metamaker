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
  (let [fname (re-frame/subscribe [:fname])]
    (fn []
      [button
       :label "Fetch"
       :class "btn-primary"
       :on-click #(re-frame/dispatch [:fetch @fname])])))



(defn rows-meta [type]
  (let [types (re-frame/subscribe [:data-types])
        metas (if (= type :file) (re-frame/subscribe [:get-file-meta]) (re-frame/subscribe [:get-meta]))
        cats (vec (set (map :parent @types)))
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
                      :width "30%"
                      :children
                      [
                       [label
                        :label "Category:"]
                       [single-dropdown
                        :model (id-for-label (:category m))
                        :on-change (if file #(re-frame/dispatch [:set-file-category % i]) #(re-frame/dispatch [:set-category % i]))
                        :width "90%"
                        :choices catmap
                        ]]]
                     (if (:category m)
                       (let [metafilter (re-frame/subscribe [:metas-for-cat (:category m)])
                             selected-meta (if file (re-frame/subscribe [:selected-file-meta @metafilter i]) (re-frame/subscribe [:selected-meta @metafilter i]))]
                         [v-box
                          :width "30%"
                          :children
                          [
                           [label
                            :label "Metadata:"]
                           [single-dropdown
                            :model @selected-meta
                            :on-change (if file #(re-frame/dispatch [:set-file-metadata % (:category m) i]) #(re-frame/dispatch [:set-metadata % (:category m) i]))
                            :width "90%"
                            :choices @metafilter
                            ]]]))
                     (if (:metadata m)
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
                    ;; [gap
                    ;;  :size "10px"]
                    ;; [file-type]
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
