(ns view.index
  (:require [hiccup.page :as h]
            [hiccup.form :as form]        
             [noir.session :as session]
              [noir.validation :as valid]
             [model.model :as model]
             [view.layout :as layout]
             
   ) )


(defn index
   []
  ( layout/common "Index"
                  (layout/navbar)
                   
                   (layout/footer)
           )
  )


