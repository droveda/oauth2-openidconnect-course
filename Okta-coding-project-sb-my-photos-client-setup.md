# Okta Coding project : Spring Boot "My Photos Client" setup
## Try this one using Azure Microsoft Entra ID

## For this setup, you would have to run two Servers 
* My Photos Resource Server API (port 8081)
* My Photos Client (port 8080)

### Instructions to set up My Photos Resource API with Okta Authorization Server
* Download the my-photos-resource-server-3.3.zip (Spring Boot 3.3) file from lecture "Okta Coding Project : Code Walkthrough". There is also a my-photos-resource-server-2.7.zip which uses Spring Boot 2.7.x but I recommend you use 3.3.x Version
* Unzip the zip file in your local directory
* Change to [local directory]/okta-albums-api
* Open the file how-to-use.txt for instructions on project setup
* Follow the steps to run the My photos Resource Server on port 8081
* Setup the photolibrary.read scope in Okta Authorization Server


### Instructions to create scope photolibrary.read in Okta Authorization Server
* Login to the Okta Developer Console.
* Click on Admin button. You will see links on the left panel.
* Click on Security Link; then click API
* Click on default Authorization Server
* Click on the Scopes link
* Click on Add Scope button
  * Name = photolibrary.read
  * Display phrase = Read access to My Photos Resource API
  * Click on Create button

### Instructions to create a Client ID and Client Secret in Okta Authorization Server
* Login to the Okta Developer Console.
* Click on Admin button. You will see links on the left panel.
* Click on Applications Link
* Click on "Create App Integration" button
  * Sign on method = OIDC - OpenID Connect
  * Application type = Web Application
* In the New Web App Integration page, enter the following
  * App Integration Name : MyPhotosReader
  * Grant Type = Authorization Code, Refresh Token
  * Sign-in Redirect URI : http://localhost:8080/login/oauth2/code/okta
  * Sign-out Redirect URIs :  http://localhost:8080
  * Controlled access :  Allow everyone in your organization to access
* Click Save button
* Capture the Client ID and Client Secret (You will need this for the My Photos Client setup)


### Instructions to set up My Photos Client with Okta Authorization Server
* Download zip (You might have already followed the steps for Google. If so,  you don't need to do this)
  * Download **my-photos-client-3.3.zip** file from lecture "Okta Coding Project : Code Walkthrough"
  * Unzip the zip file in your local directory (You may use the 2.7 version instead but I recommend you use 3.3 version)
  * Change to [local directory]/albums-client
  * Open the file how-to-use.txt for instructions on project setup 
* During the step (3) of how-to-use.txt, you will setup for Okta Authorization Server (instead of Google)
  * Set up a Client ID and Client Secret from your Okta Developer Console (See Instructions above)
  * Make sure that the following configuration property is set to 'okta' in albums-client project.
    * File Location : albums-client\src\main\resources\application.properties ``` pring.profiles.active=okta ```
  * Set the Client ID, Client Secret and Okta Issuer in application-okta.properties 
  * To compile, package and run the My Photos Client at port 8080 ``` mvn spring-boot:run ```
  * After this, you should be able to access the My Photos Client as ``` http://localhost:8080 ```


### OAuth 2.0 with Okta
* https://www.oauth.com/playground/index.html
* https://www.okta.com/developer/signup
* https://developer.okta.com/docs/

### OAuth 2.0 useful utility links
* https://jwt.io/
* https://tonyxu-io.github.io/pkce-generator/

### Spring Security Documentation
* https://docs.spring.io/spring-security/reference/index.html
* https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html