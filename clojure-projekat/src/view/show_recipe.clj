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

;;display recipe
(defn recipe-display [id]
  
  [:div.row
  [:div.col-md-offset-3.col-md-8   
[:div.col-md-9
  {:id "txt-area-2" } 
  [:h2.col-md-offset-4.title (:title(model/recipe-details id))]
[:h4.user.col-md-offset-9 "By "(:username(model/recipe-details id)) ]
[:br]
  [:div 
   [:pre {:id "recipe"}
   (:body(model/recipe-details id))
   ]]
  ]]
  ]
  )

;;display like and dislike buttons
(defn like-dis [id]
   [:div.row
   [:div{:style "margin-top:20px; margin-left:44% "}
   (form/form-to  {:role "form" :class "form-btn"} [:post (str "/show/" id) ] 
                  (form/text-field {:style "display:none"} "like" "like")
                  (form/submit-button {:class "btn btn-default" :id "like"} "" )
                  )
   (form/form-to  {:role "form" :class "form-btn" } [:post (str "/show/" id) ]
                   (form/text-field {:style "display:none"} "dislike" "dislike")
                  (form/submit-button {:class "btn btn-default" :id "dislike"} "" )
                  )
   ]]
  )

;;display comment form
(defn comment-form [id]
   [:div.row 
   [:div.col-md-offset-2.col-md-8 {:style "margin-top:20px"}
     (form/form-to  {:role "form" :class "form-btn"}[:post (str "/show/" id) ]
                  [:div {:class "form-group"}
                     (form/label {:class "reg-label control-label col-md-2"} "c" "leave comment")
                     [:div.col-md-9
                    (form/text-field {:class "form-control" :style "margin-left:-20px" } "comment" "" )]
                    [:br]
                      [:br]
                    ]
                  (form/submit-button {:class "btn btn-default col-md-offset-5" } "publish")
                  )
    ]
   ]
  
  )
;;display comments for recipe
(defn comments [id]
  (for [com (model/get-comments id)]
    [:div.row
      [:div.col-md-offset-3.col-md-6 {:class "comments"}
        [:div {:class "form-group"}
                     (form/label {:class "reg-label control-label col-md-3 col-md-offset-9" :style "font-style:italic"} "username" (str "user "(:username com)))
                      [:br]
                     [:div.col-md-12
                    (form/text-field {:class "form-control " :style "margin-left:-5px; margin-bottom:10px" :disabled "true"} "com" (:text com) )]
                    [:br]]
       ]
     ]
    )
  )

(defn recommended-titles [id]
  (into [] (filter  #(not(= (:title(model/recipe-details id)) %) ) (model/return-titles (session/get :user)) )
  ) 
  )

(defn get-id [title]
  (str "/show/"(model/recipe-id title))
  )

(defn recomendation [id]
   [:div.col-md-offset-3.col-md-6 {:style " border: 2px dashed #777; border-radius:10px; margin-top:10px"}
    [:p.col-md-offset-4 {:style "font-style:italic ; font-size:medium"} "Recommended recipes"]
 (for [title (recommended-titles id)]
   [:div.row 
    
   [:a {:href (get-id title) :style "margin-left:5%"} title]]
   )
 ]
  )

(defn show-selected
  [id]
   (layout/common "Recipe"
                  (layout/navbar)
                   (recipe-display id)
                   ( when (not(nil?(session/get :user)))
                (like-dis id))
                   ( when (not(nil?(session/get :user)))
                 
                 (recomendation id)) 
                   
                ( when (not(nil?(session/get :user)))
                 
                 (comment-form id))
                
                 (comments id)
                   (layout/footer)
           )
  
  )


(defroutes show
  (GET "/show/:id" [id]
       (show-selected id )
       )
 (POST "/show/:id" [ comment id like dislike] (cond
                                                (not(nil? comment ))
                                                (do
                                                (model/insert-commment comment (session/get :user) id)
                                                (show-selected id))
                                     (not(nil? like ))
                                     (do
                                        (model/update-rating 1 (session/get :user) id)
                                        (recommended-titles id)
                                        (show-selected id))
                                     
                                     (not(nil? dislike ))
                                     (do
                                        (model/update-rating -1 (session/get :user) id)
                                         (recommended-titles id)
                                        (show-selected id))
                                        )
  ))