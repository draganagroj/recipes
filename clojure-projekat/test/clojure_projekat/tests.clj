(ns clojure-projekat.tests
  (:require [clojure.java.jdbc :as sql]
            [model.model :as model]
            [clojure.test :refer :all])
  )
(defn clear-comments []
     (sql/execute! model/spec ["DELETE FROM comment"]) 
  )
(defn delete-all-ratings []
  (sql/execute! model/spec ["DELETE FROM rating"])
  )

(defn clear-user
  "clearing user after test"
  [name]
   (sql/execute! model/spec ["DELETE FROM user WHERE user.username=? " name]) )

(defn clear-users
  "clearing users after test"
  []
   (sql/execute! model/spec ["DELETE FROM user"]) )

(defn clear-recipe [name]
  "clearing recipe"
 (sql/execute! model/spec ["DELETE FROM recipe WHERE recipe.title=?" name])
  ) 

(defn get-recipe-id [name]
  "getting recipe id"
  (sql/execute! model/spec [" SELECT id FROM recipe WHERE recipe.title=?" name])
  )

(defn prepare-get-user [name pass]
  "preparing get user"
  (model/create-user name pass)
  )
(defn after-get-user [name]
  "teardown get user"
  (clear-user name)
  )

(defn prepare-insert-recipe []
  (model/create-user "Dragana" "dragana")
  )

(defn after-insert-recipe []
  (do  (clear-recipe "First")
    (clear-user "Dragana")
     
    )
  )
(defn prepare-insert-comment []
  "insert comment setup"
  (do
   (model/create-user "Dragana" "dragana")
   (model/insert-recipe "First" "first recipe" "Dragana"))
  )

(defn after-insert-comment []
  "insert comment teardown"
   (do
    (clear-recipe "First")
    (clear-user "Dragana")   
    )
  )
(defn prepare-get-comments []
  "get comment setup"
 (do
   (model/create-user "Dragana" "dragana")
   (model/insert-recipe "First" "first recipe" "Dragana")
   (model/insert-commment "nice" "Dragana" (model/recipe-id "First")))
   ) 
  
(defn after-get-comments []
  "get comments teardown"
  (do
    (clear-recipe "First")
    (clear-user "Dragana")   
    )
  )

(defn prepare-get-recipe []
  "get recipe setup"
   (do
   (model/create-user "Dragana" "dragana")
   (model/insert-recipe "First" "first recipe" "Dragana"))
  )

(defn after-get-recipe []
  (do
    (clear-recipe "First")
    (clear-user "Dragana")   
    )
  )

(defn prepare-making-keys []
  "setup for making keys"
   (do
   (model/create-user "Dragana" "dragana")
    (model/create-user "Ena" "ena")
    (model/create-user "Marko" "marko"))
  )



(def sample 
  {:Dragana [1 1 1 1 1 1]
   :Marta [1 0 0 0 0 0]
   :Marko [1 1 0 0 0 0]
   }
  )

(defn prepare-ids-numbers []
  "setup ids"
 ( do(model/create-user "Dragana" "dragana")
   (model/create-user "Ena" "ena"))
  )

(defn after-ids-numbers []
  "teardown ids"
  (do (clear-user "Dragana")
       (clear-user "Ena")    
    )
  )

(defn delete-all-recipes []
  (sql/execute! model/spec ["DELETE FROM recipe"])
  )
(defn delete-all-users[]
  (sql/execute! model/spec ["DELETE FROM user"])
  )

 (defn preparing-all-users []
   (do 
      (delete-all-ratings)
     (delete-all-recipes)
     (delete-all-users)
     (model/create-user "Dragana" "dragana")
     )
   )

  
(defn after-making-keys []
  "teardown making keys"
  (do(clear-users)
 
  ))
