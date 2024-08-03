# Assignment 01

This assignment is to call the Google Drive API Resource Server successfully using an access token. Please follow the instructions below to structure this assignment. If you need help, download the attached text file "Google Drive Authorization Code Flow.txt" for more help.  



## (A) FOLLOW THE STEPS BELOW
1. From your Google Developer Console, create a client id and client secret (If you don't have a Google account, create one)
   1. https://console.developers.google.com
   2. Make sure that you enable Google Drive API by clicking on "ENABLE APIS AND SERVICES" link
2. Find the Service Endpoint and Scope needed to call the Google Drive API
   1. https://developers.google.com/identity/protocols/oauth2
   2. Click on "OAuth 2.0 Scopes" link on the left and find the Endpoint for Google Drive API you want to call and the scope needed to access it. 
   3. For example, you can decide to call "https://www.googleapis.com/drive/v3/files" API
3. Construct Authorization Request to send to the Google Authorization Server (use scopes : **openid, profile, email and the Google Drive scope**)
   1. https://accounts.google.com/.well-known/openid-configuration (Use the Discovery endpoint to get all other endpoints)
   2. https://tonyxu-io.github.io/pkce-generator/ (Use this to generate a code challenge from a verifier)
4. Send Authorization Request using Chrome and extract Authorization Code
   1. You would have to log in using your Google Credentials and authorize the request.
5. Find the Google Token Endpoint and construct a Token Request
   1. https://accounts.google.com/.well-known/openid-configuration (Use the Discovery endpoint to get all other endpoints)
6. Send Token Request using Postman and examine the Response. Identify the access token from the Response
7. Send Google Drive Request using Postman to receive all your Google Drive documents (Get endpoint from previous step)
8. Identify ID Token and examine contents of the JWT token using jwt.io
9. Find the User Info endpoint and send a Request to the User Info endpoint
   1. https://accounts.google.com/.well-known/openid-configuration (Use the Discovery endpoint to get User Info endpoint)
10. Use the Token Info endpoint to send a Request to get more information of a Token. This endpoint is proprietary to Google. You can look up the previous lectures to see how to do that or research on the internet.
    1. https://www.googleapis.com/oauth2/v3/tokeninfo
11. Let Access Token expire (wait for 60 mins) and check that you cannot call a Drive API with an expired token
12. (B) REPEAT THE ABOVE STEPS IN (A) BY PROVIDING ONLY SCOPE : Google Drive scope  
13. (C) REPEAT THE ABOVE STEPS IN (A) BY PROVIDING THE FOLLOWING SCOPES : openid and Google Drive scope 


### Assignment Questions
1. What differences did you observe when you did not include openid, profile and email scopes (basically only application scope) ? 
   1. When I ask for an access token using the authorization endpoint "https://accounts.google.com/o/oauth2/v2/auth". I don't receive any user data like email, name, openID id_token in the response. I only get the access_token, token_type, expires_in and scope data.
   2. I Can call the google drive API to retrive the documents
   3. I cannot call the user info endpoint, it returns a 401 Unauthorized
2. What did you observe when you included openid as scope but did not add profile and email ?
   1. When I ask for an access token using the authorization endpoint "https://accounts.google.com/o/oauth2/v2/auth". I received back the access_token, token_type, expires_in, scope and also the id_token data.
   2. I Can call the google drive API to retrive the documents
   3. I can also call the user info endpoint and it returns the **sub** and **picture** data.
3. In the token endpoint response, how do you know the valid time limit of the token ? 
   1. I can know it looking at the field called "expires_in". It contains the token expiration in seconds
4. For (A) - What is the difference in response from /userinfo endpoint and the /tokeninfo endpoint ? When would you choose to use /tokeninfo endpoint versus /userinfo endpoint?
   1. The user info endpoint returns information about the user, like name, email, picture
   2. The foken info endpoint returns information about the access token, for whom it was issued to, audience, scope(s), expiration
   3. I can use the /userinfo to get more information about the user and display it on my application
   4. I can use the /tokeninfo to track the expiry time for my current token, check the scopes and execute some validations with the data.

### Answers Steps
* Scope for google drive: https://www.googleapis.com/auth/drive.readonly
* Authorization endpoint: https://accounts.google.com/o/oauth2/v2/auth
* Endpoint that I will call: GET https://www.googleapis.com/drive/v3/files
* Building the authorization request:
  * GET
  * https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=<my-client-id>&scope=openid%20profile%20email%20https://www.googleapis.com/auth/drive.readonly&state=state123&redirect_uri=http://localhost:8080&code_challenge=Yje7IVvMh8SrjjyF-8aBh-q0HuUTiuOh_1YEbxcJbGo&code_challenge_method=S256
* Contruct a token request:
  * https://oauth2.googleapis.com/token
  ```
  curl --location 'https://oauth2.googleapis.com/token' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --data-urlencode 'grant_type=authorization_code' \
    --data-urlencode 'client_id=<my-client-id>' \
    --data-urlencode 'client_secret=<my-client-secret>' \
    --data-urlencode 'code=<the-auth-code>' \
    --data-urlencode 'redirect_uri=http://localhost:8080' \
    --data-urlencode 'code_verifier=<my-code-verifier>'
  ```
* Google Drive request using the token:
  ```
  curl --location 'https://www.googleapis.com/drive/v3/files' \
        --header 'Authorization: Bearer <the-access-token>'
  ```
* User info endpoint: https://openidconnect.googleapis.com/v1/userinfo
  ```
  curl --location 'https://openidconnect.googleapis.com/v1/userinfo' \
    --header 'Authorization: Bearer <the-access-token>'
  ```
