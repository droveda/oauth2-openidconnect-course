# Hands On Experiment with Okta Authorization Server using Postman

This assignment will help you to practice any interactions with the Okta OAuth 2.0 Authorization Server. You will be using the Authorization Code grant type (without PKCE Extension) to get an access token from the Okta Authorization Server and use that to call the FakeBook Resource API.  

## Instructions
This assignment is to call the Fakebook API Resource Server successfully using an access token obtained from Okta Authorization Server. We will be using the Authorization Code grant (but we will not use the PKCE Extension). Along the way, we will analyze the tokens, expire the tokens, refresh the tokens and revoke the tokens.  

Please follow the instructions below to structure this assignment. I have laid out the steps that you should follow and note that you will have to do some research in constructing some of the URLs. For that you can look at Okta documentation at Okta Reference Documentation (https://developer.okta.com/docs/reference/api/oidc/#endpoints)  

1. STEP 1: Create an account in Okta (if you have not already done so)
   1. Identify your Issuer URI from Okta Developer Console
   2. Identify your Discovery Endpoint from the Issuer URI
   3. Create the two scopes needed for Fakebook API Resource Server : fakebookapi.read, fakebookapi.admin 
2. STEP 2: Get a running Fakebook API Resource server locally on your Laptop. This will run on port 8080.  
   1. Follow the instructions in lecture "FakeBookAPI Resource Server setup" to setup the Server
3. STEP 3: From the Okta Console, create a new OAuth 2 Client called FakebookAuthCodeClient for a Web Application
   1. We covered this in the Lectures in this section
   2. Use Sign-in redirect uri as http://localhost:{port} 
   3. Use Sign-out redirect uri as http://localhost:{port}
   4. {port} should be different than 8080. Why?
   5. Note down your ClientID and Client Secret
4. STEP 4: Construct an Authorization URL to send to Okta Authorization Server
   1. Identify your authorize endpoint
   2. Use scopes "openid profile email fakebookapi.read offline_access"
   3. See format below :
      1. ```<AUTHORIZE ENDPOINT>?response_type=code&client_id=<CLIENTID>&state=state123&redirect_uri=<REDIRECT URI>&scope=<SCOPES>&nonce=test123```
5. STEP 5: Run a local Web Server on port {port} to capture the Authorization Code (This step is optional and needs Python Installed)
   1. ```python -m http.server <port>```
6. STEP 6: Send and Authorization Request using Chrome and extract Authorization Code
   1. You would have to log in using your Okta Credentials
   2. Capture the Authorization Code from the Web Server output console or from the browser URI
7. STEP 7: Construct a token request to get an access token using grant type of authorization code
   1. Identify the Okta token endpoint 
8. STEP 8: Send the Token Request using Postman and examine the Response.
   1. If you get an error during token request, then you will have to send authorize request again
   2. Identify the access token and ID Token from the Response
   3. Identify Access Token Expiry Time
   4. Identify the Refresh Token
9. STEP 9: Call the Books Resource API to retrieve all books with the access token 
   1. See that the list of Fake books are retrieved
      1. Resource Endpoint to call : **http://localhost:8080/books**
10. STEP 10: Find the User Info endpoint and send a Request to this endpoint
    1.  See that you receive the user Information 
11. STEP 11: Examine contents of the Access and ID tokens
    1. Use **http://jwt.io**
    2. Check that ID token contains the **nonce** parameter with the value as expected
12. STEP 12: Let Access Token expire (find out the token timeout) and check that you cannot call the Resource API with an expired token
    1. You should receive an Unauthorized 401 error
13. STEP 13: Use the refresh token to create a new Access token (using a token request with grant type = **refresh_token**)
    1. Call the Fakebook Resource API and see it succeeds
14. STEP 14: Revoke the refresh token 
    1.  Identify the Okta Revocation Endpoint 
15. STEP 15: Check that refresh token cannot be used to create new access tokens
16. OTHER STEPS:
    1. You can experiment with various calls to introspect API, not providing openid scopes or calling unauthorized endpoints in FakeBookAPI. 
    2. Use the **Okta Reference Documentation** (https://developer.okta.com/docs/reference/api/oidc/#endpoints) as a reference.
    

### Questions:
1. What challenges or difficulties did you face when doing this Assignment on your own ? 


### Answers
1. ISSUER URI: https://dev-29895772.okta.com/oauth2/default
2. Discovery Endpoint: https://dev-29895772.okta.com/oauth2/default/.well-known/openid-configuration
3. ```https://dev-29895772.okta.com/oauth2/default/v1/authorize?response_type=code&client_id=<CLIENTID>&state=state123&redirect_uri=http://localhost:7001&scope=openid%20profile%20email%20fakebookapi.read%20fakebookapi.write%20offline_access&nonce=test123```



### Authorization Basic





