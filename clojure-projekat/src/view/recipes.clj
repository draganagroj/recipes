(ns view.recipes
 (:require [hiccup.page :as h]
            [hiccup.form :as form]
             [ring.util.anti-forgery :as anti-forgery]
             [noir.session :as session] 
             [model.model :as model]
             [view.layout :as layout]
             [compojure.core :refer [defroutes GET POST ]]
             [noir.response :refer [redirect]]
             
  )
  )

(defn recipe [recipes]
  "my recipes page"
    (layout/common "Recipes"
     (layout/navbar-my-recipes)
     [:row
     [:div.table-responsive {:style "width:75% ; margin:auto"}
      [:table.col-md-8 {:style " width:100%;margin-bottom:20px "}
       [:tbody
       
         (for [recipe recipes]
      [:tr {:style "height:100px ; border-top: 2px dashed #46b8da; border-bottom: 2px dashed #46b8da "}
       [:td  {:style "color: #555; font-weight:bold"} (:title recipe)]
       [:td  {:style "color: #555; font-style:italic"} (str (subs (:body recipe) 0 200) "...") [:a {:href (str "/show/" (:id recipe))} "  see whole recipe"]]
       ]
      ) 
        
       ]
   
    ]
        ]  ]        
     (layout/footer)
                  )
  )

(defroutes my-recipes
  "my recipe routes "
  (GET "/myrecipes" []
       (if (nil?(session/get :user))
           (redirect "/")
           (recipe (model/my-recipes (session/get :user)) ))
       )
  )