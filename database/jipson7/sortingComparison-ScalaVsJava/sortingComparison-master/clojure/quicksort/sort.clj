(ns sorting)

(defn quick-sort [[pivot & the-rest]]
  (when pivot
    (let [smaller #(cust-comp pivot %)]
      (lazy-cat (quick-sort (filter smaller the-rest))
    [pivot]
    (quick-sort (remove smaller the-rest))))))

