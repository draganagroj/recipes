(ns view.index
  (:require [hiccup.page :as h]
            [hiccup.form :as form]        
             [noir.session :as session]
              [noir.validation :as valid]
             [model.model :as model]
             [view.layout :as layout]
             [compojure.core :refer [defroutes GET POST routes]]
             
   ) )



(defn most-liked []
  "show most liked recipes"
  [:row
  [:div.table-responsive  {:style "width:75% ; margin:auto"}
   [:p1.col-md-offset-4 {:style "font-weight:bold; color:#555; font-family:Lucida Calligraphy; letter-spacing: 2px ; font-size:x-large"} "Most popular"]
   
      [:table.col-md-8 {:style " width:100%; margin-top:20px ;margin-bottom:20px"}
       [:tbody
       
         (for [recipe (model/best-recipe)]
      [:tr {:style "height:100px ; border-top: 2px dashed #46b8da; border-bottom: 2px dashed #46b8da ;"}
       [:td  {:style "color: #555; font-weight:bold; padding-right:20px"} (:title recipe)]
       [:td  {:style "color: #555; font-style:italic"} (str (subs (:body recipe) 0 200) "...") [:a {:href (str "/show/" (:id recipe))} "  see whole recipe"]]
       ]
      ) 
        
       ]
   
    ]
        ]   ]     
  )

(defn searched [search]
  "search recipe"
  (if (empty?(model/search-recipe search))
            (list [:p1.col-md-offset-2 {:style "font-weight:bold; color:#555; font-family:Lucida Calligraphy; letter-spacing: 2px ; font-size:x-large"} "Sorry, there are no recipes with that name"]
           )
      (list      
            [:div.containter  {:style "width:75% ; margin:auto"}
   [:p1.col-md-offset-4 {:style "font-weight:bold; color:#555; font-family:Lucida Calligraphy; letter-spacing: 2px ; font-size:x-large"} "Search results"]
   
      [:table.col-md-8 {:style " width:100%; margin-top:20px "}
       [:tbody
       
         (for [recipe (model/search-recipe search)]
      [:tr {:style "height:100px ; border-top: 2px dashed #46b8da; border-bottom: 2px dashed #46b8da "}
       [:td  {:style "color: #555; font-weight:bold"} (:title recipe)]
       [:td  {:style "color: #555; font-style:italic"} (str (subs (:body recipe) 0 200) "...") [:a {:href (str "/show/" (:id recipe))} "  see whole recipe"]]
       ]
      )      
       ]
   
    ]
        ]        
          )  )  
  )
  

(defn index
  "home page"
   [search]
  ( layout/common "Index"
                  (layout/navbar)
                 (if (empty? search)
                    (most-liked)
                    (searched search)
                   )
                   (layout/footer)
           )
  )

(defroutes index-route
  "home routes"
  (GET "/" [] (index ""))
  (POST "/" [search] (index search) )
  )
