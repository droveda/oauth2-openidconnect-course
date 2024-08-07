# Oauth 2.0 - Using Okta

We would have to use an enterprise grade Authorization server. There are lots of open source Authorization servers which an enterprise can use like **cloud foundry UUA**, **key cloak** etc, and it's not the intention of this course to recommend one over the other. 
  
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

Okta updates the User Interface of the Developer Console regularly. If there are major changes, I re-record the lectures to reflect the changed User Interface and im doing so in some cases you will see that the **Client ID** and **Client Secret** from the Okta UI videos may not exactly match the **Postman** lectures. If the UI changes are not major, then I will not record the video. I expect you to take this into account.  

## Okta Setup and Endpoints
https://dev-```<my-account>```.okta.com/oauth2/default/.well-known/openid-configuration

## Deep Dive - Client Credentials Grant Type
* See how to and documentation [here](FakeBookAPI-Resource-Server-setup.md)
* POST - https://dev-29895772.okta.com/oauth2/default/v1/token
  * grant_type = client_credentials
  * client_id = my-client-id
  * client_secret = my-client-secret
  * scope = fakebookapi.read fakebookapi.admin
```
curl --location 'https://dev-29895772.okta.com/oauth2/default/v1/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=9E4181349A7FDDBBDF979534BEDEEA59' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'client_id=<my-client-id>' \
--data-urlencode 'client_secret=<my-client-secret>' \
--data-urlencode 'scope=fakebookapi.read fakebookapi.admin'
```

```
# using the bearer access_token returned by the previous curl command
curl --location 'http://localhost:8080/books' \
--header 'Authorization: Bearer <my-access-token>'
```

```
curl --location 'http://localhost:8080/books' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <my-access-token>' \
--data '{
    "id": 6,
    "title": "My book",
    "author": "Droveda",
    "cost": 7.99,
    "numPages": 260
}'
```

```
# Instropect the token call
curl --location 'https://dev-29895772.okta.com/oauth2/default/v1/introspect' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=A9F4E80113AA004F756865491D003F72' \
--data-urlencode 'client_id=<my-client-id>' \
--data-urlencode 'client_secret=<my-client-secret>' \
--data-urlencode 'token=<my-access-token>'
```