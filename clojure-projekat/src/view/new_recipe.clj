(ns view.new-recipe
  (:require [hiccup.page :as h]
            [hiccup.form :as form]
             [ring.util.anti-forgery :as anti-forgery]
             [noir.session :as session]
              [noir.validation :as valid]
             [model.model :as model]
             [view.layout :as layout]
               [compojure.core :refer [defroutes GET POST routes]]
             
   )
  )

(defn new-recipe []
  (layout/common "New recipe"
    (layout/navbar)              
                 
(form/form-to  {:role "form" :id "login-form" :class "form-horizontal"}[:post "/new"]
[:div {:class "form-group"}
(form/label {:class "reg-label control-label col-md-2"} "title" "Title:")
[:div.col-md-9
(form/text-field  {:class "form-control"} :title "")]]
[:br]
[:div {:class "form-group"}
 (form/label {:class "reg-label control-label col-md-2"} "text" "Text:")
 [:div.col-md-9
(form/text-area  { :id "txt-area" :class "form-control"} :text )]]
[:br]
(form/submit-button {:class "btn btn-info col-md-offset-6"} "save"))
  (layout/footer)
  ))

(defroutes recipe
  
 (GET "/new" [] (new-recipe))
  (POST "/new" [title text] (model/insert-recipe title text (session/get :user) ))
  )
