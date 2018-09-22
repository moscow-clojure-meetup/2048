(ns dojo2048.core)

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
  


##_(defn next-state [board ])

