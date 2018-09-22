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

