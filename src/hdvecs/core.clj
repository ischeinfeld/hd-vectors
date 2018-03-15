(ns hdvecs.core
  (:use [hdvecs.memory]
        [hdvecs.ops]))

(def memory
  (->> (create-memory 128)
       (set-item! :a)
       (set-item! :b)
       (set-item! :c)))

(set-item! :group
           (compose (get-item :a memory)
                    (get-item :b memory)
                    (get-item :c memory)) 
           memory)

(similarity (get-item :a memory) (get-item :b memory))
(similarity (get-item :a memory) (get-item :group memory))

(set-item! :ab (encode (get-item :a memory) (get-item :b memory)) memory)
(set-item! :a? (decode (get-item :b memory) (get-item :ab memory)) memory)

(similarity (get-item :a memory) (get-item :a? memory))

(reconstruct (decode (get-item :a memory) (get-item :ab memory)) memory)

(view-item :a memory)

(comment (use 'hdvecs.memory :reload)
         (use 'hdvecs.ops :reload))
