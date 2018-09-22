(ns dojo2048.letters)

(defn long-str [& strings] (clojure.string/join "\n" strings))

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

(defn convert-digit [digit]
  (case digit
    "1" one
    "2" two
    "3" three
    "4" four
    "5" five
    "6" six
    "7" seven
    "8" eight
    "9" nine))
 
(defn convert-number [number]
  ((str number) map convert-digit))
