# Security Fundamentals - LDAP and SAML

* Identity Provider
* Service Provider
* Token
* Assertion
* SAML
* Authentication
* Authorization


## Security Basics - Providers
* User, Identity
  * where are the user credentials store?
  * Is is typacally stored in a data store, like LDAP or it might even be a database.
  * An example of LDAP could be Active Directory which is used in Microsoft Data centers
* Authentication
  * Who does this IdP
  * How does it happen?
* Authorization
  * Application does this
  * Uses information from IdP

## LDAP (Lightweight Directory Access Protocol)
![Hybrid Encryption](/images/LDAP.png)

* Identity
  * Stored in LDAP database
  * Microsoft Active Directory (In most organizations, Microsoft Active Directory is the most common LDAP used)
* Authentication
  * Done by LDAP (AD)
* Authorization
  * Done by the Application
* LDAP and Application in same data center


## SAML (Security Assertion Markup Language) and Single Sign-On
What problem does SAML solve?  
* Communicating across data centers (use HTTP Redirect) 
* HTTP Redirection plays a central role in SAML and OAuth
* Avoid entering the user credentials (use SSO)
* The network user is already a part of AD
* Single Sign On (SSO)
  * Enterprise SSO


### SAML - Single Sign-On Flow
![Hybrid Encryption](/images/SAML.png)

ADFS stands for Active Directory Federation Service  

* Identity
  * Stored in LDAP database
  * Microsoft Active Directory
* Authentication
  * SAML Identity Provider does the authentication
  * ADFS
* Authorization
  * Application controls it
  * Can use LDAP groups


![Hybrid Encryption](/images/SAML02.png)

SAML Request and Response is an XML. Not that at no point - user password is exposed to application.

* SAML Metadata File
* Trust Between
  * SAML Identity Provider
  * SAML Service Provider
* SAML Response
  * Contains SAML Token
  * Token contains claims
* Federated User (It means that you have one Identity in one place inside the organizations and all the applications in other data centers can use the same Identity. Federated User, it's the same user Identity which has been used by all of these applications)
* Single Sign On
* Redirect Importance


### Enterprise Problem use cases (SAML)
1. Problem 1 - Microservices
   1. ![Hybrid Encryption](/images/problem1.png)
2. Problem 2 - Cloud Apps
   1. How does REST calls across network boundaries get secured?
   2. ![Hybrid Encryption](/images/problem2.png)
   3. Basic Authentication is usually used to make a REST API call
3. Problem 3 - Machine to Machine
   1. ![Hybrid Encryption](/images/problem3.png)


## Social Media Platform
* Social Media Sites
  * Facebook
  * Linkedln
  * Google
  * Twitter
  * GitHub
  * Yahoo
* A user usually has Multiple Identities
  * Many Identity Providers
* What if a third party application wants to access or publish to these sites on behalf of its user?
  * User/password would be a bad idea.