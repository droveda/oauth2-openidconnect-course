# Keycloak - Oauth and OpenID Connect Together

In this section we will be using key-cloack to user authentication/login in using openId Connect and Oauth and EXCLUSIVE OAuth to access gitlab resource server to read projects/repositories for a user.  

![openid connect and oauth 2.0](/course02/images/openidconnect-and-oauth.png)

## LAB
1. Disable gitlab on keycloack Identity providers and hide it from the login form.
2. add the following to the application.properties file:
```
spring:
  security:
    oauth2:
      client:
        registration:
          gitlab-oauth:
            provider: gitlab
            client-name: GitLab
            client-id: ${GITLAB_CLIENT_ID}
            client-secret: ${GITLAB_CLIENT_SECRET}
            authorization-grant-type: authorization_code
            scope: read_user,api,read_api
        provider:
          gitlab:
            issuer-uri: https://gitlab.com
```
3. change security configuration -> ```http.oauth2Client(Customizer.withDefaults())```
4. Enable the scopes on gitlab -> (api, read_api)
5. Add the redirect URI -> ```http://localhost:8080/authorize/oauth2/code/gitlab-oauth```

## Spring Security - oauth2Login versus oauth2Client
**HttpSecurity.oauth2Client(..)** should be used when the application needs exclusive OAuth capability to access a Resource Server. By calling the method in **SecurityFilterChain**, the relevant filters are registered in the Spring Security Filter chain and it exposes the redirect **"authorize"** URL. This redirect URL needs to be registered with the Identity Provider so that the Identity Provider can call back on it with an Authorization code.  

The format of the redirect authorize URL is as follows.  
```
    <APPLICATION_ROOT>/authorize/oauth2/code/REG-ID
     
    # Example for registration id 'gitlab-oauth'
    http://localhost:8080/authorize/oauth2/code/gitlab-oauth
```


**HttpSecurity.oauth2Login(..)** should be used when the application needs to login to the application using OpenID Connect and optionally want to call microservice using an access token. By calling the method in **SecurityFilterChain**, the relevant filters are registered in the Spring Security Filter chain and it exposes the redirect **"login"** URL. This login URL needs to be registered with the Identity Provider so that the Identity Provider can call back on it with an Authorization code.  

The format of the redirect login URL is as follows.  
```
    <APPLICATION_ROOT>/login/oauth2/code/REG-ID
     
    # Example for registration id 'keycloak-oidc'
    http://localhost:8080/login/oauth2/code/keycloak-oidc
```


### Spring Security Documentation Link
For further clarification, the link below talk about setting up Spring Security for "login", as a "client" (pure OAuth) or both.  

Spring Security OAuth Client and Login - https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html#oauth2-client. 

