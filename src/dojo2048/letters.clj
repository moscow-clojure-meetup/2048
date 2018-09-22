(ns dojo2048.letters)

(defn long-str [& strings] strings)

(def zero (long-str " _ "
                    "/ \\"
                    "\\_/"))

(def one (long-str "   "
                   "/| "
                   " | "))

(def two (long-str "_  "
                   " ) "
                   "/_ "))

(def three (long-str "_  "
                     "_) "
                     "_) "))

(def four (long-str "   "
                    "|_|" 
                    "  |"))

(def five (long-str " _ "
                    "|_ "
                    " _)"))

(def six (long-str " _ "
                   "|_ "
                   "|_)"))

(def seven (long-str "__ "
                     " / "
                     "/  "))

(def eight (long-str " _ "
                     "(_)"
                     "(_)"))

(def nine (long-str " _ "
                    "(_|"
                    "  |"))

(def e (long-str "   " "   " "   "))

(defn convert-digit [digit]
  (case (read-string (str digit))
    0 zero
    1 one
    2 two
    3 three
    4 four
    5 five
    6 six
    7 seven
    8 eight
    9 nine
    e))
 
(defn convert-number [number]
  (let [normalized (case (count (str number))
                      1 (str "!!!" number)
                      2 (str "!!" number)
                      3 (str "!" number)
                      4 (str number))]
    (map convert-digit normalized)))
 
(defn print-number [number]
  (for [i (range 3)]
    (do
      (doall 
        (for [j (range (count number))]
          (print (nth (nth number j) i)))) 
      (println ""))))

