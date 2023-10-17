(ns mrhota.alphabetsuperset.e
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def increment 0.01)

(defn my-noise [x y i]
  (* (q/noise (* i x) (* i y)) 255))

(defn setup []
  (let [scl 5
        cols (q/floor (/ (q/width) scl))
        rows (q/floor (/ (q/height) scl))
        y-vals (take-nth scl (range (+ (q/height) scl)))
        x-vals (flatten (map #(repeat (count y-vals) %)
                             (take-nth scl (range (+ (q/width) scl)))))
        corners (partition 2 (interleave x-vals (cycle y-vals)))]
    {:scale scl
     :cols cols
     :rows rows
     :x-vals x-vals
     :y-vals y-vals
     :corners corners}))

(defn update-state [state]
  state)

(defn draw [{:keys [scale corners]} state]
  (q/background 255)
  (q/stroke-weight .5)
  (doseq [[x y] corners]
    (q/fill (my-noise x y increment))
    (q/rect x y scale scale)))


#_{:clj-kondo/ignore [:unresolved-symbol]}
(q/defsketch elevation-map
  :host "app"
  :size [400 400]
  :setup setup
  :update update-state
  :draw draw
  :middleware [m/fun-mode])
