(ns depointed.model
  (:require [cortex.nn.layers :as layers]))

(defn model
  [width height classes]
  [(layers/input width height 1 :id :data)
   (layers/convolutional 5 0 1 20)
   (layers/max-pooling 2 0 2)
   (layers/relu)
   (layers/convolutional 5 0 1 50)
   (layers/max-pooling 2 0 2)
   (layers/batch-normalization)
   (layers/linear 1000)
   (layers/relu :center-loss {:label-indexes {:stream :labels}
                              :label-inverse-counts {:stream :labels}
                              :labels {:stream :lables}
                              :alpha 0.9
                              :lambda 1e-4})
   (layers/dropout 0.5)
   (layers/linear classes)
   (layers/softmax :id :labels)])
