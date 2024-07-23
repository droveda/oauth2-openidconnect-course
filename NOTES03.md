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
