(ns model.model
  (:require [clojure.java.jdbc :as sql]
          [clojure.math.numeric-tower :as math]
          )

  )


;; db properties
(def spec 
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname "//127.0.0.1:3306/clojure"
   :user "root"
   :password ""})

;; fetching names of all users from database
(defn all-users []
   (into [] (flatten(subvec(sql/query spec ["SELECT username FROM user"] :as-arrays? true )1))))

;;get id of all users
(defn all-users-id []
  (into [] (flatten(subvec(sql/query spec ["SELECT id FROM user"] :as-arrays? true )1)))
  )
;;inserting new user into db
(defn create-user [name pass]
 (sql/execute! spec ["INSERT INTO user (username,password)  VALUES(?,?) " name pass])
  )

;;getting comments for recipe
  (defn get-comments [id]
    (into  [] (sql/query spec ["SELECT comment.text,user.username  FROM user JOIN comment ON user.id=comment.user JOIN recipe ON recipe.id=comment.recipe WHERE recipe.id = ? " id])
  )
    )
 
  ;;getting the recipe 
  (defn get-recipe [id]
    (get(into [] (sql/query spec ["SELECT * FROM recipe WHERE recipe.id=?" id]  ))0))
  
  ;;getting the username of id 
  (defn get-username[id] 
      (get(into [] (sql/query spec ["SELECT username FROM user WHERE user.id=?" id]  ))0))
  
  ;;getting the userame of user who wrote recipe
  (defn username-recipe [id]
   (get-username(:user(get-recipe id)))
    )
  ;;recipe
  (defn recipe-details [id]
    (conj (get-recipe id) (username-recipe id))
    )
  
  (defn search-recipe [search]
    
    )

;;getting the user from db
(defn user [name pass]
 (into  [] (sql/query spec ["SELECT username, password FROM user WHERE username = ? AND password = ?" name pass])
  ))

;;get id of a recipe
(defn recipe-id [title]
  (:id(get(into  [] (sql/query spec ["SELECT id FROM recipe WHERE title = ? " title])
  )0)))

;;getting names of recepies
(defn titles []
  (into [] (flatten(subvec(sql/query spec ["SELECT title FROM recipe"] :as-arrays? true )1))))

;; getting id of a user
(defn user-id[name]
   (:id(get(into  [] (sql/query spec ["SELECT id FROM user WHERE username = ? " name ])
  )0)))

 ;;inserting comment
  (defn insert-commment [comment,user,recipe]
    (sql/execute! spec ["INSERT INTO comment (text, user,recipe) VALUES (?,?,?)" comment (user-id user) recipe ] )
    )
  
  ;;insert recipe  
(defn insert-recipe [title body user]
  (get(into [](sql/execute! spec ["INSERT INTO recipe (title,body,user) VALUES (?,?,?)" title body (user-id user)] )
  )0))
;;max recipe id
(defn max-id []
 (:max(get(into [](sql/query spec ["SELECT max(id) as max FROM recipe"]))0) )
  )
;;insert default rating of all users for new recipe
(defn insert-def-rat []
  (for [user (all-users-id)]
  (sql/execute! spec ["INSERT INTO rating (value,user,recipe) VALUES (?,?,?)" 0 user (max-id)])
  ))
;;update rating
(defn update-rating [value user recipe]
   (sql/execute! spec ["UPDATE rating SET rating.value=? WHERE rating.user=? AND rating.recipe=?" value (user-id user) recipe])
  )


;;inserting default rating for all recepies
(defn rating-def 
  [name]
  
  (for [t (titles)]
   (sql/execute! spec ["INSERT INTO rating (value, user,recipe) VALUES (?,?,?) " 0 (user-id name) (recipe-id t)] ) )  
  )
  (defn has-rating? [name]
  (into  [] (sql/query spec ["SELECT user.username FROM user JOIN rating ON user.id=rating.user WHERE user.username = ?" name])     
      ))

;; getting ratings of users 
(defn from-db []
  (for [name (all-users)]
  (into [](flatten(subvec (sql/query spec [ "SELECT  rating.value FROM user JOIN rating ON user.id=rating.user JOIN recipe ON recipe.id=rating.recipe WHERE user.username=? ORDER BY recipe.title" name
   ]   :as-arrays? true )1)))))

;;making keys of usernames
(defn making-keys []
  (map #(keyword %) (all-users)))

;;making a list of users and their ratings
(defn user-list []
  (zipmap (making-keys)  (from-db)))

;;square two numbers
(defn sqr
 
  [x]
  (math/expt x 2))

(defn euclidean-squared-distance
  "Computes the Euclidean squared distance between two sequences"
  [a b]
  (reduce + (map (comp sqr -) a b))
  )

(defn euclidean-distance
  "Computes the Euclidean distance between two sequences"
  [a b]
  (math/sqrt ( euclidean-squared-distance a b)))

;;remove user from map
(defn remove-current [user]
  (dissoc (user-list) (keyword user) )
  )

;;user ratings
(defn user-vector [user]
 ( (keyword user) (user-list))
  )

(defn find-closest
  "Find closest user in user map to supplied user"
  [user users]   
   (apply min-key (partial euclidean-distance user) (vals users)))
 ;;gets vestor of ratings of most similar users
(defn get-closest [user]
  (find-closest (user-vector user) (remove-current user))
  )

;;get username of most similar user
(defn get-key [user]
(get (get 
       (into [](filter
                 #(= 
                  (get-closest user)
                  (val %)
                  )
              (user-list)
                ))0)0))



;;return similar user
(defn return-similar [user]
   
  (into [] (sql/query spec [ "SELECT  user.username, recipe.title, rating.value FROM user JOIN rating ON user.id=rating.user JOIN recipe ON recipe.id=rating.recipe WHERE user.username=?" 
 (name(get-key user)) ]))
  )

;;return liked
(defn liked [user]
  (vec(filter #(> ( :value %) 0)  (return-similar user))
 ))
;;return titiles which the most similar user liked
(defn return-titles [user]
  (vec(map  :title (liked user)))
)

