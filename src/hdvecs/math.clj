(ns hdvecs.math
  (:use [clojure.core.matrix]
        [clojure.core.matrix.operators]
        [clojure.core.matrix.random]))

;; based on mikera's solution to standard convolution
;; at https://stackoverflow.com/a/8256512

(defn cconv
  [^doubles v1 ^doubles v2] ;; size v1 = size v2 
  (let [size (count v1)
        out (double-array size)]
    (dotimes [i (int size)]
      (dotimes [j (int size)]
        (let [offset (mod (int (+ i j)) size)]
          (aset out offset (+ (aget out offset)
                                 (* (aget v2 i)
                                    (aget v1 j)))))))
    out))

(defn ccorr
  [^doubles v1 ^doubles v2] ;; size v1 = size v2 
  (let [size (count v1)
        out (double-array size)]
    (dotimes [i (int size)]
      (dotimes [j (int size)]
        (let [offset (mod (int (- i j)) size)]
          (aset out offset (+ (aget out offset)
                                 (* (aget v2 i)
                                    (aget v1 j)))))))
    out))
