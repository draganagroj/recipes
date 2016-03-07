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

;;inserting new user into db
(defn create-user [name pass]
  
 (sql/execute! spec ["INSERT INTO user (username,password)  VALUES(?,?) " name pass])
  
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

(def success "Success")
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