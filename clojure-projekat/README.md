# On-line recipes

This project is made for subject on master studies "Tools and methods of Software engineering". It is done in function language - Clojure. 
It represents on-line application for recipes, where users can register,login and post their recipes. If user is not logged, he can only see most popular ones and search them. Users who are logged-in can add new recipe, see their recipes, leave comments on recipes and also get recommendations for other recipes based on likings of most similar user. 

##Requirements 

This application needs Leiningen 2.0.0
 
##Libraries

There were several libraries used in this application:

 libnoir - for user sessions, redirecting users if they are not logged and for validation, testing routes
 ring - for handling routing
 compojure - for handling routing
 java.jdbc - for connection to relational database
 hiccup - for making html pages
 math.numeric-tower - for mathematical formulas
 clojure.test - for testing business logic
 midje - for testing business logic
 
 
##Starting application

To start application on localhost from command line type the command :

      lein ring server 

##References

Literature being used for this project:

"Clojure for the brave and true"
"Web development with Clojure"

 as well as clojuredocs.org and wiki pages of mentioned libraries

## License

Copyright Dragana Groj 2016 

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
