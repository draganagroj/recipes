(ns view.new-recipe
  (:require [hiccup.page :as h]
            [hiccup.form :as form]
             [noir.session :as session]
             [model.model :as model]
             [view.layout :as layout]
             [compojure.core :refer [defroutes GET POST ]]
             [noir.validation :refer [has-value?]]
             
   )
  )

(defn new-recipe [message]
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
(when
 (= message 1) 
[:div
[:div.alert.alert-success.col-md-offset-2.col-md-9.message "Your recipe has been saved"] ])
(when
 (and (not(= message 1)) (not(= message "")) )
[:div
[:div.alert.alert-danger.col-md-offset-2.col-md-9.messsage "Error occured during saving"] ]
 )
  (layout/footer)
  ))

(defroutes recipe
  
 (GET "/new" [] (new-recipe ""))
  (POST "/new" [title text] (new-recipe(model/insert-recipe title text (session/get :user)) ))
  )
