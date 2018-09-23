(ns dojo2048.core
  (:require [lanterna.screen :as s]
            [clojure.string :as str])
  (:gen-class))

(def empty-board
  [[nil nil nil nil]
   [nil nil nil nil]
   [nil nil nil nil]
   [nil nil nil nil]])


(defn get-empty [board]
  (let [empties
        (for [x (range 4)
              y (range 4)
              :when (nil? (get-in board [y x]))]
          [x y])]
    (and (seq empties)
         (rand-nth empties))))


(defn add-new [board]
  (let [[x y] (get-empty board)
        v (rand-nth [2 2 2 2 4])]
    (and x y (assoc-in board [y x] v))))


(defn init-board []
  (-> empty-board
      add-new
      add-new))
  

(defn left [board]
  (mapv
   (fn [x] (let [reduced
            (loop [res []
                   rem (remove nil? x)]
              (let [f (first rem)
                    s (second rem)
                    [nres nrem]
                    (if (and f s (= f s))
                      [(conj res (+ f s)) (drop 2 rem)]
                      [(conj res f) (drop 1 rem)])]
                (if (empty? nrem)
                  nres
                  (recur nres nrem))))]
            (vec (take 4 (concat reduced (repeat nil))))))
   board))

(defn right [board]
  (->> board
       (map reverse)
       left
       (mapv reverse)
       (mapv vec)))

(defn transpose [m]
  (apply mapv vector m))

(defn up [board]
  (->> board
       transpose
       left
       transpose))

(defn down [board]
  (->> board
       transpose
       right
       transpose))

(defn next-state [board input]
  (let [x ((case input
             :left left
             :right right
             :up up
             :down down) board)]
    (add-new x)))

(def splitter "-------------------------")

(defn board-to-screen [board scr]
  (s/put-string scr 0 0  splitter)
  (doseq [[i ii] (map vector (range 1 12 2) (range 4))]
    (let [x (str/join
             "|" (map #(if (nil? %) "     " (format "%5d" %)) (get board ii)))]
      (s/put-string scr 0  i (str "|" x "|")))
    (s/put-string scr 0 (inc i) splitter)
    (s/redraw scr)))

(defn -main []
  (let [scr (s/get-screen :swing {:cols 25 :rows 9})]
    (s/start scr)
    (loop [board (init-board)]
      (board-to-screen board scr)
      (let [i (loop [inp (s/get-key-blocking scr)]
                (or (#{:up :down :left :right} inp)
                    (recur (s/get-key-blocking scr))))
            next (next-state board i)]
        (if (nil? next)
          (do
            (s/clear scr)
            (s/put-string scr 0 0 "GAME OVER!")
            (s/redraw scr))
          (recur next))))
    (s/get-key-blocking scr)
    (s/stop scr)))
