# Single Page Application Using Angular

![Angular](/images/angular01.png)  

![Angular](/images/angular02.png)  

## Angular Project Setup

* My Photos Angular Client has been tested with the following 
  * Node 20.14.0
  * NPM 10.4.0
  * Angular 17.2
* Instructions to set up My Photos Angular Client 
  * Download the attached ZIP file in this lecture
  * Unzip the zip file in your local directory 
  * Change to [local directory]/myalbum
  * Open the file how-to-use.txt for instructions on project setup 
  * Follow the steps to run the "My Photos Angular Client"
    * For Google, you need to make sure that the client id is registered in the Google Developer Console
    * For Okta, you need to register a Client with following properties
      * Application type  = "Single Page Application" 
      * Allowed Grant Types = Authorization Code and Implicit (You would be using the Authorization Code)
      * Login redirect URIs = http://localhost:4200
      * Logout redirect URIs=  http://localhost:4200
    * For Okta, you would also have to run the My Photos Resource API at port 8081


* What precautions should you take when writing a Single Page Application ? 
  * Use Authorization Code Grant with PKCE and store the access token in a safe place
  * This is the best option because the access token is less likely to fall into the hands of a hacker. Also, the access token should be kept in a safe place like Session Storage. 

# Protecting Native Applications (Desktop Mobile)

It is a public client, it runs in the front channel.  
This means we cannot use the client secret we can use the clientId but not the client secret.  
Public clients should always use the grant type of **authorization code with PKCE**.  

But how do we get around with the absence of user agent and the web server in a Desktop application?  
It opens a browser from the person's computer.  
The redirect URL should be a loopback address to the same localhost that is 127.0.0.1  port 8080, it could be any port. I just chose 8080.  
This redirect URI should be registered during the client registration.  
The desktop application should also generate a code verifier, compute the PKCE code challenge and send that along with the authorize request.  
The desktop application should have a server running on the llopback address 127.0.0.1 port 8080 before it opens the system browser to send the authorize request.  
All platforms have a way to do this whether it is a Java or NodeJs or .NET.  
The embebed web server will get the authorization code, with the auth code the desktop application will call the token endpoint to get an access token.   

![desktop](/images/desktop-01.png) 

# Using OAuth in Mobile Applications

![desktop](/images/mobile-01.png)  
![desktop](/images/mobile-02.png)  

## RFC Link for Native Applications

### Mobile Applications
Mobile applications can be considered as very similar to Desktop application. However, for mobile applications we generally do not start an embedded Web Server but rather make use of the custom URI scheme. Many mobile computing platforms support inter-app communication via URIs by allowing apps to register a custom URL schemes like below (example for Google Maps application in iPhone)  
comgooglemaps:/  
When the browser or another app attempts to load a URI with a private-use URI scheme, the app that registered it is launched to handle the request. During the installation of the application on mobile devices, the custom URL Scheme is associated with the mobile application. We can take advantage of this in OAuth by using this custom URL as a Redirect URI that is registered with the Authorization Server. The authorization server will redirect the Authorize response directly to the mobile application and will be handled by the mobile application without needing an embedded web server. Apart from this detail, the flow of the Desktop application and the mobile application is the same.   
The section "Receiving the Authorization Response in a Native App" in the RFC link below explains this topic quite nicely.  

### OAuth 2.0 For Native App Implementation
The RFC which details the best practices for native applications (Desktop, mobile) is RFC 8252 and the link is provided below. It provides a good explanation of the best practices for implementing OAuth 2.0 with native applications. You can go through this RFC - if you plan to integrate your mobile or desktop application with OAuth 2.0  

https://www.rfc-editor.org/rfc/rfc8252.txt  

# Protecting Devices (TV, Watches, etc...)
* RFC 8628 - OAuth 2.0 Device Authorization Grant

![device](/images/device-01.png)
![device](/images/device-02.png)    

## RFC Link for OAuth 2.0 Device Authorization Grant
**OAuth 2.0 Device Authorization Grant**  
The RFC related to the Device Authorization Grant is RFC 8628 and the link is provided below. It provides a good explanation of the grant and you can go through this RFC if you plan to implement the Device Authorization grant for your project.  

https://tools.ietf.org/html/rfc8628  


# Delegated Authentication

* There should be a TRUST between the **Identity Management System** and the **Web Application** this can be achieved by using public/private keys certificates.
* The messages which are sent back end forth between the IDM and the application are digitally signed and optionally encrypted
  * When the web applciation sends a request, it signs it using the application's private key and then encrypt the message using the IDM's public key
  * The IDM, on the other hand will verify the signature using the applciation's public key and decrypt using it's own private key.
  * When the IDM sends an authentication response to the applciation, the IDM signs it using the IDM Private key and then encrypt the message using the Application's public key
  * The Web Application will verify the signature it using the IDM's Public Key and decrypt the data using it's own private key
* So in order for all of these to work correcly, the IDM and the application should really know about each other's public keys or certificates.


![delegated](/images/delegated.png)