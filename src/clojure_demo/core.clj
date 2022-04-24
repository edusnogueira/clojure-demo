(ns clojure-demo.core
  (:require [org.httpkit.server :as server]
            [compojure.core :as core]
            [compojure.route :as route]
            [ring.middleware.defaults :as rdef]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json])
  (:gen-class))

  ; Simple Body Page
(defn simple-body-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Hello World"})

; request-example
(defn request-example [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (->>
             (pp/pprint req)
             (str "Request Object: " req))})

(core/defroutes app-routes
  (core/GET "/" [] simple-body-page)
  (core/GET "/request" [] request-example)
  (route/not-found "Error, page not found!"))


(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; Run the server with Ring.defaults middleware
    (server/run-server (rdef/wrap-defaults #'app-routes rdef/site-defaults) {:port port})
    ; Run the server without ring defaults
    ;(server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
