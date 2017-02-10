(ns metagator.repl
  (:require [cljsjs.papaparse]
            [re-frame.core :as re-frame]))

(def db (re-frame/subscribe [:db]))

(:ftype2 @db)

