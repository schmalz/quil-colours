(ns sketch.core
  (:require [quil.core :as q]
            [sketch.dynamic :as dynamic])
  (:gen-class))

(def sketch)

(q/defsketch sketch
  :title "sketch"
  :setup dynamic/initialise
  :draw dynamic/draw
  :size [900 900])

(defn refresh
  []
  (use :reload 'dynamic)
  (.loop sketch))
