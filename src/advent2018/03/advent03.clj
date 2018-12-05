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
(defn overlap [lines]
  (into {} (filter #(> (val %) 1) (apply (partial merge-with +) (map map-from lines)))))
(println (count (overlap lines)))

(defn nooverlap [lines]
  (into {} (filter #(= (val %) 1) (apply (partial merge-with +) (map map-from lines)))))

;; #2
(defn full-in? [m2 m1]
  (let [m2-filtered (filter #(get m1 (key %)) m2)]
    (if (= (count m1) (count m2-filtered))
      m1)))
;; (full-in? (map-from "#1 @ 749,666: 27x15") (map-from "#1 @ 749,666: 27x15"))
;; (full-in? (map-from "#1 @ 749,666: 27x15") (map-from "#1 @ 749,665: 27x15"))

(defn find-id [mmm pieces]
  (let [ls (map first (keys mmm))
        l (apply min ls)
        w (+ 1 (- (apply max ls) l))
        ts (map second (keys mmm))
        t (apply min ts)
        h (+ 1 (- (apply max ts) t))]
    (println l t w h)
    (:id (first (filter #(and (= (:l %) l) (= (:t %) t) (= (:h %) h) (= (:w %) w)) pieces)))))
;; (find-id (map-from "#1 @ 749,666: 27x15") (map parse lines))

(defn process [lines]
  (let [pieces (map parse lines)
        maps (map map-from lines)
        noover (nooverlap lines)
        ;;mmm (first (remove nil? (map (partial full-in? noover) maps)))
        mmm (first (remove nil? (map (partial full-in? noover) maps)))]
    (find-id mmm pieces)
    ;;mmm
    ))

(println (process lines))
