(ns sorting)

(defn bubble-swap [the-list]
  (if (or (nil? the-list) (nil? (second the-list)))
    the-list
    (if (cust-comp (first the-list) (second the-list))
      (cons (second the-list) (cons (first the-list) (nthrest the-list 2)))
      (cons (first the-list) (bubble-swap (rest the-list))))))

(defn bubble-sort [a-list]
  (if (= a-list (bubble-swap a-list))
    a-list
    (recur (bubble-swap a-list))))
 

