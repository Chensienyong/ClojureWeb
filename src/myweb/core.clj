(ns myweb.core
  (:require [net.cgrand.enlive-html :as enlive]
            [compojure.core :refer [defroutes GET POST]]
            [ring.adapter.jetty :as jetty]))

(defrecord Tweed [title content])

(defprotocol TweedStore
  (get-tweeds [store])
  (put-tweed! [store tweed]))

(defrecord AtomStore [data])

(extend-protocol TweedStore
   AtomStore
   (get-tweeds [store]
      (get @(:data store) :tweeds))
   (put-tweed! [store tweed]
      (swap! (:data store)
             update-in [:tweeds] conj tweed)))

(def store (->AtomStore (atom {:tweeds '()})))

(get-tweeds store)
(put-tweed! store (->Tweed "Test Title" "Test Content"))
(put-tweed! store (->Tweed "Test Title2" "Test Content"))

(enlive/defsnippet tweed-tpl "tweedler/index.html" [[:article.tweed enlive/first-of-type]]
  [tweed]
  [:.title] (enlive/html-content (:title tweed))
  [:.content] (enlive/html-content (:content tweed)))

(enlive/deftemplate index-tpl "tweedler/index.html"
  [tweeds]
  [:section.tweeds] (enlive/content (map tweed-tpl tweeds)))

(index-tpl (get-tweeds store))

(defroutes app
  (GET "/" [] (index-tpl (get-tweeds store)))
  (POST "/" req "TODO"))

(def server (jetty/run-jetty #'app {:port 3030 :join? false}))
