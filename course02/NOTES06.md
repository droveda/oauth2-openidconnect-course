## Keycloack Add Roles Claim
LAB:  
* Go to clients and select the bugtracker client
  * select the tab called "Client Scopes"
    * click on "bugtracker-dedicated"
    * under tab "Mappers" click on "Configure a new mapper", select the type "User Client Role"
  * Fill the fields following the image above

![Client Roles Claim](/course02/images/roles-001.png)

With this change in place, when Keycloack generates the access token and ID token, it will also create the role as a claim, and it will contain the user roles for bugtracker.  
Do the necessary changes in the spring-boot app.  

* Remove the scopes from the client
  * go to the bugtracker client, tab "Client scopes"
  * remove **bugtracker.admin** scope and **bugtracker.user** scope


# Handling Multiple Providers
![Multiple Providers](/course02/images/multiple-provider-001.png)

## Client Registration in Gitlab
1. create a account in GitLab if you do not have one
2. log into gitlab, go to the user settings/preferences
3. click on Applications
   1. name = bugtracker
   2. redirect URL = http://localhost:8080/login/oauth2/code/gitlab-oidc
   3. for scopes select = email, profile and openid
   4. Click on SAVE APPLCIATION
   5. Take note of the **application id** and **secret**
4. https://docs.gitlab.com/integration/openid_connect_provider/
5. https://gitlab.com/.well-known/openid-configuration

After that do the necessary changes in the spring-boot application.  
application.yaml and Oauth2LoginSecurityConfig.java file.  

## Architectures Downsides for Multiple Identity Proviers
* Need to handle multiple Identity Providers
* Need to translate claims in the code
* Users are not consolidated into one place
* Architecture Problem
  * Single Page Applications
  * Microservice problem (Will have different access tokens gitlab and keycloak)

Using Identity Brokers can solve the issues.   