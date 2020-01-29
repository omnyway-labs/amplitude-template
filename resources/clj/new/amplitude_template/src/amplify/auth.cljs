(ns {{namespace}}.amplify.auth
  (:require
   [reagent.core :as r]
   ["@aws-amplify/auth" :default Auth]
   ["aws-amplify-react" :refer (withAuthenticator)]
   [{{namespace}}.util :refer [log >>]]))

(defn current-creds []
  (-> (.currentCredentials Auth)
      (.then
       (fn [response]
         {:accessKeyId                (.-accessKeyId  response)
          :secretAccessKey            (.-secretAccessKey response)
          :service-object-credentials (.essentialCredentials
                                       Auth
                                       response)}))))
(defn session []
  (.currentSession Auth))

(defn get-token []
  (-> (.currentSession Auth)
      (.then
       (fn [response]
         (-> response (.getAccessToken) (.getJwtToken))))))

(defn fetch-current-user! []
  (-> (.currentUserInfo Auth)
      (.then
       (fn [user]
         (>> [:set-current-user (js->clj user)])
         (.getUsername user)))))

(defn wrap-auth [component]
  (r/adapt-react-class
   (withAuthenticator (r/reactify-component component)
                      false)))

(defn sign-in [username password on-success on-failure]
  (.. Auth
      (signIn username password)
      (then on-success)
      (catch on-failure)))

(defn sign-out []
  (.. Auth
      (signOut)
      (then (fn [response] (log :auth-sign-out)))
      (catch (fn [response] (log :error)))))
