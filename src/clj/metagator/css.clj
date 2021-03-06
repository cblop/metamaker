(ns metagator.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:.row-header {:font-weight "bold"
                   :background-color "#eee"
                   :padding "7px 20px"
                   :width "100%"
                   :text-align :right}]
  [:.column-header {:font-weight "bold"
                 :background-color "#eee"
                 :padding "5px 20px"
                 :width "100%"
                 :text-align :center}]
  [:.na {:color "#ccc"
         :margin "2px 0"
         }]
  [:.table-data {
                   :padding "5px 20px"
                   :width "100%"
                   :text-align :center}]
  )
