(ns curbside-challenge.core
  (:require [clj-http.client :as client]
            [cheshire.core :refer [parse-string]])
  (:gen-class))

(def base-url "http://challenge.shopcurbside.com/")
(def req-per-session 10)
(def req-count (atom 0))
(def current-session-id (atom ""))

(defn get-new-session-id []
  (:body (client/get (str base-url "get-session"))))

(defn get-session-id []
  (when (= 0 (mod @req-count req-per-session))
    (reset! current-session-id (get-new-session-id)))
  @current-session-id)

(defn call-curbside [endpoint]
  (let [response (client/get (str base-url endpoint) {:headers {"Session" (get-session-id)}})]
    (swap! req-count inc)
    (parse-string (:body response) (fn [k] (keyword (.toLowerCase k))))))

(defn challenge [endpoint]
  (let [response (call-curbside endpoint)]
    (if (contains? response :secret)
      (:secret response)
      (if (vector? (:next response))
        (map challenge (:next response))
        (challenge (:next response))))))

(defn do-challenge []
  (apply str (flatten (challenge "start"))))

(defn -main
  "Prints the result of the Curbside challenge."
  [& args]
  (println (do-challenge)))
