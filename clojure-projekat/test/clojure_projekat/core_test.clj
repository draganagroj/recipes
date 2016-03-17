(ns clojure-projekat.core-test
  (:use midje.sweet) 
  (:require [clojure.test :refer :all]
            [model.model :as model]
            [clojure-projekat.tests :as t]
             [ring.mock.request :as mock]
             [controller.controller :as cont]
           )
  )

(against-background [(before :contents (model/create-user "Dragana" "dragana")) (after :contents (t/clear-user "Dragana"))]
(facts "all users"                 
(fact (model/all-users) => ["Dragana"])
(fact (model/all-users) => (one-of string? ))
(fact (model/all-users) => (contains "Dragana"))
(fact (model/all-users) => (has-prefix "Dragana")))
)
  
(against-background [(before :contents (t/prepare-ids-numbers)) (after :contents (t/after-ids-numbers))]
(facts "are all ids numbers"
(fact (model/all-users-id) => (has every? number?)))
)


(deftest inserting-user
  (testing "inserting user with valid username and password"
   (try 
   (model/create-user "Ana" "ana")
     (finally (t/clear-user "Ana"))))
  (testing "inserting user with null username"
   (try 
   (is (thrown? Exception (model/create-user "" "ana")))
    )
  )
  (testing "inserting user with null password"
  (try 
  (is (thrown? Exception (model/create-user "Ana" "")))
  )
  )
 (testing "inserting user with null username and password"
   (try 
  (is (thrown? Exception(model/create-user "" "")))
   ) 
 )
 )

(against-background [(before :contents (t/prepare-get-user "Dragana" "dragana")) (after :contents (t/after-get-user "Dragana"))]
 (facts "getting the user from db" 
   (fact (model/user "Dragana" "dragana")=> [{:username "Dragana", :password "dragana" }])
   (fact (model/user "Ena" "ena")=> [])
   (fact (model/user "Dragana" "") => (throws Exception))
   (fact (model/user "" "dragana") => (throws Exception))
   (fact (model/user "" "") => (throws Exception))
   )                   
                    )

(deftest insert-comments
 (testing "inserting comment with all valid valid values"
  (t/prepare-insert-comment)     
  (try 
  (model/insert-commment "nice" "Dragana" (model/recipe-id "First"))
   (finally (t/clear-comments)) ))
   (testing "inserting comment for non existing recipe id"
   (t/prepare-insert-comment) 
   (try 
     (is (thrown? Exception (model/insert-commment "Nice" "Dragana" 1)))(finally (t/clear-comments))))
  (testing "inserting comment for non existing username "
  (try 
     (t/prepare-insert-comment)
   (is (thrown? Exception (model/insert-commment "Nice" "Marko" 1))) (finally (t/clear-comments))))
   (testing "inserting blank comment"
    (t/prepare-insert-comment)
    (try 
    (is (thrown? Exception (model/insert-commment "" "Marko" 1)))
    (finally (t/clear-comments)) ))
   (t/after-insert-comment)
   )

(against-background [(before :contents (t/prepare-get-comments)) (after :contents (t/after-get-comments))]
(facts "getting comments for recipe"
 (fact (model/get-comments (model/recipe-id "First"))=> [{:text "nice", :username "Dragana"}])
 (fact (model/get-comments "")=> [])
 (fact (model/get-comments 1)=> [] ))
)


