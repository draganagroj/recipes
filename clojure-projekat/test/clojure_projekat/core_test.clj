(ns clojure-projekat.core-test
  (:use midje.sweet) 
  (:require [clojure.test :refer :all]
            [model.model :as model]
           )
  )

(facts "getting username"
(fact (model/all-users) => ["Dragana" "Ena"])
(fact (model/all-users) => (two-of string? ))
(fact (model/all-users) => (contains "Dragana"))
(fact (model/all-users) => (has-prefix "Dragana"))
)
      


(facts "are all ids numbers"
(fact (model/all-users-id) => (has every? number?))
)