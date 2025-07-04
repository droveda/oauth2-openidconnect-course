# Course Project
* JAUBS - Just Another Used Bookstore
* User Types
  * Regular User, Admin User
* JAUBS Users - Keycloack Identity Managemtn System
* Social Users - Gitlab, Google
* Partners - Okta & SAML (Wonder Bookstore)
* Pure OAuth
  * Use GitLab to pull data


## Assignment 1
In this assignment, you will protect JAUBS (Web Application) using OpenID Connect. You will create the Keycloak scopes, roles, clients and setup Spring Boot security for a JAUBS that you will download.  

### Prerequisites
* Java 22 installed (if not then change maven accordingly)
* Keycloak 22.0.4 or later Installed.
 
### Instructions
Attached as a downloadable resource is a **jaubs-initial.zip** file which contains an initial version of the JAUBS Web site. It has incomplete configuration for OAuth - especially the **application.properties** and the **OAuth2LoginSecurityConfig.java*. These are the two files you should concentrate on - to set the JAUBS website up with OAuth configuration. You can download and unzip this ZIP file and import the Spring Boot project into your favorite IDE like IntelliJ IDEA or Eclipse - and then start making changes.

The goal of this assignment is for the application to use scopes to set up the authorization for the project. To this end, you need to make the following broad changes.  
1. Create the necessary Keycloak Configuration using Administration tool
2. Change Spring Boot code for correct authorization for admins and regular users

Follow the steps below for Keycloak Configuration.  
1. Create a new **realm**
2. Create two **scopes** - one for admin and one for user **(jaubs-admin, jaubs-user)** 
3. Create a client called **jaubs**
4. Create two users - **userdoe** and **admindoe** which represents a user and admin 
5. Create roles for jaubs **(jaubs-admin, jaubs-user)**
6. Make the necessary associations between the entities (i.e. Associate users with roles, scopes with roles etc). Review lectures if necessary. 
7. Note down the created **ClientID** and **Client Secret**. 


Follow the steps below for Spring Boot setup.  
* Add necessary Keycloak OpenID configuration in **application.properties**
* In **OAuth2LoginSecurityConfig.java**, you see that authorization is given to all "authenticated" users. This is obviously wrong.
* Change the above to do the following
  * main.html and related Javascript, css and Image files should be accessed by "anyone"
  * **/jaubs/ui** should be accessed by all "authenticated" users
  * Admin URLS should be accessible to only "admin" users (users who are assigned role **jaubs-admin**)
  * Any other request should be accessible to both "admin" and "regular users"


### Testing
* Access the JAUBS application using URL http://localhost:8080/main.html
* Login using user **admindoe** and confirm that you can complete Administration Functions
* Login with user **userdoe** and confirm that you can buy used book ONLY ! 

### Asignment Questions
Were you able to complete the assignment successfully ?  

jaubs-initial.zip.  