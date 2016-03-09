(ns view.show-recipe
  (:require [hiccup.page :as h]
            [hiccup.form :as form]
             [noir.session :as session]
             [model.model :as model]
             [view.layout :as layout]
             [compojure.core :refer [defroutes GET POST routes]]
             [noir.validation :refer [has-value?]]
             
   )
  )


(defn recipe-display [id]
  [:div.col-md-offset-3.col-md-8   
[:div.col-md-9
  {:id "txt-area-2" } 
  [:h2.col-md-offset-4.title (:title(model/recipe-details id))]
[:h4.user.col-md-offset-9 "By "(:username(model/recipe-details id)) ]
[:br]
  [:div 
   (:body(model/recipe-details id))
   ]
  ]]
  
  )

(defn show-selected
  [id]
   (layout/common "Recipe"
                  (layout/navbar)
                   (recipe-display id)
                  
                   (layout/footer)
           )
  
  )


(defroutes show
  (GET "/show/:id" [id]
       (show-selected id )
       )
  )