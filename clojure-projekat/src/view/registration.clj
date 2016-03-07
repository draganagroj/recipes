(ns view.registration
   (:require [hiccup.page :as h]
            [hiccup.form :as form]
             [ring.util.anti-forgery :as anti-forgery]
             [noir.session :as session]
              [noir.validation :as valid]
             [model.model :as model]
             [view.layout :as layout]
             [compojure.core :refer [defroutes GET POST routes]]
             [noir.response :refer [redirect]]
              [noir.validation :refer [rule errors? has-value? on-error]]
             )
  )
;; registration page
(defn registration-page [message]
(layout/common "Register"
(form/form-to  {:role "form" :class "form-horizontal"} [:post "/register"]
                 (anti-forgery/anti-forgery-field)
              [:div {:class "form-group"}
(form/label {:class "control-label"} "name" "username:")
(form/text-field  {:class "form-control"}"name")]
[:br]
 [:div {:class "form-group"}
(form/label {:class "control-label"} "pass" "password:")
(form/password-field {:class "form-control"} "pass")]
[:br]
 [:div {:class "form-group"}
(form/label {:class "control-label"}"pass1" "retype password:")
(form/password-field  {:class "form-control"}"pass1")]
[:br]
(form/submit-button {:class "btn btn-default"} "create account"))

(when
(and  (has-value? message) (not(= message "Error"))) 
[:div
  [:div {:id "first"} message ]
[:div.alert.alert-success "You successfuly registered"] ])
  
(when (and (has-value? message ) (= message "Error") )
  [:div
  [:div {:id "first"} message ]
 [:div.alert.alert-danger message]])


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
          
    