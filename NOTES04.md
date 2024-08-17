# Google Authorization Server

## Client Registration

### LAB
* https://console.cloud.google.com
* https://developers.google.com/photos
* https://developers.google.com/photos/library/guides/get-started
* Login to the API console and Create a new Project. Example: "My Google Photos Project"
* Search for Google Photos API, Select it and click the Blue Button "ENABLE"
* Click on the Sandwich button, APIs & Services -> Click on CREDENTIALS
  * Click on CREATE CREDENTIALS, Select type = OAuth Cliend ID
    * Click on "Configure Consent Screen"
      * User Type = External, click on "CREATE"
        * App Name = "My Google Photos Reader"
        * Email Support = My Email
        * Click on "Save and Continue"
        * Do not need to define any scopes at this moment.
        * Click on "Save and Continue"
        * Test Users
          * Click + Add Users
            * inform my email, click ADD
        * Click on SAVE AND Continue, Summary
  * Go Back to APIs & Services -> Click on CREDENTIALS
    * Click on CREATE CREDENTIALS, Select type = OAuth Cliend ID
      * Application type, select "Web Application"
        * name = My Google Photos Reader
        * Authorized redirect URIs
          * click on ADD URI
        * Click ON CREATE
        * You will get a ClientID and a Client Secret, download it and keep it secure


### Finding Google Endpoints
* https://accounts.google.com/.well-known/openid-configuration
* Get the Google Photos Resource API endpoint: https://photoslibrary.googleapis.com
* Will be using postman to execute the http requests


### Deev Dive : Authorization Code Grant Type
https://developers.google.com/identity/protocols/oauth2  
The site above you can use it in order to search the scopes for the google APIs

1. Construct Authorization Request for grant type = Authorization code
   1. endpoint = https://accounts.google.com/o/oauth2/v2/auth
   2. http type = GET
   3. response_type=code
   4. client_id=```<my-client-id>```
   5. scope=openid%20profile%20email%20https://www.googleapis.com/auth/photoslibrary.readonly
   6. state=state123
   7. redirect_uri=http://localhost:8080
   8. access_type=offline (this one is for getting the refresh token as well)
   9. prompt=consent (This means users have to explicitly approve the authorize request after entering credentials)
   10. the entire URI = https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=```<my-client-id>```&scope=openid%20profile%20email%20https://www.googleapis.com/auth/photoslibrary.readonly&state=state123&redirect_uri=http://localhost:8080&access_type=offline&prompt=consent
   11. start a python HTTP server, to catch the redirect URL: (You need to start a local server on port 8080, in order to the redirect URI work properly)
       1.  **python3 -m http.server 8080**
   12. Paste the URI in a browser
   13. after executing the request copy the auth code
2. Send Request and extract the Auth Code
3. Construct a Token Request
   1. https://oauth2.googleapis.com/token
   2. HTTP TYPE = POST
   3. grant_type =
   4. client_id =
   5. client_secret =
   6. code =
   7. redirect_uri =
4. the curl command
   ```
    curl --location 'https://oauth2.googleapis.com/token' \
        --header 'Content-Type: application/x-www-form-urlencoded' \
        --data-urlencode 'grant_type=authorization_code' \
        --data-urlencode 'client_id=<my-client-id>' \
        --data-urlencode 'client_secret=<my-client-secret>' \
        --data-urlencode 'code=<my-auth-code>' \
        --data-urlencode 'redirect_uri=http://localhost:8080'
   ```
5. Send Request and extract Token
6. Send Google Photos Request with the Access Token
7. Send Request and see one Album 
    ```
    curl --location 'https://photoslibrary.googleapis.com/v1/albums/ANIwyWEO1rRoPxS3MWH5J7ao0kU7AtvTUwlQdfgEB-D0r0rtUbuZtOPJJw4N1mSmuspaJOwZhmLy' \
    --header 'Authorization: Bearer <my-bearer-token>'
    ```
8. Send Request to see All Album Photos
   ```
   curl --location 'https://photoslibrary.googleapis.com/v1/albums' \
        --header 'Authorization: Bearer <my-bearer-token>'
   ```
9. Get All Photos (Medias) from album
   ```
   curl --location 'https://photoslibrary.googleapis.com/v1/mediaItems:search' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer <my-bearer-token>' \
    --data '{
    "albumId": "ANIwyWEO1rRoPxS3MWH5J7ao0kU7AtvTUwlQdfgEB-D0r0rtUbuZtOPJJw4N1mSmuspaJOwZhmLy"
    }'
   ```


### Deev Dive : Authorization Code Grant Type - Part 2
POST https://www.googleapis.com/oauth2/v1/tokeninfo  
```
//this is for token verification
curl --location 'https://www.googleapis.com/oauth2/v1/tokeninfo' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'access_token=<my-access-token>'
```

#### Refresh Token
POST https://oauth2.googleapis.com/token  
grant_type  = refresh_token  
client_id  = my-client-id  
client_secret  = my-client-secret  
refresh_token  = the-refresh-token  

