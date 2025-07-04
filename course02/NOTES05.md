# BugTracker With PKCE

Lab:  
1. access keycloak clients configuration (app registration)
   1. access the configuration for bugtracker client
   2. Under **Capability config** set ```Client authentication``` to OFF, this will means that it is a public client
   3. Under **Login Settings** set ```Conset required``` to ON
   4. Under **Login Settings** set ```Display Client on screen``` to ON
   5. Under **Login Settings** set ```Client consent screet text``` type: "Plase approve the scope bugtracker"
   6. Click on **SAVE**
   7. To enable PKCE do the following:
      1. go to the **Advanced** tab
      2. Scroll down and look for ```Proof Key for Code Exhange Code Challenge Method```, choose S256
      3. Click on **SAVE**

### Setting this now on Spring-boot application
application.yaml. 
```
spring:
  application:
    name: bugtrackercli
  security:
    oauth2:
      client:
        registration:
          keycloak-oidc:
            provider: keycloak
            client-name: bugtracker
            client-id: bugtracker
            # client-secret: ${CLIENT_SECRET}
            client-authentication-method: none
            authorization-grant-type: authorization_code
            scope: openid,profile,email,bugtracker
        provider:
          keycloak:
            issuer-uri: http://127.0.0.1:9090/realms/oauthrealm
```

## Creating enterprise ROLES and assign it to the USERS
1. Access keycloak admin UI
   1. go to clients, select the bugtracker client
   2. Click on ROLES and add two roles:
      1. bugtracker.admin
      2. bugtracker.user
   3. Navigate to the users and assign the roles to the users
      1. janedoe -> bugtracker.user
      2. johndoe -> bugtracker.admin
2. Now we need to create a mapping between the scopes and roles
   1. Left side menu click on ```Client scopes```
   2. Click on CREATE CLIENT SCOPE
      1. name = bugtracker.user
      2. Type = Optional
      3. leave the defaults and click on SAVE
      4. name = bugtracker.admin
      5. Type = Optional
      6. leave the defaults and click on SAVE
3. We now need to create the mapping and association
   1. Left side menu click on ```Client scopes```
      1. Select the ```bugtracker.user```scope
      2. on the tab on the top navigate to ```Scope```
         1. click on ASSIGN ROLE
         2. search for the ROLE and ASIGN the ROLE ```bugtracker.user```
      3. Do the same for ```bugtracker.admin```
4. Now, we need to associate the two new scopes ```bugtracker.user``` and ```bugtracker.admin``` to the bugtracker client
   1. Go to the client bugtracker, select the tab "Client Scopes", search for the two scopes and ADD them as OPTIONAL
5. As we are using an enterprise app, disable the ```Consent Require``` in the bugtracker client
6. Clear the consents from the users jane and john if necessary
7. So now in order to not need to speficy the scopes ```bugtracker.user``` and ```bugtracker.admin``` in the application.properties in spring-boot change both scopes for the client bugtrakcer from "OPTIONAL" to "DEFAULT" type.
8. To finalize, disable ```bugtracker-dedicated``` client scopes
   1. Go to the bugtracker client
   2. Tab -> Client Scopes -> click on ```bugtracker-dedicated``` -> Click on TAB Scope And furn OFF "FULL SCOPE ALLOWED"


### References
Spring Security OAuth 2 Documentation. 

The Spring Security documentation for setting up a OAuth Client is shown below. 
* Spring Security OAuth 2 - Summary of Client Features - https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html#oauth2-client
* Spring Security OAuth 2 - Support for Login - https://docs.spring.io/spring-security/reference/servlet/oauth2/login/core.html
* Spring Security OAuth 2 - Support for Client - https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html