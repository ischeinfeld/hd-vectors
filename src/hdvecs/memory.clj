(ns hdvecs.memory
  (:use [clojure.core.matrix])
  (:require [hdvecs.ops :as ops]
            [clojure.algo.generic.functor :as func]))

(defn create-memory
  [dim]
  (atom {:items {}
         :dim dim}))

(defn set-item!
  ([item memory]
   (do
     (swap! memory assoc-in [:items item] (ops/rand-vec (:dim @memory)))
     memory))

  ([item repr memory]
   (do
;     (assert (= (type repr) (type (matrix :double-array [0]))))
;     (assert (= (shape repr) (vector (:dim @memory)))) ;;TODO add :vectorz check
     (swap! memory assoc-in [:items item] repr)
     memory)))

(defn get-item
  [item memory]
  (get-in @memory [:items item]))

(defn reconstruct
  [v memory]
  (let [sims (func/fmap #(ops/similarity v %) (:items @memory))]
    (key (apply max-key val sims))))

(defn view-item
  [item memory]
  (let [v (get-item item memory)
        size (count v)
        idxs [1 2 3 (- size 3) (- size 2) (- size 1)]
        view (if (< size 6)
               (vec v)
               (vec (select-indices v idxs)))]
    (str item " has size " size " and elements " view)))
