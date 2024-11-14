(ns sorting (:use clojure.pprint))

(defn cust-comp [a b] 
  (cond
    (>= (compare a b) 0) true
    (< (compare a b) 0) false))

(def VALID-CHARS
  (map char (range 97 123)))

(defn random-char []
  (nth VALID-CHARS (rand (count VALID-CHARS))))

(defn random-str [length]
  (apply str (take length (repeatedly random-char))))

(load-file "./bubblesort/sort.clj")
(load-file "./quicksort/sort.clj")
(load-file "./mergesort/sort.clj")

(def num-in-list 10)
(defn rand-int-list [] (take num-in-list (repeatedly #(rand-int 100))))
(defn rand-float-list [] (take num-in-list (repeatedly #(rand 100))))
(defn rand-string-list [] (take num-in-list (repeatedly #(random-str 5))))

(println "ASCENDING")
(println)

(println "Bubble Sort")
(pprint (bubble-sort (rand-int-list)))
(pprint (bubble-sort (rand-float-list)))
(println (bubble-sort (rand-string-list)))
(println)

(println "Quick Sort")
(pprint (quick-sort (rand-int-list)))
(pprint (quick-sort (rand-float-list)))
(println (quick-sort (rand-string-list)))
(println)

(println "Merge Sort")
(pprint (merge-sort (rand-int-list)))
(pprint (merge-sort (rand-float-list)))
(println (merge-sort (rand-string-list)))
(println)

(defn cust-comp [a b] 
  (cond
    (>= (compare a b) 0) false
    (< (compare a b) 0) true))

(println "DESCENDING")
(println)

(println "Bubble Sort")
(pprint (bubble-sort (rand-int-list)))
(pprint (bubble-sort (rand-float-list)))
(println (bubble-sort (rand-string-list)))
(println)

(println "Quick Sort")
(pprint (quick-sort (rand-int-list)))
(pprint (quick-sort (rand-float-list)))
(println (quick-sort (rand-string-list)))
(println)

(println "Merge Sort")
(pprint (merge-sort (rand-int-list)))
(pprint (merge-sort (rand-float-list)))
(println (merge-sort (rand-string-list)))

