(ns hdvecs.visualize
  (:use [clojure.core.matrix]
        [clojure.core.matrix.operators])
  (:require [think.tsne.core :as tsne]
            [com.hypirion.clj-xchart :as c]
            ))

(defn dim-reduce
  [memory-state]
  (let [items (keys (:items memory-state))
        item-vecs (vals (:items memory-state))
        m (matrix :vectorz item-vecs)
        reduced-m (matrix :vectorz
                            (tsne/tsne m 2
                                       :tsne-algorithm :slow-standard-tsne))]
    (assoc memory-state :items (zipmap items (rows reduced-m)))))

(defn bubblify
  [dim-reduced]
  (let [symbols (vals (select-keys (:items dim-reduced) (:symbols dim-reduced)))
        groups (vals (select-keys (:items dim-reduced) (:groups dim-reduced)))
        ]
    (zipmap 
            
            )
    
    )
  )

(defn chart-groups
  [memory-state]
  (let [dim-reduced (dim-reduce memory-state)
        symbols (vals (select-keys (:items dim-reduced) (:symbols dim-reduced)))
        groups (vals (select-keys (:items dim-reduced) (:groups dim-reduced)))
        ]
    (c/view 
      (c/bubble-chart*
        {"Symbols" {:x (map first symbols)
                    :y (map second symbols)
                    :bubble (repeat (count symbols) 8)}}
        {:title "Title"
         :legent {:position :inside-ne}
         :y-axis {:title "y axis title"}
         :x-axis {:title "x axis title"}}))))