(deftest insert-recipe
 (testing "inserting recipe with all valid valid values"
  (t/prepare-insert-recipe)     
  (try 
  (model/insert-recipe "First" "first recipe" "Dragana")
   (finally (t/after-insert-recipe)) ))
   (testing "inserting recipe without title"
   (t/prepare-insert-recipe) 
   (try 
   (is (thrown? Exception (model/insert-recipe "" "First recipe" "Dragana")))
   (finally (t/after-insert-recipe)) ))
  (testing "inserting recipe without body"
  (t/prepare-insert-recipe)    
  (try 
   (is (thrown? Exception (model/insert-recipe "First" "" "Dragana")))
   (finally (t/after-insert-recipe))
   ))
   (testing "inserting recipe without user"
  (t/prepare-insert-recipe)  
  (try 
   (is (thrown? Exception (model/insert-recipe "First" "first recipe" "")))
   (finally (t/after-insert-recipe))
   ))
  (testing "inserting recipe with non existing user"
  (t/prepare-insert-recipe)  
  (try 
   (is (thrown? Exception (model/insert-recipe "First" "first recipe" "Ena")))
   (finally (t/after-insert-recipe))
   ))
   )
  
(against-background [(before :contents (t/prepare-get-recipe)) (after :contents (t/after-get-recipe))]
(facts "getting recipe"
 (fact (model/get-recipe (model/recipe-id "First"))=> {:id  (model/recipe-id "First"), :title "First", :body "first recipe" :user (model/user-id "Dragana")})
 (fact (model/get-recipe  (model/recipe-id ""))=> nil)
 (fact (model/get-recipe (model/recipe-id "Second") )=> nil ))
)

(deftest update-rating 
  (testing "updating defaut rating with valid values"
  (t/prepare-rating)  
   (try 
  (model/update-rating 1 "Dragana" (model/recipe-id "First") )
   (finally (t/after-rating)) ))
    (testing "updating defaut rating with unknown user"
  (t/prepare-rating)  
   (try 
  (is (thrown? Exception (model/update-rating 1 "" (model/recipe-id "First"))))
   (finally (t/after-rating)) ))
     (testing "updating defaut rating with not existing username"
  (t/prepare-rating)  
   (try 
  (is (thrown? Exception (model/update-rating 1 "Ena" (model/recipe-id "First"))))
   (finally (t/after-rating)) ))
   (testing "updating defaut rating with not existing recipe title"
  (t/prepare-rating)  
   (try 
  (is (thrown? Exception (model/update-rating 1 "Ena" (model/recipe-id "Second"))))
   (finally (t/after-rating)) ))
     )

    (against-background [(before :contents (t/prepare-making-keys)) (after :contents (t/after-making-keys))]
    (facts "are all elements keywords"
     (fact (model/making-keys) => (has every? keyword?) )      
           ))

    (facts "squared Euclidian distance"
     (fact (model/euclidean-squared-distance [0 1] [1 2]) => 2)
     (fact (model/euclidean-squared-distance [1 1] [1 2]) => 1)
     )

   (facts "Euclidian distance"
   (fact (format "%.2f"(model/euclidean-distance [0 1] [1 2])) => "1.41")
   (fact (format "%.2f"(model/euclidean-distance [1 3] [3 4])) => "2.24")
   )

   
   (facts "find closest user to provided users"
     (fact (model/find-closest [1 1 1 1 1 0] t/sample)=> [1 1 1 1 1 1])   
     (fact (model/find-closest [0 0 0 0 0 0] t/sample)=> [1 0 0 0 0 0]) 
   
      )

   

  (deftest testing-routes
   (testing "not-found route"
    (let [response (cont/app (mock/request :get "/unknown"))]
      (is (= (:status response) 404)))
    )
   (testing "index route"
    (let [response (cont/app (mock/request :get "/"))]
      (is (= (:status response) 200))))
    (testing "my recipes route"
    (let [response (cont/app (mock/request :get "/myrecipes"))]
      (is (= (:status response) 302)))
   )
    (testing "login route"
    (let [response (cont/app (mock/request :get "/login"))]
      (is (= (:status response) 200)))
   )
    (testing "register route"
    (let [response (cont/app (mock/request :get "/register"))]
      (is (= (:status response) 200)))
   )
    (testing "add new recipe route"
    (let [response (cont/app (mock/request :get "/new"))]
      (is (= (:status response) 302)))
   )
    )

