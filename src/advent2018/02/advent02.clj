(ns advent02
  (:require [clojure.string :as cs]))

(def ids (cs/split-lines (slurp "resources/input02.txt")))

;; #1
(defn check-freq [n id]
  (some #(= % n) (vals (frequencies id))))
(defn freq-ids [n ids]
  (count (filter true? (map (partial check-freq n) ids))))
(println (* (freq-ids 2 ids) (freq-ids 3 ids)))

;; #2
(defn print-same-char [c1 c2]
  (if (= c1 c2) c1))
(defn printres [id1 id2]
  (println (apply str (map print-same-char id1 id2))))
(defn compare-char [c1 c2]
  (if (= c1 c2) 0 1))
(defn compare-id [id1 id2]
  (let [sum (reduce + (map compare-char id1 id2))]
    (if (= sum 1)
      (printres id1 id2))
    sum))
(defn check-id [id ids]
  (doall (map (partial compare-id id) ids)))
(defn check-ids [ids]
  (let [fid (first ids)
        rid (rest ids)
        res (check-id fid rid)]
     (if-not (empty? rid)
      (recur rid))))
(check-ids ids)
