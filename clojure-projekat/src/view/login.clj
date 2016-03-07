(ns view.login
  (:require [hiccup.page :as h]
            [hiccup.form :as form]
             [ring.util.anti-forgery :as anti-forgery]
             [noir.session :as session]
              [noir.validation :as valid]
             [model.model :as model]
             [view.layout :as layout]
             [compojure.core :refer [defroutes GET POST routes]]
             [noir.response :refer [redirect]]
  ))

  
  (defn login-page
 [& message]  
  (layout/common "Logging"
(form/form-to  {:role "form" :class "form-horizontal"}[:post "/login"]
(anti-forgery/anti-forgery-field)
[:div {:class "form-group"}
(form/label {:class "control-label"} "name" "username:")
(form/text-field  {:class "form-control"} :name "")]
[:br]
[:div {:class "form-group"}
 (form/label {:class "control-label"} "pass" "password:")
(form/password-field  {:class "form-control"} :pass )]
[:br]
(form/submit-button {:class "btn btn-default"} "login"))
 (if (= message "Login error" )
   
 [:div.alert.alert-danger message]
 
 
   )
 
  
  )
   
    )
(defn login-check [name pass]
     (if (not (empty? (model/user name pass) ))
           (do (session/put! :user name)
             
               (redirect "/"))
           
           (login-page "Error login"))
           )         
   
  
(defroutes login
    (GET "/login" [] (login-page ))
  (POST "/login" [name pass]        
          (if (= true (clojure.string/blank? name))
             (login-page "Login error")
            (login-check name pass)
            )         
          ) ) 
 
