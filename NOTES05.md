# Oauth 2.0 - Using Okta

We would have to use an enterprise grade Authorization server. There are lots of open source Authorization servers which an enterprise can use like **cloud foundry UUA**, **key cloak** etc, and it's not the intation of this course to recommend one over the other. 
  
I wanted to chosse an Authorization server which is enterprise-grade and useful for learning but at the same time also deployed in the cloud so that I don't have to deploy that locally. Okta fits that bill perfectly.  
  
A developer can sign up with Okta and get access to a free Okta Authorization server at no cost. A big advantage of this is that you can write your own resource server and your own clients. You can also create your own custom scopes, something that is not possible using Google Authorization Server.  

## Important - Okta User Interface Changes
IMPORTANT - Follow instructions below carefully. We will **NOT** be using Auth0 Authorization Server.  
  

Okta's signup screen has changed for https://developer.okta.com/signup/ after Okta's acquisition of Auth0. It now shows 3 options to choose from.  
* Important : Do **NOT** click on **Try Customer Identity Cloud** or **Try Workforce Identity Cloud**
* Please choose the option **"Access the Okta Developer Edition Service"** by clicking on button **"Sign up free"**
* Enter your work information and click **"Sign up"**
* Alternatively, you can use your personal **Google** or **Github** account to sign up
* Login using your user and from this point everything is the same

Okta updates the User Interface of the Developer Console regularly. If there are major changes, I re-record the lectures to reflect the changed User Interface and in doing so in some cases you will see that the **Client ID** and **Client Secret** from the Okta UI videos may not exactly match the **Postman** lectures. If the UI changes are not major, then I will not record the video. I expect you to take this into account.  

## Okta Setup and Endpoints
https://dev-<my-account>.okta.com/oauth2/default/.well-known/openid-configuration