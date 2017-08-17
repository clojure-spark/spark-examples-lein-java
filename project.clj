(defproject spark-examples-lein-java "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 ;; from spark-examples project pom.xml
                 [org.apache.spark/spark-core_2.10 "1.6.0"]
                 [org.apache.spark/spark-mllib_2.10 "1.6.0"]
                 [org.apache.nifi/nifi-spark-receiver "1.3.0"]
                 [edu.stanford.nlp/stanford-corenlp "3.4.1"]]
  :java-source-paths ["java_src"]
  :aot :all
  :main com.jeremydyer.Main)
