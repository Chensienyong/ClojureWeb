(defproject myweb "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [markdown-clj "0.9.62"]
                 [enlive "1.1.5"]
                 [ring "1.3.2"]
                 [compojure "1.3.1"]]
  :repl-options {:init-ns myweb.core})
