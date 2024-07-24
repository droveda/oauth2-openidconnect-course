# Oauth2

Oauth 2.0 Authorization Server  

## Oauth 2.0 Authorization Framework (RFC 6749)
The OAuth 2.0 authorization framework enables a **third-party application** to obtain **limited** access to an **HTTP service**, either **on behalf of a resource owner** by orchestrating an **approval interation** between the resource owner and the HTTP service, or by allowing the third-party application to obtain **access on its own behalf**.  

### Some terms
* Authorization Server
* Thirdy Party Application
* Resource Owner
* Resource Server
* Access Token also called bearer token

### Authorization in OAuth
1. The Authorization in OAuth (stands for Open Authorization) refers to the "permission" given by the user to the Third Party application to act on his or her behalf. In the Shutterfly demonstration, I clicked on the Allow button to give permission to the Shutterfly application to use my Google Photos. 
2. Do not confuse this "Authorization" with traditional definition of Authorization for Enterprise applications. The traditional definition of Authorization refers to the ability of an Application to have different groups of users perform different actions within the application itself. 
3. Throughout the course, I assume that the Shutterfly interaction with the Google Authentication Server is initiated from the backend server and not from the browser. In actual practice, this may or may not be true but this assumption helps me to explain OAuth 2.0 
4. In future Lectures and Sections, I will describe exactly how everything works technically.


### OAuth 2.0 - Roles
* Resource Owner
  * The User
  * User Agent
* Resource Server
  * REST API which protects resource
* Client
  * Application that needs access
* Authorization Server
  * Authorizes the client
  * Gives out access tokens
  * Many on the internet
  * OAuth Endpoints

A Resource Owner should never send the password to the Third Party (untrusted) Application  


### Oauth 2.0 - Client Registration
* Administration utility to register a Client
* Redirect a Client
* Redirect URIs
* Client ID, Client Secret
* When Client sends request to Authorization Server, it will send Client ID, Client Secret as well

![Hybrid Encryption](/images/client-registration.png)

### Oauth 2.0 - Opaque Token
Access Token is sent in an HTTP Header  
Authorization : Bearer <token>  
Think about the access token as a key which opens the door to the Resource Server  
Scopes in an Access Token is used by Resource Server for Authorization  
The Resource Server needs to verify the token in the Authorization Server as it is "opaque"  

![Hybrid Encryption](/images/opaque-token1.png)  

![Hybrid Encryption](/images/opaque-token2.png)  

Autorization: Bearer 1d52703551c84012a7b0af09300092ea6  

### Oauth 2. Structured Access Tokens (JWT)
JWT Token is sent in an HTTP Header   
Authorization : Bearer <jwt token>  
The structured token is signed by the Authorization Server using it's private key, and so to verify the token the Resource Server would have to verify the signature using the public sign key, so in a sense instead of sending a separate request to the Authorization Server to verify the token, the Resource Server will verify the signature and the token itself, so this avoid a new call to the Authorization Server.  
But how would the Resource Server get the public signing key? The Authorization Server would have a specific endpoint that would retrieve the signing key and the Resource Server would use that.  
Other verifications are also done - Expiry Time, Issuer validation, etc...  

![Hybrid Encryption](/images/iwt-token01.png)  
![Hybrid Encryption](/images/jwt-token02.png)  
![Hybrid Encryption](/images/jwt-token03.png)  


### Oauth 2.0 - OAuth Scopes
A Scope is just a string. Think about it as an application role.  
In enterprise applications we typically do not explicity authorize an application to make an api call on our behalf.  
IN enterprise applications - the client, the resource server and Authorization server are all usually controlled by the same company and there is really no notion of a Third Party application as such. Users are typically associated in active directory with groups, so what we really need is automatic authorizations to take place and the scopes to be set automatically based on the user Active Directory roles.

![Hybrid Encryption](/images/scopes01.png)  

An **access token** opens the door to the **Resource Server**. The Resource Server will first verify the token (i.e signature is good, expiry date validation passes, issuer is the right Authorization server and other checks). If verification passes, then Resource server will do authorization checks based on **scopes** associated with the access token. A Resource server can look at multiple scopes to figure out if the API call should be Accepted or Rejected. Think about a scope as an access privilege granted to the user to perform a certain functionality within the **Resource Server**. Here I refer to the traditional definition of Authorization as defined for Enterprise Applications.  


