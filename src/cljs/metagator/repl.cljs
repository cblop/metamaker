(ns metagator.repl
  (:require 
            [re-frame.core :as re-frame]))

(def db (re-frame/subscribe [:db]))
(def types (re-frame/subscribe [:datatypes]))

;; @types

;; (:datatypes @db)

;; (:ftype2 @db)

