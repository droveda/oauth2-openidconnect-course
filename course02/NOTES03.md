## Lab Keycloak
* create some **users** and set password for the users
* create **client scopes**
* create a **client** AKA app registration in order to get the clientId and clientSecret
  * After that, associate the scope create before called "bugtracker" with this client registration

![Client Registration](/course02/images/client-registration.png)


## Spring Security - Oauth Autentication
Just by including the Spring Security dependency below, the application endpoints are automatically protected and access to application endpoints will require authentication.  
```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
```

An enterprise application may use different kinds of Authentication and Spring Security handles all of them. Examples being **LDAP, SAML or OpenID Connect**.  
If you include the OAuth Client dependency as below (in addition to the above dependency), then your application is configured automatically as an OAuth or OpenID Connect Client. Because of this, we will also be including the following dependency in our Bug Tracker Application.  

```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-client</artifactId>
    </dependency>
```

If you include the OAuth Resource Server dependency below, then your application is configured automatically as a Resource Server. We will include this in our Microservice later when we create one.  
```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>
```

### application.properties
Spring Security needs to know the location of your Identity provider because it needs to communicate with the Identity Provider (SAML, OAuth etc). For OAuth, the information related to Keycloak Issuer URL, client ID, client secret etc would have to be provided to Spring Security and this is usually specified in the application.properties file.  
  
An example of such a client defined in the application.properties is shown below. Note here that I shortened the property names to fit the screen. Each property should be prefixed with **spring.security.oauth2**.    

```
spring.security.oauth2.client.registration.keycloak-oidc.provider=keycloak
spring.security.oauth2.client.registration.keycloak-oidc.client-name=Keycloak
spring.security.oauth2.client.registration.keycloak-oidc.client-id=bugtracker
spring.security.oauth2.client.registration.keycloak-oidc.client-secret=<<Enter secret>>
spring.security.oauth2.client.registration.keycloak-oidc.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.keycloak-oidc.scope=openid,email,profile
spring.security.oauth2.client.provider.keycloak.issuer-uri=http://127.0.0.1:9090/realms/oauthrealm
```
In this client property setting, keycloak-oidc is called the Client Registration ID. You will see this being used in the code to refer to a particular client.  

### SecurityFilterChain
Along with pom.xml and the application.properties file, developers can also provide a security configuration class and configure the **SecurityFilterChain** object for further customization of the OAuth process. An example of this is shown below.  
```
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) 
                                            throws Exception {
        http
           .authorizeHttpRequests(<<customize authorizations >>)
           .oauth2Login(<<customize OAuth 2 login >>)
           .logout(<<customize logout>>);
     
        return http.build();
    }
```

The above method would be located within a Spring Boot @Configuration class and it returns the SecurityFilterChain Spring Bean (Note the @Bean annotation). Various aspects of the authentication and authorization can be customized by calling configuration methods on the **HttpSecurity** parameter.  

For example, by calling ```HttpSecurity.authorizeHttpRequests(..)``` we can customize authorizations for different endpoints of the organizations.  

By including ```HttpSecurity.oauth2Login(..)```, we specify that authentication should be **OpenID Connect**. By doing this, two URLs are automatically exposed as listed below.  

### Redirect URL
Spring Boot will automatically configure a Redirect Endpoint for Identity Providers to use. The default Redirect Endpoint for login with a client with registration id REG-ID (as defined in application.properties) is. 

```
    <APPLICATION_ROOT>/login/oauth2/code/REG-ID
     
    # Example for registration id 'keycloak-oidc'
    http://localhost:8080/login/oauth2/code/keycloak-oidc
```

That's the reason, we used the above redirect URI when setting up Keycloak bugtracker client. One thing to note is that, we can setup multiple clients for OpenID Connect in the ```application.properties```. Each one will have a different Redirect Endpoint as the URL itself suggests.  


### Login URL
This URL is used by the application itself and is useful to provide a link to the user to login with a specific Identity Provider (in case there are multiple). A login URL for each such client has the following format.  

```
    <APPLICATION_ROOT>/oauth2/authorization/REG-ID
     
    # Example for registration id 'keycloak-oidc'
    http://localhost:8080/oauth2/authorization/keycloak-oidc
```

These Login URLs will be useful when we use multiple Identity Providers and will be demonstrated in future lectures.  

```HttpSecurity.oauth2Login(..)``` also provides a number of configuration options for customizing OAuth 2.0 Login endpoints.  The behavior of the endpoints can be customized as below (actual customization not shown but can easily looked up in the documentation). It is common to see such customizations in Spring Security and we will see several examples of them in the coming lectures.  

```
    http
       .oauth2Login(oauth2 -> oauth2
    	.authorizationEndpoint(authorization -> authorization
    		...
    	)
    	.redirectionEndpoint(redirection -> redirection
    		...
    	)
    	.tokenEndpoint(token -> token
    	        ...
    	)
    	.userInfoEndpoint(userInfo -> userInfo
    	        ...
    	)
        )
```

### Authentication interface
Once Spring Security does its magic and authenticates the user with LDAP, SAML or OpenID Connect - how does the application access the information of the user ? And how does the application know the privileges associated with the user ?  

Spring Security abstracts that problem by using an interface called **Authentication**. Here’s how the interface looks.  

```
    public interface Authentication extends Principal, Serializable {
     
        Collection<? extends GrantedAuthority> getAuthorities();
        Object getCredentials();
        Object getDetails();
        Object getPrincipal();
     
        // not all methods included
    }
```

**Authentication** interface is the abstraction for an “authenticated user” whereas **GrantedAuthority** interface is the abstraction for a "privilege" associated with the user. Roles, Groups or Scopes in access tokens will finally map to a **GrantedAuthority** within the Authentication interface.