### Oauth 2.0 - Endpoints (EP)

![Hybrid Encryption](/images/endpoints01.png)  


### Token Revocation Endpoint
There is an additional Endpoint which is not shown in the previous lecture - the **Token Revocation** endpoint. Using the Token Revocation endpoint, a client can invalidate an access token and other related tokens. It's used for cleanup after a user logs out of the client application and in cases where the token is suspected of being compromised.  

This will be covered in detail in future lectures.  

### Grant Types
* **Authorization Code Grant**
  * ![Hybrid Encryption](/images/auth-code-grant.png)
* **Authorization Code Grant (PKCE)** (Proof Key of Code Exchange)
  * Where a parameter called **code verifier** is dynamically generated by the client, a code challenge created and sent when calling the Authorization endpoint.
  * Ideally client secret is not passed in PKCE. Code Verifier is passed. Think abount Code Verifier as dynamically generated secret
  * ![Hybrid Encryption](/images/auth-code-pkce-grant.png)
* **Implicit Grant (Deprecated, should not be used)**
  * ![Hybrid Encryption](/images/implicit.png)
* **Client Credentials**
  * Is the simplest grant type where there is no Resource Owner.
  * ![Hybrid Encryption](/images/client-credentials.png)
* **Resource Owner Password Credentials (Deprecated, should not be used, or only if the Resource Server and Client Application are from the same Organization)**
  * ![Hybrid Encryption](/images/resource-owner-password-credentials.png)
* **Refresh Token**
  * Access tokens are valid for a certain ammount for time, for example 60 minutes
  * When we get the access token, we can also get a refresh token along with that.
  * The refresh token usually has a much higher timeout setting, in fact in Google it never expires.
  * The client can then use the refresh token to get a new access token so basically the client will make another call to tje Authorization server where the **grant type is refresh token** and the parameters would be the **expired access token** and the **refresh token** techinically it is a separate grant type, but it is mostly used in conjunction with an Authorization code grant or the Resource Owner password credentials grant.
  * ![Hybrid Encryption](/images/refresh-token.png)
* **Token Revocation**
  * ![Hybrid Encryption](/images/token-revogation.png)



#### Types of Clients
* Confidential Client - Client that resides on the server. For example a java application running on a tomcat
* Public Client - These clients run on an insecure environment like a browser (Ajax applications, SPA, Smartphone)


#### Notes and questions
1. Q: Why the Authorization Server when it redirects why does it not redirect directly the access token? Why do we have to make a separate call to get an access Token?
   1. A: The reason for this is that we do not want to expose the access token on the front-end side. We don't want to expose it on the front channel. It should always remain on the back channel as much as possible.
   2. A: The auth code is valid for a few minutes and it needs to be exchanged for an access token. So even if the auth code is compromised it really still requires a client identifier and a client secret to get an access token. So without that you cannot get an access token. But if you were to send an access token using the front channel through the browser as a redirect then the access token cloud be stolen and then the access token can be used by anybody to gain access. So this is the reason why the proccess is broken into two parts - one to get the auth code and the second to actually get the access token.


### Open ID Connect Scopes
The oauth access token have minimal information, minimal information about the user, for example in a JWT token there us the **sub** subject field, which can contains for instance an user email.  
The JWT is small and contains eniugh information for verification of the token, and that's how it should be, because this access token will be sent as part of every API request and we want this to be as small as possible. The OAuth 2.0 specification does a good job of that.  
OpenID Connect fill the gap, in order to get more information of the user, for instance User name, lastname, department, photo, etc...  
OpenID Connect defines a set of scopes that can be used to retrieve the user information after the user gets authenticated.  
OpenID Scopres are built-in scopes which allow the client to get information about the user and by the way the user would have to still authorize these as well  
The Client will have to specify the OpenID specifc scopes when asking for the token. The OpenID scopes are openid, profile, email, address and phone.  
The Authorization server will return an access token as well as an **OpenID ID Token** which contains user information. If you specify openid as a scope, the client can also call the user information that is the user info  

* OpenID Scopes and Token
  * openid profile email address phone
  * ID Token contains User information


![Hybrid Encryption](/images/openid01.png)

![Hybrid Encryption](/images/openid02.png)


### OAuth Grant Types Summary
![Hybrid Encryption](/images/grant-types-summary.png)

