# OpenID Connect and Microservices

![Microservice arch](/course02/images/microservice-arch.png)

## Spring Security - Resource Server
1. JwtAuthenticationToken

In the ```BugTrackerservice.createBug()``` method, you will notice that the Principal object being obtained is an object of type Jwt.  
```
    // Extract the user of the Application
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication token = ctx.getAuthentication();
    Jwt principal = (Jwt)token.getPrincipal();
```

Recall that for the abstraction of **Authentication** Interface, the concrete implementations of **Authentication** and returned Principal object would depend on the type of Authentication (SAML, OAuth Client, OAuth Resource Server etc). For Authentication using JWT, the Authentication object is **JwtAuthenticationToken** and the corresponding Principal object is **Jwt**.  

The Resource server can then access any custom claims from the Jwt object and act on it.  
```
    var claimValue = principal.getClaim("<claim name>")
```

2. JwtAuthenticationConverter

In our security configuration code, we create a bean for the Convertor class. Instead of registering a Spring Bean for the Convertor object, we can also customize it using the **oauth2ResourceServer()** method as shown below.   

```
    // setting convertor using HttpSecurity
    http.oauth2ResourceServer(oauth2 -> 
        oauth2.jwt(cfg -> 
            cfg.jwtAuthenticationConverter(jwtAuthenticationConverter())))
     
    // Remove the @Bean for this method 
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
     
        // implementation not shown
     
    }
```


## Schedulers in an Enterprise
client credentials grant.  

![scheduler with client credentials](/course02/images/scheduler-use-case.png)

* create a new client/app registration in keycloak
  * bugtracker-stats
  *  Client Authentication set to TRUE
  *  Authentication flow -> mark only "Service Accounts Roles"
  *  SAVE (It does not have any URLs)
* After creating the new client, click on the new tab called "Service accounts roles"
  * assign the role **bugtracker.user** to the client
  * Add a mapper to the ROLE, it is very simmilar what we did for the onther client
  * Disable the full scope, after that assign the scope **bugtracker.user** as well
* Check if everything is okay by evaluating the token generation
  * Select the client bugtracker-stats, access tab -> "Client Scope" -> Tab "Evaluate" -> search for "service-account-bugtracker-stats" and verify the token




## Spring Security Resource Server Documentation
A few links to the Resource Server will give you more detailed information related to the Resource Server Implementation.   

Spring Security OAuth 2 - Summary Of Resource Server features:  
https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html#oauth2-resource-server