## MS Learn some useful links to use
* https://learn.microsoft.com/en-us/entra/fundamentals/identity-fundamental-concepts
* https://learn.microsoft.com/en-us/entra/identity-platform/security-tokens
* https://learn.microsoft.com/en-us/entra/identity-platform/certificate-credentials
* https://learn.microsoft.com/en-us/entra/identity-platform/v2-protocols-oidc


1. Get the Access Token
```  
// Line breaks are for legibility only. This is the first Request in order to GET the Access TOKEN
// MS documentation link: https://learn.microsoft.com/en-us/graph/auth-v2-service?tabs=http
POST https://login.microsoftonline.com/{tenant}/oauth2/v2.0/token HTTP/1.1
Host: login.microsoftonline.com
Content-Type: application/x-www-form-urlencoded

client_id={client-id-for-the-app-registration}
&scope=https://graph.microsoft.com/.default
&client_secret=qWgdYA....L1qKv5bPX
&grant_type=client_credentials
```

## Useful Microsoft Oauth 2.0 Links:  
https://login.microsoftonline.com/{tenant}/oauth2/v2.0/token  
https://login.microsoftonline.com/{tenant}/.well-known/openid-configuration  
https://login.microsoftonline.com/{tenant}/v2.0/.well-known/openid-configuration  (in my tests I used this one)  
  

### LAB Web App Authentication - Application Registration
https://learn.microsoft.com/en-us/entra/identity-platform/scenario-web-app-sign-user-app-registration?tabs=java  

* Create a Java Web Rest API
* Go to Microsoft Entra ID
  * Left side menu -> Click on App Registrations
    * Click: +New Registration
      * Name: webapp, click REGISTER
  * Left side menu -> Manage -> Click on "Authentication"
    * Click on "+Add a Platform"
      * Select "WEB"
        * Inform the redirect URIs: http://localhost:8080
        * Inform the logout URI
        * Click checkbox to enable Id Tokens -> [x] Id Tokens (used for implicity and hybrid flows)
        * Click on "Configure"
  * Left side menu, click on Manage -> Client & Secrets
    * In the Client secrets section, select New client secret, and then:
      * Enter a key description.
      * Select the key duration In 1 year.
      * Select Add.
      * When the key value appears, copy it for later. This value won't be displayed again or be retrievable by any other means. 
  * Left side menu, clicn on Manage -> API Permissions
    * Click +Add a permission
      * Choose "Azure Storage", select **User Impersonation** checkbox and click on **Add Permissions**

## Lab Using the authorization code flow, using client_secret_post auth method
prerequisites:  
* Start a local web server: **python3 -m http.server 8080**  
* Have a **Storage Account**, with a blob storage and a simple text file inside a container.  
* Have an **app registration** following the instructions above  
* Give to the user that you want to impersionate **Storage Blob Data Reader** role access to the **Storage Account**  


---
1. Get the Auth code
```
    GET
    https://login.microsoftonline.com/{tenant}/oauth2/v2.0/authorize
    ?response_type=code
    &client_id={my_client_id}
    &scope=openid%20profile%20email%20https://storage.azure.com/user_impersonation
    &state=state123
    &redirect_uri=http://localhost:8080
    &access_type=offline

    //the complete URL
    https://login.microsoftonline.com/{tenant}/oauth2/v2.0/authorize?response_type=code&client_id={client_id}&scope=openid%20profile%20email%20https://storage.azure.com/user_impersonation&state=state123&redirect_uri=http://localhost:8080&access_type=offline  
```

2. Send the request to exchange the **auth code** to the **access token**
```
  POST https://login.microsoftonline.com/{tenant}/oauth2/v2.0/token

  curl --location 'https://login.microsoftonline.com/{tenant}/oauth2/v2.0/token' \
        --header 'Content-Type: application/x-www-form-urlencoded' \
        --data-urlencode 'grant_type=authorization_code' \
        --data-urlencode 'client_id=<my-client-id>' \
        --data-urlencode 'client_secret=<my-client-secret>' \
        --data-urlencode 'code=<my-auth-code>' \
        --data-urlencode 'redirect_uri=http://localhost:8080'
```

3. Access the blob file using the **access token**
```
  GET https://rovedastorage.blob.core.windows.net/data/NOTES.md

  curl --location 'https://rovedastorage.blob.core.windows.net/data/NOTES.md' \
    --header 'Authorization: Bearer <the-access-token>' \
    --header 'x-ms-version: 2024-05-04'
```

## Lab Using the authorization code flow, using private_key_jwt auth method
prerequisites:  
* Start a local web server: **python3 -m http.server 8080**  
* Have a **Storage Account**, with a blob storage and a simple text file inside a container.  
* Have an **app registration** following the instructions above  
* Give to the user that you want to impersionate **Storage Blob Data Reader** role access to the **Storage Account**  

TODO  



## Lab Using the client_credentials flow, using client_secret_post auth method
prerequisites:  
* Start a local web server: **python3 -m http.server 8080**  
* Have a **Storage Account**, with a blob storage and a simple text file inside a container.  
* Have an **app registration** following the instructions above  
* Give to the user that you want to impersionate **Storage Blob Data Reader** role access to the **Storage Account**  

TODO  