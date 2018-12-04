(ns advent03
  (:require [clojure.string :as cs]
            [clojure.math.combinatorics :as combi]))

(def lines (cs/split-lines (slurp "resources/input03.txt")))
(def pattern #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)")
(defrecord Piece [id l t w h])
(defn piece-from [values]
  (apply #(Piece. %1 %2 %3 %4 %5) values))

(defn parse [s]
  (piece-from (map read-string (rest (re-matches pattern s)))))
;;(parse "#1 @ 749,666: 27x15")

(defn map-from [s]
  (let [piece (parse s)
        rng1 (range (:l piece) (+ (:l piece) (:w piece)))
        rng2 (range (:t piece) (+ (:t piece) (:h piece)))
        tuples (combi/cartesian-product rng1 rng2)]
    (zipmap tuples (replicate (count tuples) 1))))
;;(map-from "#1 @ 749,666: 27x15")

;; #1
(defn process [ids]
  (count (filter #(> % 1)
                 (vals (apply (partial merge-with +)
                              (map map-from ids))))))
(println (process lines))
