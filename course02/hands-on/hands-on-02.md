# Protecting JAUBS using only Roles

In this assignment, you will only use Keycloak roles to protect your application resources. You will add a new property in the access token called 'roles', which will be used by your application for authorization.  

## Prerequisite
* The completed assignment 1 would be the starting point for this Assignment
* Feel free to look at the BugTracker code for any ideas

## Instructions
* For client **jaubs**, create a new mapper and add property 'roles' 
* In Spring Boot application, for **OAuth2LoginSecurityConfig.java**
  * Change the authorization to use roles instead of scopes
  * Update accordingly to set Authorities from roles instead of scopes (Review lectures if needed)

## Assignment Questions
1. Were you able to complete the assignment successfully ? 
Yes.  


## Solution
```
==============
KeyCloak Setup 
==============

- Lets Add roles attribute in access token
    - From client jaubs, click on tab = Client Scopes
    - click jaubs-dedicated
    - Configure New Mapper of Type = User Client Role
    - Name = jaubs-role-mapper
    - Client Role Prefix = ROLE_
    - Token Claim Name = roles 
    - Add to Access Token = ON


=================
Spring Boot Setup
=================

- In the Spring Security config java file, add the following

authorize
    .requestMatchers("/login","/main.html", "/css/**", 
                           "/js/**", "/images/**").permitAll()
    .requestMatchers("/jaubs/ui").authenticated()
    .requestMatchers("/jaubs/ui/admin/**").hasAnyRole("jaubs-admin")
    .anyRequest().hasAnyRole("aubs-admin", "jaubs-user"))

- In application.properties file, simply comment out the GitLab and Google
  properties. We do not need them for this assignment.
```