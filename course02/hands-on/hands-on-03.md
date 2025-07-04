# External Identity Providers with Spring Boot
In this assignment, you will set up multiple social Identity Providers - GitLab and Google, in your Spring Boot application. You have already learnt how to do this using GitLab (with BugTracker), but you will also use Google to Sign in to the website. This will require some research on your part.  

## Prerequisites
* Complete the Assignment 2 
* The Zip file jaubs-roles.zip from Assignment 2 is also a solution to this.

## Instructions
In this assignment, you will allow users to log in using either their **GitLab** or **Google accounts**. As a part of this, you will have to do the following.  
* Create a Client in GitLab UI for JAUBS Website (We have done this for BugTracker)
* Create a Client within Google UI for JAUBS Website


### To create a client in GitLab 
* Go to https://about.gitlab.com/pricing/ to create a free account
* Log in using your account
* Update a profile picture so that you can see that in JAUBS Website
* Create application called **jaubs** with the correct scopes and callback-urls
* Note down the GitLab **client id** and **client secret**

### To create a client in Google
* From your Google account, go to https://console.cloud.google.com/
* Create a new project called **jaubs** and create an OAuth credentials (Require some research)
* Note down the Google **client id** and **client secret**

Make the necessary changes to the Spring Boot **application.properties** file of **jaubs-roles.zip**.  
* Update with GitLab specific OpenID Connect properties
* Update with Google specific OpenID Connect properties

## Testing
* Test the JAUBS website and make sure you can Sign in using either GitLab or Google accounts. 
* Make sure that the role associated with this user is the jaubs-user role.
* Does your uploaded picture in GitLab and Google show up in the application ?

## Asignment Questions
* Were you able to complete the assignment successfully ? Yes

```
## GitLab specific OAuth 2 related properties
spring.security.oauth2.client.registration.gitlab-oidc.provider=gitlab
spring.security.oauth2.client.registration.gitlab-oidc.client-name=GitLab
spring.security.oauth2.client.registration.gitlab-oidc.client-id=my-client-id
spring.security.oauth2.client.registration.gitlab-oidc.client-secret=my-secret
spring.security.oauth2.client.registration.gitlab-oidc.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.gitlab-oidc.scope=openid,profile,email

### You need to set the issuer (Not Strictly Required because it's already known to Spring Boot)
## Openid configuration - https://gitlab.com/.well-known/openid-configuration
spring.security.oauth2.client.provider.gitlab.issuer-uri=https://gitlab.com


## Google specific OAuth 2 related properties
spring.security.oauth2.client.registration.google-oidc.provider=google
spring.security.oauth2.client.registration.google-oidc.client-name=Google
spring.security.oauth2.client.registration.google-oidc.client-id=my-client-id
spring.security.oauth2.client.registration.google-oidc.client-secret=my-secret
spring.security.oauth2.client.registration.google-oidc.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.google-oidc.scope=openid,profile,email

### You need to set the issuer (Not Strictly Required because it's already known to Spring Boot)
## Openid configuration - https://gitlab.com/.well-known/openid-configuration
spring.security.oauth2.client.provider.google.issuer-uri=https://accounts.google.com
```


```
Were you able to complete the assignment successfully ?
Creating a jaubs client in GitLab
    Log in using your GitLab account
    Click on your icon and click Preferences -> Applications
    Click on Add New Application
        name = jaubs
        Callback URL = http://localhost:8080/login/oauth2/code/gitlab-oidc
        Scopes = openid, profile, email
        Confidential = yes
    Note down the GitLab client id and secret


Creating a jaubs client in Google
    Log in to Google Cloud account
    Create a new project called jaubs
    From upper left corner, select API Services -> Credentials
    Click on button Create Credentials -> OAuth Client ID
    Click on OAuth Consent Screen
        User Type = External, click Create
        Go through the steps to create the Consent screen
        Enter only necessary required info like emails ids
    Click on button Create Credentials -> OAuth Client I
        Application Type = Web Application
        Name = jaubs
        Authorized redirect URIs = http://localhost:8080/login/oauth2/code/google-oidc
    Note down the Google client id and client secret

Spring Boot Updates
    In the application.properties file, uncomment the GitLab and Google blocks
    Enter the Client Id/Client Secrets of GitLab and Google at appropriate places
    Restart the application 

The Java code for this assignment is the same as Assignment 2. The only change is the modification of the application.properties file as explained in the Instructions above.
```

