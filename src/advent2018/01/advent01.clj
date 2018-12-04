(ns advent01
  (:require [clojure.string :as cs]))

(def freqs (map read-string (cs/split-lines (slurp "resources/input01.txt"))))

;; #1
(println (reduce + 0 freqs))

;; #2
(def old-freqs (atom #{}))
(def continue (atom true))
(def start-freq (atom 0))
(defn +freqs [^long prev ^long delta]
  (let [next (+ prev delta)]
    ;;(println prev delta next (take 3 @old-freqs) (contains? @old-freqs next))
    (if @continue
      (if (contains? @old-freqs next)
        (do
          (reset! continue false)
          (println next))
        (reset! old-freqs (conj @old-freqs next))))
    next))

(do
  (reset! old-freqs #{})
  (reset! continue true)
  (reset! start-freq 0)
  (while @continue
    (reset! start-freq (reduce +freqs @start-freq freqs))))
