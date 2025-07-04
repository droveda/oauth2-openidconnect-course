## Spring Security - Authorized Client

We know that after the User authentication is successful, an Authentication object representing the user is stored in the **SecurityContextHolder**. If the application is using OpenID Connect, the ID token associated with the user can be retrieved from the Authentication object.  

### OAuth2AuthorizedClient
In Spring Boot, how do we get hold of the access token and the refresh token? After all, at a later point of time we would need the access token to call any microservices of the application (if we use Microservice based design).  

The answer is : **OAuth2AuthorizedClient**. This object is created for each client for which the end user has given authorization. When the user logs into an application, the user gives authorization to the client as well and so there will be an **OAuth2AuthorizedClient** associated with the logged in user.  

### Retrieving default OAuth2AuthorizedClient
Getting access to the default **OAuth2AuthorizedClient** object is straightforward. This represents the **OAuth2AuthorizedClient** object of the client used for OpenID Connect (basically the client used for logging in the user). It can be injected as a parameter to the controller methods as shown in the code snippet below. From the authorized client, we can get the access token, the refresh token, the client registration (as defined in the application.properties) and the name of the user who gave the authorization. 

```
    @GetMapping("/bugtracker/ui")
    public ModelAndView home(
        @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {
     
        OAuth2AccessToken accessToken = client.getAccessToken();
        OAuth2RefreshToken refreshToken = client.getRefreshToken();
        ClientRegistration regstrn = client.getClientRegistration();
        String name = client.getPrincipalName();
     
        // Make use of the access token, refresh token etc
     
        return model;
    }
```

The important point to remember here is that for a logged in user, there can be only one **Authentication** object. However, there can be multiple **OAuth2AuthorizedClient** objects. That's because the application can integrate with multiple Authorization Servers in an application and in that case - there can be one OAuth2AuthorizedClient for each client defined in the application.properties (at the same time). We will talk about this in great detail in future lectures.  


### Retrieving OAuth2AuthorizedClient for a client

Assuming the user logged in using Keycloak Identity Provider, OAuth2AuthorizedClient for a client with registration id 'gitlab' can be retrieved as follows. Note the keyword 'gitlab' used as part of the annotation.  

```
    @GetMapping("/bugtracker/ui")
    public ModelAndView home(
        @RegisteredOAuth2AuthorizedClient("gitlab") OAuth2AuthorizedClient cli) {
     
        OAuth2AccessToken accessToken = cli.getAccessToken();
        OAuth2RefreshToken refreshToken = cli.getRefreshToken();
        ClientRegistration regstrn = cli.getClientRegistration();
        String name = cli.getPrincipalName();
     
        // Make use of the access token, refresh token etc
     
        return model;
    }
```

Also if the user has not given authorization for access to GitLab, Spring Security will initiate the authorization code flow with GitLab Authorization Server, get the authorization from the user, create the **OAuth2AuthorizedClient** and then call the home(..) method.  

We will revisit this when we talk about using OAuth and OpenID Connect together with multiple Identity providers. For now, you can imagine there is only a single **OAuth2AuthorizedClient** object for the client defined in the application.properties.  


## Some tests using curl and or postman

### Testing grant-type - authorization_code
* start a server: ```python3 -m http.server 9092```
* well known: ```http://localhost:9090/realms/oauthrealm/.well-known/openid-configuration```
* auth URL: ```http://localhost:9090/realms/oauthrealm/protocol/openid-connect/auth```

```
# get the auth code
http://127.0.0.1:9090/realms/oauthrealm/protocol/openid-connect/auth?response_type=code&client_id=bugtracker&scope=openid%20profile%20email%20bugtracker&state=something&redirect_uri=http://localhost:9092&nonce=_Kn3kG92NZVzQn7MCDaeF3Sifh5wYnNNQGiYnCRvsrs
```

```
# get the token
curl --location 'http://localhost:9090/realms/oauthrealm/protocol/openid-connect/token' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --data-urlencode 'grant_type=authorization_code' \
    --data-urlencode 'client_id=<my-client-id>' \
    --data-urlencode 'client_secret=<my-client-secret>' \
    --data-urlencode 'code=<my-auth-code>' \
    --data-urlencode 'redirect_uri=http://localhost:9092'
```
