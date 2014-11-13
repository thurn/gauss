(ns clojurescript.core
 (:require
  [cljs.core.async :as async
    :refer [<! >! chan close! sliding-buffer put! alts! timeout]]
  [weasel.repl :as weasel])
 (:require-macros [cljs.core.async.macros :as m :refer [go alt!]]))
(weasel/connect "ws://localhost:9001" :verbose true)

(def ch (chan 1))

(go
  (>! ch "hello")
  (js/alert (<! ch)))

(def AssetLoader (.-AssetLoader js/PIXI))
(def BaseTexture (.-BaseTexture js/PIXI))
(def Texture (.-Texture js/PIXI))
(def CanvasRenderer (.-CanvasRenderer js/PIXI))
(def WebGLRenderer (.-WebGLRenderer js/PIXI))
(def Stage (.-Stage js/PIXI))
(def DisplayObjectContainer (.-DisplayObjectContainer js/PIXI))
(def Sprite (.-Sprite js/PIXI))
(def MovieClip (.-MovieClip js/PIXI))
(def Graphics (.-Graphics js/PIXI))
(def Text (.-Text js/PIXI))
(def Point (.-Point js/PIXI))

(def stage (Stage. 0x66FF99))
(def renderer (.autoDetectRenderer js/PIXI 400 300))
(.appendChild (.-body js/document) (.-view renderer))

(def texture (.fromImage Texture "bunny.png"))
(def bunny (Sprite. texture))
(set! (.-x (.-anchor bunny)) 0.5)
(set! (.-y (.-anchor bunny)) 0.5)
(set! (.-x (.-position bunny)) 200)
(set! (.-y (.-position bunny)) 150)

(.addChild stage bunny)

(defn animate []
  (js/requestAnimFrame animate)
  (set! (.-rotation bunny) (+ 0.1 (.-rotation bunny)))
  (.render renderer stage))

(js/requestAnimFrame animate)
