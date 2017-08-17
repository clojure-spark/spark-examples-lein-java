(ns spark-examples-lein-java.core
  (:import
   (org.apache.nifi.remote Transaction)
   (org.apache.nifi.remote TransferDirection)
   (org.apache.nifi.remote.client SiteToSiteClient)
   (org.apache.nifi.remote.client SiteToSiteClientConfig)
   (java.io IOException)
   (java.util HashMap)))

;; java_src/main/java/com/jeremydyer/Test.java
#_(doto (SiteToSiteClient.)
  (.Builder)
  (.url "http://127.0.0.1:8080/nifi")
  (.portName "Data From Spark")
  (.buildConfig))
