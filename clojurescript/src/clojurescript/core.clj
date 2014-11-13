(ns clojurescript.core
 (:require
  [weasel.repl.websocket :as wsocket]
  [cemerick.piggieback :as piggieback]))

(defn cljs []
  (piggieback/cljs-repl :repl-env (wsocket/repl-env)))

(defn dthurn2 []
  (when true (+ 3 4))
  (prn "dthurn2"))

(defn pos [col value]
  "Returns the key associated with val in col, or nil if val is not present in col"
  (let [col-map (if (vector? col) (zipmap (range (count col)) col) col)]
    (some (fn [x] (if (= value (val x)) (key x))) col-map)))
