(defproject clojure-projekat "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [ring/ring-defaults "0.1.2"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [mysql/mysql-connector-java "5.1.38"]
                 [lib-noir "0.9.9"]
                  [org.clojure/math.numeric-tower "0.0.4"]           
                ]

:plugins [[lein2-eclipse "2.0.0"]
            [lein-ring "0.8.10"]
            [lein-midje "3.1.3"]
            ]

:ring {:handler controller.controller/app}

:profiles {:dev {:dependencies [[midje "1.6.3"]]}}
)