(ns dojo2048.core)

(def empty-board
  [[nil nil nil nil]
   [nil nil nil nil]
   [nil nil nil nil]
   [nil nil nil nil]])

(defn init-board []
  (let [board (make-array Long/TYPE 4 4)
        [x1 y1 x2 y2]
        (repeatedly 4 #(rand-int 4))]
    (if (= [x1 y1] [x2 y2])
      (init-board)
      (do
        (aset board x1 y1 2)
        (aset board x2 y2 2)
        board))))

#_ (defn next-state [board ])

