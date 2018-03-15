(ns hdvecs.ops
  (:use [clojure.core.matrix]
        [clojure.core.matrix.operators]
        [clojure.core.matrix.random]
        [hdvecs.math])) 

(defn rand-vec
  [n]
  (do
    (set-current-implementation :double-array)
    (normalise! (sample-normal n))))

(defn compose
  [& vecs]
  (normalise (apply + vecs)))

(defn encode
  [v1 v2]
  (normalise (cconv v1 v2)))

(defn decode
  [v1 v2]
  (normalise (ccorr v1 v2)))

(defn similarity
  [v1 v2]
  (dot v1 v2))
