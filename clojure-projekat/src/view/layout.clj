(ns view.layout
   (:require [hiccup.page :as h]
             [hiccup.form :as form]   
             [model.model :as model]            
             ))

;;page template
(defn common [title & body]
  (h/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport" :content
            "width=device-width, initial-scale=1, maximum-scale=1"}]
    [:title title]
    ;;(h/include-css "/stylesheets/bootstrap.css")
    ]
   
   [:body
    [:div {:id "header"}
     [:h1 {:class "container"} "Kuvar"]]
    [:div {:id "content" :class "container"} body]]))
