(ns view.registration
   (:require [hiccup.page :as h]
            [hiccup.form :as form]
             [noir.session :as session]
             [model.model :as model]
             [view.layout :as layout]
             [compojure.core :refer [defroutes GET POST routes]]
             [noir.response :refer [redirect]]
              [noir.validation :refer [has-value?]]
             )
  )
;; registration page
(defn registration-page [message]
(layout/common "Register"
               
[:div.col-md-offset-3.col-md-6 
 [:div {:class "border-div"}
 [:h2.col-md-offset-4 {:id "reg-title"} "Registration form"]
(form/form-to  {:role "form" :id "reg-form" :class "form-horizontal"} [:post "/register"]             
[:div {:class "form-group"}
(form/label {:class "reg-label control-label col-md-2"} "name" "username:")
[:div.col-md-9
(form/text-field  {:class "form-control"}"name")]]
[:br]
 [:div {:class "form-group"}
(form/label {:class "reg-label control-label col-md-2"} "pass" "password:")
[:div.col-md-9
(form/password-field {:class "form-control"} "pass")]]
[:br]
 [:div {:class "form-group"}
(form/label {:class "reg-label control-label col-md-2"}"pass1" "retype password:")
[:div.col-md-9
(form/password-field  {:class "form-control"}"pass1")]]
[:br]
(form/submit-button {:class "btn btn-info col-md-offset-5"} "create account"))
]
(when
(and  (has-value? message) (not(= message "Error"))) 
[:div
  [:div {:id "first"} message ]
[:div.alert.alert-success "You successfuly registered"] ])
  
(when (and (has-value? message ) (= message "Error") )
  [:div
  [:div {:id "first"} message ]
 [:div.alert.alert-danger message]])

]
  )
)


(defn registration-handler [name pass pass1]
   (if (= pass pass1)  
              (do 
             
              (model/create-user name pass)
              
            (registration-page  (model/rating-def name)  
              )
              )  
            (registration-page "Error")
  )
   )

(defroutes registration
  (GET "/register" [] (registration-page ""))
 (POST "/register" [name pass pass1]
       (registration-handler name pass pass1)
         ))
          
    