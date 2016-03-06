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
