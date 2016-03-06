(ns controller.controller
  (:require [compojure.core :refer [defroutes GET POST routes]]
         
             [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
             [compojure.handler :as handler]
             [model.model :as model]
                [ring.util.response :as ring]
                 [hiccup.form :as form]
                [noir.response :refer [redirect]]
                [noir.session :as session]
                  [ring.util.anti-forgery :as anti-forgery]
                [ring.middleware.session.memory
                 :refer [memory-store]]
                [hiccup.page :as h]
                 [compojure.route :as route]
                  [noir.validation :as valid]
                  [noir.util.middleware :as util]
                  [view.index :as index]
                  )
  )



(defroutes index
  (GET "/" [] (index/index))
  
  )


(def app
(->
(handler/site (routes index))
(session/wrap-noir-session 
{:store (memory-store)})
 (valid/wrap-noir-validation))

)