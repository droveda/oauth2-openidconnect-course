# OAuth 2.0 for the Enterprise and Cloud

| OAuth 2.0           | Enterprise         |
| ------------------- | ------------------ |
| Resource Owner      | User               |
| User Agent          | Browser            |
| Client              | Application        |
| Resource Server     | Rest API, Service  |

## OAuth 2.0 - Users, Groups, Scopes

| Social Platform                   | Enterprise Platform                 |
| --------------------------------- | ----------------------------------- |
| User population endless           | User population is limited          |
| User self registration            | No self registration                |
| No Groups but scopes              | Users are assigned to groups, roles |
| Scopes are associated at runtime  | User associated to groups upfront   |
| -                                 | Scopes can be used Optionally       |

  
---
![JWT](/images/enterprise-arch.png)  
![JWT](/images/enterprise-arch-02.png)  
![JWT](/images/enterprise-arch-03.png)  


## OAuth 2.0 - Multiple Sessions, Login and Logout
![JWT](/images/login-logout-01.png)  
![JWT](/images/login-logout-02.png)  

## OAuth 2.0 - User Association to scopes

## RBAC - Role based Access Control
OAuth 2.0 - User association to Groups  

![JWT](/images/rbac-01.png) 

* Steps:
  * Create the Application Groups
  * Assign groups to Application Users
  * Add "groups" claim to Access Token
  * Modify Application to look at **Groups** instead of **Scopes**


## OAuth 2.0 in the Cloud

![JWT](/images/cloud-01.png) 

![JWT](/images/cloud-02.png) 

![JWT](/images/cloud-03.png) 


## OAuth 2.0 and OpenID Connect
* OAuth 2.0 is for Authorization
  * Access token shloud contain only authorization information
  * Scopres, Roles, Grant Types, Flows, Tokens
  * No ID Token
  * https://tools.ietf.org/html/rfc6749
* OpenID Connect is the Identity layer on top of OAuth 2.0
  * ID Token contains user claims
  * /userinfo endpoint
  * Scopres : openid profile email
  * Can create custom claims
  * Adds more Response types
  * https://openid.net/specs/openid-connect-core-1_0.html


### Some examples of when using OAuth 2.0 and OpenId Connect
* Using booth OAuth 2.0 and OpenIdConnect
  * Our my-photos google albums (Used to get user info like name, email, etc.. and used oauth to call the resource server to get the albums and the photos)
* Only Using OpenIdConnect: (This apps gives us an option to log as...)
  * Spofity (Continue with facebook, apple, google)
  * Deezer
  * Udemy
* Only using OAuth 2.0
  * When we are not interested in knowing the user information, but interested in calling a resource API
  * Example a cronjob (client_credentials)
  * Shutterfly application

## Identity Brokers
![JWT](/images/identity-broker.png)
![JWT](/images/identity-broker-01.png) 
![JWT](/images/identity-broker-02.png) 
![JWT](/images/identity-broker-03.png) 

## OAuth 2.0 Best Practices
* Prefer Authorization Code Grant with PKCE
* Prefer Client Credentials Grant for Cron Jobs
* Avoid using Implicit Grant
* Avoid using Resource Owner Password Grant
* Store the secrets in a Safe place
* Rotate the secrets regularly
* Keep Access tokens short (5 Minutes)
* Avoid using local users of the Authorization Server
* Do not associate users with more scopes than needed
* Use the enterprise logout (all sessions) You should also revoke the tokens
* Do not store tokens or secrets in the browser or Mobile devices