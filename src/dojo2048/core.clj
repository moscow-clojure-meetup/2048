(ns dojo2048.core
  (:require [lanterna.screen :as s]
            [clojure.string :as str])
  (:gen-class))

(def empty-board
  [[nil nil nil nil]
   [nil nil nil nil]
   [nil nil nil nil]
   [nil nil nil nil]])

;; if nil --> game over
(defn get-empty [board]
  (let [empties
        (into [] (for [x (range 4)
                       y (range 4)
                       :when (nil? (get-in board [y x]))]
                   [x y]))]
    (rand-nth empties)))


(defn add-new [board]
  (let [[x y] (get-empty board)
        v (rand-nth [2 4])]
    (assoc-in board [y x] v)))


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
        (take 4 (concat reduced (repeat nil)))))
   board))

(defn right [board]
  (->> board
       (map reverse)
       left
       (mapv reverse)))

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
  (add-new
   ((case input
      :left left
      :right right
      :up up
      :down down) board)))

(defn print-board [board]
  (doseq [r board]
    (println r)))

(defn board-to-screen [board scr]
  (for [i (range 4)]
    (s/put-string scr 10 (+ 10 i) "AAA")))

(defn -main []
  (let [scr (s/get-screen)]
    (s/start scr)

    (loop [board (init-board)]
      (doseq [i (range 4)]
        (let [x (str/join "|" (map #(if (nil? %) "     " (format "%5d" %)) (get board i)))]
          (s/put-string scr 10 (+ 10 i) (str "|" x "|")))
        (s/redraw scr))
      (let [i (loop [inp (s/get-key-blocking scr)]
                (if (#{:up :down :left :right} inp)
                  inp
                  (recur (s/get-key-blocking scr))))

            next (next-state board i)]
        (if (nil? next)
          (do
            (s/put-string scr 15 15 "GAME OVER!")
            (s/redraw scr))
          (recur next))))
    (s/get-key-blocking scr)
    (s/stop scr)))