As developers, the important Authentication interface methods are listed below.  
1. ```getPrincipal()``` which provides us with information of the authenticated user
2. ```getAuthorities()``` which provides us with the privileges associated with the authenticated user.

Within the Spring Boot application, developers can use these methods to perform code authorization or display user information if needed.  

The ```GrantedAuthority``` interface looks as follows.  
```
    public interface GrantedAuthority extends Serializable {
        String getAuthority();
    }
``` 

It simply holds a string and ultimately a scope, role or group associated with the user gets mapped to an authority. By default, after OpenID Connect authentication - Spring Security will set the authorities field from the claim called **scope** or **scp** in the access token. It will also prefix the authority with the keyword **SCOPE_**.  As an example, if the scope claim in the access token is "openid email profile bugtracker.user", then the authorities field would be a collection containing **SCOPE_openid, SCOPE_email, SCOPE_profile and SCOPE_bugtracker.user**. As we will see in future lectures, developers also have an option to set the authorities as part of Spring Security customization.  

Note that the **Authentication** interface implements the java.security.Principal interface which in Java Security represents an authenticated user (just like the Spring **Authentication** interface). But, you also see that getPrincipal() returns an Object. Depending on the type of authentication (SAML, OAuth2 etc), this principal class object is different and provides the complete information of the user.  

Each type of authentication would have its own implementation of the **Authentication** interface. For OAuth, the primary authentication object is called **OAuth2AuthenticationToken** (which indirectly implements the **Authentication** interface) and the principal object it returns is **OidcUser**.

See diagram below.  

![Enterprise](/course02/images/diagram001.png)

### Accessing Authentication interface in Spring Boot
Once authentication is successful, Spring Security creates and stores the **Authentication** object associated with the user in the class SecurityContextHolder. Under the hood, the Authentication object is stored in a ThreadLocal object. This makes perfect sense because in the Spring Boot application server, each user accessing the application get it's own thread and ThreadLocal's are the perfect way to store "per thread" information. In some cases, the Authentication object will also be stored in the Http Session when dealing with UI.

However all of the ThreadLocal access is masked by Spring Boot and the application can retrieve the Authentication information by simply accessing it as follows.  
```
    // extract the user of the Application
    SecurityContext ctxt = SecurityContextHolder.getContext();
    OAuth2AuthenticationToken token
       = (OAuth2AuthenticationToken) ctxt.getAuthentication();
    OidcUser principal = (OidcUser)token.getPrincipal();
```

**Note** :  In the Supplementary Section of this course, there are 3 lectures on Java Thread Locals. These lectures will help you in understanding the use of Thread Local variables.  

Because we are using OAuth here, we are casting the objects to the proper class that is expected. If you were using SAML for authentication, these casts will look different. As developers, we can then simply use the **OIDCUser**  object to get any information related to the user. Note that the **OIDCUser** object will contain the ID Token itself and so all information in the ID Token can be accessed by the application.  

A useful shortcut to get hold of the **Authentication** object is to inject it as a parameter to the Controller method which handles the HTTP requests. See the code below.  

```
    @GetMapping("/bugtracker/ui")
    public ModelAndView home(OAuth2AuthenticationToken token) {
        OidcUser principal = (OidcUser)token.getPrincipal();
     
        // code not shown
     
        return model;
    }
```

In the example above, when the home() method is invoked by Spring MVC, Spring Boot will automatically extract the **Authentication** object from the SecurityContextHolder and provide it to the home() method in the form of the token variable.  

### Authorization in Spring Security
Spring Security depends on the Authorities field in the Authentication object to authorize application endpoints. There are several ways in which authorization can be performed.  

1. Using SecurityFilterChain
When we saw the SecurityFilterChain in this article before, we saw a snippet to customize the authorizations. Lets expand on that snippet with an example.  

```
    http
      .authorizeHttpRequests(authorize ->
          authorize
            .requestMatchers("/ui/admin/**").hasAnyAuthority("ROLE_admin")
            .anyRequest().authenticated())
```

In the code above, we are specifying that any URL path which matches prefix **/ui/admin** can only be accessed by a user who has an authority of 'ROLE_admin'.  For the rest of the URLs, an authenticated user is enough.  
Spring Security will check that 'ROLE_admin' is present in the list of authorities for the user in the **Authentication** object before allowing access to the URLs.

Spring Security treats roles as slightly special. If the Authority has a prefix of **ROLE_**, then it considers the Authority as a Role. For example, an authority **ROLE_admin** is same as Role **admin**. So - above example can be re-written as below [note the use of **hasAnyRole(..)** instead of **hasAnyAuthority(..)** ]

```
    http
      .authorizeHttpRequests(authorize ->
          authorize
            .requestMatchers("/ui/admin/**").hasAnyRole("admin")
            .anyRequest().authenticated())
```

2. Using Annotations
You can also specify authorizations in Spring Beans by annotating them with **@Secured, @RolesAllowed, @PreAuthorize and @PostAuthorize**. Since it's not important to our discussion of OAuth and OpenID Connect, I am not going to elaborate on them. If you are interested, you can very easily look up a YouTube video related to the topic.  

3. In the code
If the application wants to perform any authorizations in the code itself, it can extract the authorities as follows and can do complex authorizations.  
```
    // Get the authorities associated with the user
    Collection<GrantedAuthority> list = token.getAuthorities();
     
    // execute some action here only if 'list' contains an Authority
```

### Final Tought
Much of what we have discussed in this article will be applied in the future lectures on the BugTracker project and you will get a better idea of a real world implementation.  


```
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaft-extras-springsecurity6</artifactId>
</dependency>
```