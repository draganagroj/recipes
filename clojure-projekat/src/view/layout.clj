(ns view.layout
   (:require [hiccup.page :as h]
             [hiccup.form :as form]   
             [model.model :as model]   
             [noir.session :as session]
             ))

;;page template
(defn common [title & body]
  (h/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport" :content
            "width=device-width, initial-scale=1, maximum-scale=1"}]
    [:title title]
    (h/include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")
      (h/include-css "/stylesheets/style.css"
                    
                     ) 
    ]
   
   [:body
    {:class "content" }
   body]))

;;navbar
(defn navbar []
  [:nav.navbar.navbar-default
  [:div.container-fluid
   [:div.navbar-header
    [:a.navbar-brand {:href "#"} "Healthy lifestyle"]
    ]
   [:ul.nav.navbar-nav
    [:li [:a {:href "/"}"Home" ]]
     [:li [:a {:href "/new"}"Add recipe" ]]
    ]
   (when (=(session/get :user) "")
    [:ul.nav.navbar-nav.navbar-right
     [:li [:a {:href "/register"}
           [:span.glyphicon.glyphicon-user]
           "Sign up"]]
      [:li [:a {:href "/login"}
           [:span.glyphicon.glyphicon-log-in]
           "Login"]]
     ]
    )
   (when (not=(session/get :user) "")
     [:ul.nav.navbar-nav.navbar-right
     [:li [:a {:href "/register"}
           [:span.glyphicon.glyphicon-user]
           (session/get :user)]]
     
    ])
   ]]
   
  )

;;footer
(defn footer []
  
  [:footer.fixed-footer
   [:div {:id "off-padding"}
    [:h4#footer-txt "Healhty lifestyle 2016"]
    ]
   ]
  )


