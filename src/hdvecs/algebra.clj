(ns hdvecs.algebra
  (:use [hdvecs.memory]
        [hdvecs.ops]))

(defn dollar-expand
  [form x]
  (cond
    (not (seq? form)) `(get-item ~form ~x)
    (= (first form) '+) `(compose ~@(map #(dollar-expand % x) (rest form)))
    (= (first form) '*) `(encode ~@(map #(dollar-expand % x) (rest form))) ;~x for mem
    :else (assert false)))

(defn dollar-search
  [form x]
  (cond
    (not (seq? form)) form
    (not (= (first form) '$)) `(~(first form) ~@(map #(dollar-search % x)
                                                     (rest form)))
    :else (dollar-expand (second form) x)))

(use 'hdvecs.memory :reload)
(defmacro memory->>
  [x & forms]
  (loop [x x, forms forms]
    (if forms
      (let [form (first forms)
            threaded (if (seq? form)
              (with-meta `(~@(dollar-search form x) ~x) (meta form))
              (list form x))]
        (recur threaded (next forms)))
      x)))


(commment (memory->> (create-memory 128)
                     (set-item! :a)
                     (set-item! :b)
                     (set-item! :c)
                     (set-item! :trace ($ (+ (* :a :b)
                                             (* :c :a)))))

          (as-> (create-memory 128)
                (set-item! :a $)
                (set-item! :b $)
                (set-item! :c $)
                (set-item! :trace (compose (encode (get-item :a $)
                                                   (get-item :b $))
                                           (encode (get-item :c $)
                                                   (get-item :a $)))
                           $))) 

(comment 
  (dollar-expand ':ab  'x)
  (dollar-search '(aaa (bbb ($ (+ :a :b)))) 'x))

(comment (use 'hdvecs.memory :reload)
         (use 'hdvecs.ops :reload))
