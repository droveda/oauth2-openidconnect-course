# Google Coding Project : Spring Boot "My Photos Client" setup
* See project "my-photos-client-3.3.zip"

## Instructions to create a new Client ID and Client Secret For Application "Google Photos Reader"
* Go To Google APIs web page and choose your project from the top left menu 
* Click Dashboard -> Enable Apis and Services -> Search for "Photos Library API" -> Enable it
* Click Navigation Menu -> API & Services -> Credentials
* Click on Create Credentials. Choose OAuth Client ID
* Enter following client information:
  * Application Type : Web application
  * Application Name : Google Photos Reader
  * Add Authorized Redirect URI : http://localhost:8080/login/oauth2/code/google 
  * Capture the client id and secret


## Instructions to set up My Photos Client with Google Authorization Server
* Download the **my-photos-client-3.3.zip** (Spring Boot 3.3.x) file from lecture "Google Coding Project : Code Walkthrough". There is also a my-photos-client-2.7.zip which uses Spring Boot 2.7.x but I recommend that you use Spring Boot 3.3.x
* Unzip the zip file in your local directory 
* Change to [local directory]/albums-client 
* Open the file how-to-use.txt for instructions on project setup 
* During the step (3) of how-to-use.txt, you will setup for Google Authorization Server
  * Set up a Client ID and Client Secret from you Google Developer Console
  * Make sure that the following configuration property is set to 'google' in albums-client project.
  * File Location : albums-client\src\main\resources\application.properties
  * Set the following two properties by creating a client id and secret in the Google Developer Console as mentioned in the lectures. Also see instructions at the top.
    * File Location : albums-client\src\main\resources\application-google.properties <client-id> <client-secret>
  * Note : Make sure you downloaded JDK 21 (Update as needed)
  * To compile, package and run the My Photos Client at port 8080 
    * ```mvn spring-boot:run```
  * After this, you should be able to access the My Photos Client as 
    * ```http://localhost:8080```


## OAuth 2.0 with Google APIs
* https://console.developers.google.com/ (Google Develop Console)
* https://developers.google.com/identity/protocols/oauth2 (Using OAuth 2.0 to Access Google APIs)
* https://developers.google.com/identity/protocols/oauth2/openid-connect (Google OpenID Connect For Developers)
* https://accounts.google.com/.well-known/openid-configuration (Google OpenID Configuration)
* https://developers.google.com/photos/library/guides/overview (Google Photos Overview)
* https://developers.google.com/photos/library/reference/rest (Google Photos REST API)
* https://developers.google.com/oauthplayground/ (Google OAuth 2.0 Playground)

## OAuth 2.0 useful utility links
* https://jwt.io/ (JWT Debugger)
* https://tonyxu-io.github.io/pkce-generator/ (An example Online PKCE Generator Tool)

## Spring Security Documentation
* https://docs.spring.io/spring-security/reference/index.html (Spring Security Reference Documentation)
* https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html (Spring Boot with OAuth 2.0 Integration)