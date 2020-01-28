(ns dynamic
  (:require [clojure.pprint :as pp]
            [quil.core :as q]))

(def colours
  
  ; Use saturation and brightness = 90.
  {:yellow-green-brown {:bg-hue-low 60
                        :bg-hue-high 90
                        :shape-fill 30}
   
  ; Use saturation = 6, brightness = 100.
   :floral-white {:bg-hue-low 160
                  :bg-hue-high 280
                  :shape-fill 40}
   
   ; Use saturation = 32, brightness = 100.
   :navajo-white {:bg-hue-low 276
                  :bg-hue-high 156
                  :shape-fill 36}})

(defn setup
  []
  (q/color-mode :hsb 360 100 100 1.0))

(defn paint-gradient
  [hue-low hue-high]
  (q/no-stroke)
  (doseq [y (range 0 800 5)]
    (q/fill (q/map-range y 0 800 hue-low hue-high)
            32
            100)
    (q/rect 0 y 800 5)))

(defn paint-shape
  [hue]
  (q/stroke 36 32 75)
  (q/fill hue 32 100)
  (q/begin-shape)
  (q/vertex (rand-int (q/width)) (rand-int (q/height)))
  (dotimes [_ 11]
    (q/bezier-vertex (rand-int (q/width)) (rand-int (q/height))
                     (rand-int (q/width)) (rand-int (q/height))
                     (rand-int (q/width)) (rand-int (q/height))))
  (q/end-shape :close))

(defn- save-to-disk
  []
  (q/save-frame (pp/cl-format nil
                              "sketches/~d-~2,'0d-~2,'0d-~2,'0d-~2,'0d-~2,'0d.jpeg"
                              (q/year) (q/month) (q/day) (q/hour) (q/minute) (q/seconds))))

(defn draw
  []
  (q/no-loop)
  (paint-gradient (get-in colours [:navajo-white :bg-hue-low])
                  (get-in colours [:navajo-white :bg-hue-high]))
  (paint-shape (get-in colours [:navajo-white :shape-fill]))
  (save-to-disk))
