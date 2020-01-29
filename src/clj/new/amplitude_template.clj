(ns clj.new.amplitude-template
  "Generate an AWS Amplify / Clojurescript / Re-Frame project."
  (:require [clj.new.templates :refer [renderer project-data project-name ->files]]))

(defn amplitude-template
  "FIXME: write documentation"
  [name]
  (let [render (renderer "amplitude-template")
        data   (project-data name)]
    (println "Generating a project called"
             (project-name name)
             "based on the 'app' template.")
    (println "Data: " data)
    (->files data
             [".gitignore" (render "gitignore" data)]
             [".dir-locals.el" (render "dir-locals.el" data)]
             [".graphqlconfig.yml" (render "graphqlconfig.yml" data)]
             ["Makefile" (render "Makefile" data)]
             ["LICENSE.txt" (render "LICENSE.txt" data)]
             ["package.json" (render "package.json" data)]
             ["README.org" (render "README.org" data)]
             ["shadow-cljs.edn" (render "shadow-cljs.edn" data)]
             ["etc/schema.graphql" (render "etc/schema.graphql" data)]
             ["src/{{nested-dirs}}/foo.clj" (render "foo.clj" data)]
             ["src/{{nested-dirs}}/amplify.cljs" (render "src/amplify.cljs" data)]
             ["src/{{nested-dirs}}/core.cljs" (render "src/core.cljs" data)]
             ["src/{{nested-dirs}}/db.cljs" (render "src/db.cljs" data)]
             ["src/{{nested-dirs}}/events.cljs" (render "src/events.cljs" data)]
             ["src/{{nested-dirs}}/subs.cljs" (render "src/subs.cljs" data)]
             ["src/{{nested-dirs}}/util.cljs" (render "src/util.cljs" data)]
             ["src/{{nested-dirs}}/views.cljs" (render "src/views.cljs" data)]
             ["src/{{nested-dirs}}/amplify/analytics.cljs" (render "src/amplify/analytics.cljs" data)]
             ["src/{{nested-dirs}}/amplify/auth.cljs" (render "src/amplify/auth.cljs" data)]
             ["src/{{nested-dirs}}/amplify/cache.cljs" (render "src/amplify/cache.cljs" data)]
             ["src/{{nested-dirs}}/amplify/graphql.cljs" (render "src/amplify/graphql.cljs" data)]
             ["src/{{nested-dirs}}/amplify/pubsub.cljs" (render "src/amplify/pubsub.cljs" data)]
             ["src/{{nested-dirs}}/amplify/rest.cljs" (render "src/amplify/rest.cljs" data)]
             ["src/{{nested-dirs}}/amplify/storage.cljs" (render "src/amplify/storage.cljs" data)]
             ["src/{{nested-dirs}}/views/bootstrap.cljs" (render "src/views/bootstrap.cljs" data)]
             ["src/{{nested-dirs}}/views/container.cljs" (render "src/views/container.cljs" data)]
             ["src/{{nested-dirs}}/views/core.cljs" (render "src/views/core.cljs" data)]
             ["public/index.html" (render "public/index.html" data)]
             ["public/css/aws-amplify-ui-style.css" (render "public/css/aws-amplify-ui-style.css" data)]
             ["public/css/chosen-sprite.png" (render "public/css/chosen-sprite.png" data)]
             ["public/css/chosen-sprite@2x.png" (render "public/css/chosen-sprite@2x.png" data)]
             ["public/css/layout.css" (render "public/css/layout.css" data)]
             ["public/css/material-design-iconic-font.min.css" (render "public/css/material-design-iconic-font.min.css" data)]
             ["public/css/re-com.css" (render "public/css/re-com.css" data)]
             ["public/css/reset.css" (render "public/css/reset.css" data)]
             ["public/css/style.css" (render "public/css/style.css" data)]
             ["public/fonts/Material-Design-Iconic-Font.eot" (render "public/fonts/Material-Design-Iconic-Font.eot" data)]
             ["public/fonts/Material-Design-Iconic-Font.svg" (render "public/fonts/Material-Design-Iconic-Font.svg" data)]
             ["public/fonts/Material-Design-Iconic-Font.ttf" (render "public/fonts/Material-Design-Iconic-Font.ttf" data)]
             ["public/fonts/Material-Design-Iconic-Font.woff" (render "public/fonts/Material-Design-Iconic-Font.woff" data)]
             ["public/fonts/Material-Design-Iconic-Font.woff2" (render "public/fonts/Material-Design-Iconic-Font.woff2" data)]
             )))
