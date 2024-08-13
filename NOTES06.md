# JWT, Client Authentication and Other Titbits

* What's in the JWT?
* Client Authentication methods
  * Until now we have used client secret as a mechanism for client authentication, and it's by far the most widly used client authentication mechanism. However, it may not actually be the most secure mechanism for all scenarios. Okta has made some changes to include public key private key as part of the client authentication **(TODO test this with Azure Microsoft Entra ID)**
* Other Titbits

## JWT
![JWT](/images/jwt-token03.png)  

* Structure:
  * {encoded header}.{encoded payload}.{encoded signature}
  * How it is created? The autorization knows who the resource owner is, basically the user on the system, and it can pull up all the properties of the resource owner - like the subject, the scopes, the email, etc... For constructing the ID token there will be a lot more information needed, like fistName, lastName, email.
    * First it will create the header and payload JSON objects from all the information that it has.
      * ```alg: RS256``` this property in the header tells that the JWT will be signed using the RSA256 algorithm
    * Next is does the Base64 URL Encoded, Base64 is to make sure that any binary data is converted to text and URL encoding to make sure that the access token can be sent as part of the URL.
    * The final puzzle is how the signature is created:
      * RSA256 is the shorthand form for RSA algorithm and SHA 256 algorithm
        * Why are there two - RSA and SHA?
          * The way the signature is created is, first a hash is created from the data using a hashing algorithm, then the hased output is signed using the RSA private key of the Okta authorization server.

```
"request_object_signing_alg_values_supported": [
    "HS256",
    "HS384",
    "HS512",
    "RS256",
    "RS384",
    "RS512",
    "ES256",
    "ES384",
    "ES512"
  ]

  // explanation of the different types of signing algorithm 
  HS => HMAC with SHA-NNN (Use Secret Key)
  RS => RSA with SHA-NNN (Use Public/Private Key)
  ES => ECDSA with SHA-NNN (Use Public/Private Key) (Eliptical curve digital signature algorithm)
```


* First, the verification of a digitally signed JWT token gives the application assurance of two things:
  1. No one has tampered with a token on its way.
  2. It comes from the authorization server and no one else.
* The access token can be read by anyone, there is nothing stopping anyone to read the header and the payload without verifying.
  * We just have to base 64 URL decode the header and the payload to just see it.
  * That's the reason confidential information like passwords should not be put in the access token.
  * We can certainly think about encrypting the token if that is necessary, but encryption of the token is not often used. Why? Because the tokens are usually sent on a secure HTTPS channel anyways.


## Client Authentication
In all of our demos and projects, there was client authentication going on using the client id and client secret.  
CliendID and ClientSecret are the credentials given to the client itself, and the authorization server uses the client credentials to authenticate the client.

```
"token_endpoint_auth_methods_supported": [
    "client_secret_basic",
    "client_secret_post",
    "client_secret_jwt",
    "private_key_jwt",
    "none"
  ],

"introspection_endpoint": "https://dev-29895772.okta.com/oauth2/default/v1/introspect",
  "introspection_endpoint_auth_methods_supported": [
    "client_secret_basic",
    "client_secret_post",
    "client_secret_jwt",
    "private_key_jwt",
    "none"
  ],
```

* Client Authentication Methods:
  * client_secret_post (We have used this)
    ```
    curl --location 'https://dev-29895772.okta.com/oauth2/default/v1/token' \
        --header 'Content-Type: application/x-www-form-urlencoded' \
        --header 'Cookie: JSESSIONID=69FBA99863DFBDF56D93D50E3B2490E9' \
        --data-urlencode 'grant_type=client_credentials' \
        --data-urlencode 'client_id=<my-client-id>' \
        --data-urlencode 'client_secret=<my-client-secret>' \
        --data-urlencode 'scope=fakebookapi.read fakebookapi.admin'
    ```
  * client_secret_basic
    ```
    value = Base64.encode(username + ':' + password);

    curl --location 'https://dev-29895772.okta.com/oauth2/default/v1/token' \
        --header 'Content-Type: application/x-www-form-urlencoded' \
        --header 'Authorization: Basic <my-client-id+':'+my-client-secret>' \
        --header 'Cookie: JSESSIONID=C5153B4219CCBC1907B4EB90AC7A5F51' \
        --data-urlencode 'grant_type=client_credentials' \
        --data-urlencode 'scope=fakebookapi.read fakebookapi.admin'
    ```
  * client_secret_jwt
    * ![JWT](/images/client_secret_jwt.png)  
    * ![JWT](/images/client_secret_jwt-02.png)  
    * ![JWT](/images/client_secret_jwt-03.png)  
    ```
    The authorization server needs to verify that the request came from a valid client, and client secret was one of the ways of doing this.
    But instead of sending the secret directly to the authorization server, can we just sign a JWT with a client secret and then send it over to the authorization server?
    The auth server can then verify the token with the same secret key, and that would confirm that the request came from the correct client.
    This is what happen in this client authentication type
    ```
  * private_key_jwt
    * This is the most secure whay of client authorization
    * The **private key** will remain with the client  and the **public key** will be associated with the client on the **authorization server**. 
    * Should use RS or ES algorithm, for example RS256
    * The payload remains the same 
      * you need to sign using the client private key
    * The auth server on the other end will verify the JWT token using the client's public key
    * ![JWT](/images/private-key-jwt-01.png)  
    * ![JWT](/images/private-key-jwt-02.png)  
  * none


## Notes
The OpenID Connect Core 1.0 Documentation specifies the different methods for Client Authentication  
https://openid.net/specs/openid-connect-core-1_0.html#ClientAuthentication  


The following RFC contains documentation related to the Client Authentication using JWT. The JSON fields for the JWT are described in detail.  
https://www.rfc-editor.org/rfc/rfc7523.html#page-5  


In addition, Okta documentation can be found here  
https://developer.okta.com/docs/reference/api/oidc/#client-authentication-methods  
https://developer.okta.com/docs/guides/build-self-signed-jwt/java/main/  
