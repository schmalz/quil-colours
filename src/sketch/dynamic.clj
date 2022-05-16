(ns sketch.dynamic
  (:require [clojure.pprint :as pp]
            [quil.core :as q]
            #_[colours.core :as colours]))

(def colour
  
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

(defn initialise
  []
  (q/smooth)
  (q/color-mode :hsb 360 100 100 1.0))

(defn paint-gradient
  [hue-low hue-high]
  (q/no-stroke)
  (doseq [y (range 0 900 3)]
    (q/fill (q/map-range y 0 900 hue-low hue-high)
            32
            100)
    (q/rect 0 y 900 3)))

(defn paint-shape
  [hue]
  (q/stroke hue 32 75) ; Mute the hue slightly for the shape's outline.
  (q/fill hue 32 100)
  (q/begin-shape)
  (q/vertex (rand-int (q/width)) (rand-int (q/height)))
  (dotimes [_ 3]
    (q/bezier-vertex (rand-int (q/width)) (rand-int (q/height))
                     (rand-int (q/width)) (rand-int (q/height))
                     (rand-int (q/width)) (rand-int (q/height))))
  (q/end-shape :close))

(defn paint-shapes
  [hue]
  (dotimes [_ 3]
    (paint-shape hue)))

(defn save-frame-to-disk
  ([]
   (q/save-frame (pp/cl-format nil
                               "frames/~d-~2,'0d-~2,'0d-~2,'0d-~2,'0d-~2,'0d-####.jpeg"
                               (q/year) (q/month) (q/day)
                               (q/hour) (q/minute) (q/seconds))))
  ([state _]
   (save-frame-to-disk)
   state))

(defn draw
  []
  (q/no-loop)
  (paint-gradient (get-in colour [:navajo-white :bg-hue-low])
                  (get-in colour [:navajo-white :bg-hue-high]))
  (paint-shapes (get-in colour [:navajo-white :shape-fill]))
  (save-frame-to-disk))
