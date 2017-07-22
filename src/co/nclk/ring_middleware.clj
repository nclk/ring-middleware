(ns co.nclk.ring-middleware
  (:require [cheshire.core :as json]
            [clojure.pprint :refer [pprint]]))

(defn wrap-json-response
  [handler]
  #(let [resp (handler %)]
    (-> resp
      (assoc-in [:headers "Content-Type"]
        "application/json")
      (assoc-in [:headers "Access-Control-Allow-Origin"]
        "*")
      (assoc :body (when (:body resp)
                     (json/generate-string (:body resp)))))))

(defn wrap-log
  [handler]
  (fn [req]
    (pprint req)
    (let [resp (handler req)]
      (pprint resp)
      resp)))

