(ns sorting)

(defn merge-step [list1 list2]
  (cond
      (nil? (first list1)) list2
      (nil? (first list2)) list1
      :else (if (cust-comp (first list1) (first list2))
              (cons (first list2) (lazy-seq (merge-step list1 (rest list2))))
              (cons (first list1) (lazy-seq (merge-step (rest list1) list2))))))
 
(defn merge-sort [the-list] 
  (if (<= (count the-list) 1) 
    the-list
    (let [[list1 list2] (split-at (quot (count the-list) 2) the-list)] 
      (merge-step (merge-sort list1) (merge-sort list2)))))
