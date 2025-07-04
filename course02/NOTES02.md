# Oauth and OpenID Connect

## Oauth 2.0 Actors
* Authorization Server
* Client Application
* Resource Server
* User Agent
* Resource Owner

Well-known openid configuration endpoint.   
1. First, Register the application with the authorization server.
   1. ClientID and ClientSecret will be provided
2. Scopes - The access token is usually associated with scopes


### Access Tokens AKA Bearer Tokens
* Opaque token
  * it is a simply long string token - the token is just a reference for the actual information (user id, expiration, scopes,created at, etc..)
* Structured Token (JWT)
  * Contains a structure in a json format with the token data (exp, iat, jti, iss, sub, typ, nonce, scope, etc...)
  * header, body and signature

```
GET /api/v4/projects?owned=true HTTP/1.1
Authorization Bearer e4f2465tty6673s
Host gitlab.com
User-Agent HTTPie
```

### Oauth Grant Types
* Authorization Code
* Authorization Code with PKCE
* Implicit (Deprecated)
* Refresh Token
  * get new access tokens when they have expired and without going through the Authorization Code flow again
* Client Credentials
  * Client Credentials grant type is not associated with a Resource Owner
* Device Code
* Password (Deprecated)


#### auth-code-grant-type
![Auth Code](/course02/images/auth-code-grant-type.png)



#### auth-code-with-pkce-trant-type
* Proof key for code exchange
* Extemsion of the Authorization Code grant type
* Usually used by public clients

1. Generate a code_verifier
   1. min 43 chars and max = 128 chars
   2. Random and impreactical to guess
2. Send code_challenge with authorize request
   1. code_challenge = BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))
3. Send code_verifier with token request for grant type = code
   1. That is the proof 

![Auth Code Pkce](/course02/images/auth-code-with-pkce-grant-type.png)


#### Client Credentials Grant Type
scheduled tasks, batch processing, etc...  
![Client Credentials](/course02/images/client-credentials-grant-type.png)

In some enterprise applications it can by used like a **Service Account**, you do not have a specific user logged or acting on behalf of a user.  


### OpenID Connect
OpenID Scopes and Token. 
* openid profile email address phone
* ID Token contains User Information
* /userinfo endpoint access

### OpenID Connect in an Enterprise
Roles and Groups. 
Scopes.  

![Enterprise](/course02/images/enterprise.png)