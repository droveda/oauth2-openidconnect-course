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