```
curl --location 'https://oauth2.googleapis.com/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=refresh_token' \
--data-urlencode 'client_id=<my-client-id>' \
--data-urlencode 'client_secret=<my-client-secret>' \
--data-urlencode 'refresh_token=<the-refresh-token>'
```

The reason it's called Bearer token is that whoever holds the token can access the API it's almost like this token is like your credentials it's like a user id and password. It's very sensitive, so for some reason that gets hacked and your access token is leaked then that access token can be used by the hacker to get into the api. So it is very sensitive and that's exactly why this is a POST request and that's why the validity of the access token must be very short.  

#### Send User Info Request
This is used by the Client only if access token has openid scope  
GET https://openidconnect.googleapis.com/v1/userinfo  
Auth Header  
```
curl --location 'https://openidconnect.googleapis.com/v1/userinfo' \
--header 'Authorization: Bearer <my-access-token>'
```

#### Explanation about JWT Token
It is composed by three parts, separated by a **'.'** character  
1. Header
   1. alg: RS256, is the signing algorithm
   2. kid: key id, kid is the reference to the signing key used to sign the token
   3. type: JWT
2. Payload, contains the information
3. Signature
   1. RSASHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), PRIVATE_KEY)

![Hybrid Encryption](/images/jwt.png)  

### JWKS Endpoint
**Json Web Key Set** endpoint **JWKS** comes into the picture, because every Authorization server has an endpoint which tells us that are all the keys which are used for signing the access tokens. So how do I find out where this endpoint is? Again we go back to the openid configuration  

The site followed field 'iss' in payload to get the Discovery EP. Applications should not do this.  

JWK -> Json Web Key  

GET https://www.googleapis.com/oauth2/v3/certs  
Just execute the above request in the broser or postman  
```
curl --location 'https://www.googleapis.com/oauth2/v3/certs'
```

Use the following site to convert a JWK to PEM:  
https://8gwifi.org/jwkconvertfunctions.jsp  


## PKCE Extension
* Proof Key For Code Exchange
* Extension of the Authorization Code grant type
* Usually used by public clients
* Send code_challenge with authorized requests
  * code_challenge = BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))
    * the big deal here is that you can go from the **code_verifier** to the **code_challenge** but you cannot go from the **code_challenge** to the **code_verifier**
* Send code_verifier with token request for grant type = authorization code
  * That's the proof

Here is how the PKCE flow works:  
![Hybrid Encryption](/images/PKCE-flow.png) 

### LAB PKCE
code_verifier = "This is a dumb verifier" (In real world scenarios, the code_verifier must be much londer and generated dynamically for each authorize request)  
https://tonyxu-io.github.io/pkce-generator (To generate code verifier)  

usdhsdskajsdksdjaskdjsakdjsak

GET https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=<my-client-id>&scope=openid%20https://www.googleapis.com/auth/photoslibrary.readonly&state=state123&redirect_uri=http://localhost:8080&code_challenge=usdhsdskajsdksdjaskdjsakdjsak&code_challenge_method=S256  

**python3 -m http.server 8080**  

Call the endpoint to get a access token:  POST  
```
curl --location 'https://oauth2.googleapis.com/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'client_id=<my-client-id>' \
--data-urlencode 'client_secret=<my-client-secret>' \
--data-urlencode 'code=<the-auth-code> \
--data-urlencode 'redirect_uri=http://localhost:8080' \
--data-urlencode 'code_verifier=This is a dumb verifier'
```

## Google Playground
https://developers.google.com/oauthplayground/  
https://developers.google.com/identity/protocols/oauth2/scopes  


## Spring-boot Java Project
See project "my-photos-client-3.3.zip", albums-client

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId> <!-- oauth2 client -->
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId> <!-- this is for the spring security jar files -->
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId> 
</dependency>
```
Now, one good thing about the spring security is that once we include the **oauth2 client** as dependency in the maven, the entire application is set up as oauth2 client. Whenever any user accesses any of the client url the OAuth flow automatically gets initiated based on the setting in the Spring Boot application.properties. There are ways to set security configuration but we have the default setup.  
The default setup protects all urls of the Application.  

---
After a successful Authentication, an **Authentication** object is set by Spring Security. For Oauth 2.0, this Authentication class is called **OAuth2AuthenticationToken** and it indirectly implements the Authentication interface.  
An application can make use of **OAuth2AuthenticationToken**, to retrieve the user claims in the JWT token scopes and groups.  
```
  OAuth2User principal = oAuth2AuthenticationToken.getPrincipal();
```

In practice, Spring Security can handle **Global Logout** as well. However, Google does not follow the global logout specifications - so I created this logout method to be compliant with both Okta and Google.  
Also note that the downloaded code version includes a post logout url so the Authorization Server will redirect back to the application after sucessful logout.  
It is also a good idea to revoke the access token and refresh token.  

**Note very important**: http://localhost:8080/login/oauth2/code/google (Use this redirect URI for the spring-boot demo) 