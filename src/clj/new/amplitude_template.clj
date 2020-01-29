(ns clj.new.amplitude-template
  (:require [clj.new.templates :refer [renderer project-data ->files]]))

(defn amplitude-template
  "FIXME: write documentation"
  [name]
  (let [render (renderer "amplitude-template")
        data   (project-data name)]
    (println "Generating fresh 'clj new' amplitude-template project.")
    (->files data
             ["deps.edn" (render "deps.edn" data)]
             ["src/{{nested-dirs}}/foo.clj" (render "foo.clj" data)])))
