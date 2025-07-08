# Keycloack and Identity Brokers

![brokers](/course02/images/brokkers-001.png)

![brokers](/course02/images/brokkers-002.png)

![brokers](/course02/images/brokkers-003.png)

![brokers](/course02/images/flow-001.png)

## Setting up Gitlab Identity Provider in Keycloack
LAB:  
* left side menu -> Identity providers
  * choose Gitlab
  * ![gitlab](/course02/images/gitlab-001.png)
  * inform the clientid + client secret from gitlab (pick it from the spring-boot properties file)
  * redirect URI -> http://localhost:9090/realms/oauthrealm/broker/gitlab/endpoint
  * Click on ADD
  * Sync Mode change to "Force"
  * Click on SAVE
* Log in to gitlab
  * preferences -> applications
    * add another recirect URL to bugtracker - http://127.0.0.1:9090/realms/oauthrealm/broker/gitlab/endpoint
      * **WATCH OUT** for the localhost/127.0.0.1 issue, it only works with **127.0.0.1**
    * check the **read_user** scope
    * click on SAVE

 ## Bugtracker identity Broker Integration
 * remove or comment out the gitlab properties from the **application.properties** file in the spring-boot project
 * run the sb application and log in with gitlab
   * go to the users page on keycloak and you will see that a new user was inserted, the gitlab user
 * Adding a mapper to the gitlab config in keycloak
   * left side menu -> identity providers -> select the gitlab
   * select tab mappers, click on Add new Mapper
     * name = gitlab-role-mapper
     * mapper type = Hardcoded Role
     * Select role -> select the role **bugtracker.user**
     * click Assign
     * click SAVE

### Assignment add Google as a identity provider as well
* http://127.0.0.1:9090/realms/jaubs/broker/google/endpoint

STEPS to Complete the Assignment.  
```
==============
Keycloak setup
==============

- Log into Keycloak Administration

GitLab Setup
============
- Click on Identity Providers -> GitLab
    - Redirect URI = http://127.0.0.1:9090/realms/jaubsrealm/broker/gitlab/endpoint
    - Client ID = <<Copy from GitLab Client>>
    - Client Secret = <<Copy from GitLab Client>>
    - Sync mode = Force (Why is this Important ?)

- Click on Mapper tab
    - Click Add Mapper
    - Name = set-role-mapper
    - Mapper type = Hardcoded Role
    - Role = jaubs-user

Google Setup
============
- Click on Identity Providers -> Google
    - Redirect URI = http://127.0.0.1:9090/realms/jaubsrealm/broker/google/endpoint
    - Client ID = <<Copy from Google Client>>
    - Client Secret = <<Copy from Google Client>>
    - Sync mode = Force

- Click on Mapper tab
    - Click Add Mapper
    - Name = set-role-mapper
    - Mapper type = Hardcoded Role
    - Role = jaubs-admin

DON'T FORGET -> to store the redirect URI in the google console https://console.cloud.google.com/ for the app jaubs
```

## SAML Overview - Security Assertion Markup Language

![SAML](/course02/images/SAML-001.png)

![SAML](/course02/images/SAML-002.png)

![SAML](/course02/images/SAML-003.png)

![SAML](/course02/images/SAML-004.png)

![SAML](/course02/images/SAML-005.png)

![SAML](/course02/images/SAML-006.png)

![SAML](/course02/images/SAML-007.png)

![SAML](/course02/images/SAML-008.png)


```
Creating an Okta Developer Account

If you don't already have an Okta Developer account, please follow the steps below to get a free Developer account.

    Enter URL  https://developer.okta.com/signup/ to see the Okta signup screen

    The signup screen shows 3 options. Go to the bottom of the signup page.

    Important : Do NOT click on Try Customer Identity Cloud or Try Workforce Identity Cloud

    Please click the button "Sign up free". See Red Arrow pointing to button to be clicked.

    Enter your work information and click Sign up

    Alternatively, you can use your personal Google or Github account to sign up by clicking on the corresponding button.

    Login to Okta Dashboard using your user
```

![OKTA](/course02/images/okta-001.png)