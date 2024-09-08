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
  * Left side menu, click on Manage -> **Certificates & Secrets**
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
    &scope=openid%20profile%20email%20https://storage.azure.com/user_impersonation%20offline_access
    &state=state123
    &redirect_uri=http://localhost:8080
    &access_type=offline

    //the complete URL
    https://login.microsoftonline.com/{tenant}/oauth2/v2.0/authorize?response_type=code&client_id={client_id}&scope=openid%20profile%20email%20https://storage.azure.com/user_impersonation&state=state123&redirect_uri=http://localhost:8080&access_type=offline  
```

2. Send the request to exchange the **auth code** to the **access token**
```
  // This one worked better field "scope" added with the client_id, solved the signature verification on jwt.io 
  // The solution found on "https://stackoverflow.com/questions/74900335/invalid-signature-azure-access-token-jwt-io"\

  POST https://login.microsoftonline.com/{tenant}/oauth2/v2.0/token
  curl --location 'https://login.microsoftonline.com/{tenant}/oauth2/v2.0/token' \
        --header 'Content-Type: application/x-www-form-urlencoded' \
        --data-urlencode 'grant_type=authorization_code' \
        --data-urlencode 'client_id={my-client-id}' \
        --data-urlencode 'client_secret={my-client-secret}' \
        --data-urlencode 'code={my-auth-code}' \
        --data-urlencode 'redirect_uri=http://localhost:8080' \
        --data-urlencode 'scope={my-client-id}/.default'
```

3. Access the blob file using the **access token**
```
  GET https://rovedastorage.blob.core.windows.net/data/NOTES.md

  curl --location 'https://rovedastorage.blob.core.windows.net/data/NOTES.md' \
    --header 'Authorization: Bearer <the-access-token>' \
    --header 'x-ms-version: 2024-05-04'
```

4. Getting a new Token using the refresh token
```
POST https://login.microsoftonline.com/{tenant}/oauth2/v2.0/token

curl --location 'https://login.microsoftonline.com/{tenant}/oauth2/v2.0/token' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode 'grant_type=refresh_token' \
  --data-urlencode 'client_id=<my-client-id>' \
  --data-urlencode 'client_secret=<my-client-secret>' \
  --data-urlencode 'refresh_token=<the-refresh-token>'
  --data-urlencode 'scope=https://graph.microsoft.com/.default'
```

5. Get User info using the **access token**
```
POST https://login.microsoftonline.com/{tenant}/oauth2/v2.0/token
curl --location 'https://login.microsoftonline.com/{tenant}/oauth2/v2.0/token' \
      --header 'Content-Type: application/x-www-form-urlencoded' \
      --data-urlencode 'grant_type=authorization_code' \
      --data-urlencode 'client_id={my-client-id}' \
      --data-urlencode 'client_secret={my-client-secret}' \
      --data-urlencode 'code={my-auth-code}' \
      --data-urlencode 'redirect_uri=http://localhost:8080' \
      --data-urlencode 'scope=https://graph.microsoft.com/.default'


https://graph.microsoft.com/.default

curl --location 'https://graph.microsoft.com/oidc/userinfo' \
--header 'Authorization: Bearer <the-access-token>' \
--header 'x-ms-version: 2024-05-04'
```



## Lab Using the authorization code flow, using private_key_jwt auth method
prerequisites:  
* Start a local web server: **python3 -m http.server 8080**  
* Have a **Storage Account**, with a blob storage and a simple text file inside a container.  
* Have an **app registration** following the instructions above  
* Give to the user that you want to impersionate **Storage Blob Data Reader** role access to the **Storage Account**  

1. https://learn.microsoft.com/en-us/entra/identity-platform/certificate-credentials  
2. https://learn.microsoft.com/en-us/answers/questions/1388776/how-to-get-access-token-using-certificate-based-au (this link helped a lot)
3. generate a private key and a certificate:
   1. openssl req -x509 -nodes -sha256 -days 3650 -newkey rsa:2048 -keyout private.key -out certificate.crt


```
# Using client_credentials grant flow
curl --location 'https://login.microsoftonline.com/{tenant_id}/oauth2/v2.0/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'scope={client_id}/.default' \
--data-urlencode 'client_assertion_type=urn:ietf:params:oauth:client-assertion-type:jwt-bearer' \
--data-urlencode 'client_assertion={my_signed_jwt_content}'
```

```
# Python Script to generate the KID
import binascii
import base64

thumbprint_hex = 'paste-the-certificate-thumbprint'
thumbprint_binary = binascii.unhexlify(thumbprint_hex)

x5t_compliant = base64.b64encode(thumbprint_binary).decode('utf-8')
print(x5t_compliant)

```

```
# Using authorization_code grant flow
curl --location 'https://login.microsoftonline.com/ba9f55a7-2eb5-49fe-bd58-f51d3a68eb1b/oauth2/v2.0/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'scope={client_id}/.default' \
--data-urlencode 'client_assertion_type=urn:ietf:params:oauth:client-assertion-type:jwt-bearer' \
--data-urlencode 'client_assertion={my_signed_jwt_content}' \
--data-urlencode 'redirect_uri=http://localhost:8080' \
--data-urlencode 'code={the-auth-code}'
```




## Lab Using the client_credentials flow, using client_secret_post auth method
prerequisites:  
* Start a local web server: **python3 -m http.server 8080**  
* Have a **Storage Account**, with a blob storage and a simple text file inside a container.  
* Have an **app registration** following the instructions above  
* Give to the user that you want to impersionate **Storage Blob Data Reader** role access to the **Storage Account**  

TODO  