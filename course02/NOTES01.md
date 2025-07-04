# Course 02 - Advanced OpenID Connect with Keycloak and Spring Security


## Keycloak Architecture
Identity and Access Management System (IAMS).  
Keycloak is basically nothing but an identity and access managemtn system and it is completely open source.  
Oauth2, OpenID Connect, SAML.  
Keycloack can be used as a single sign on server.  
Can act as a SAML identity provider, or it can even act as an OAuth authorization server.  
It can act like an identity broker in the middle between your application and another identity provider.  

![Hybrid Encryption](/course02/images/keycloack-arch.png)

### Keycloack Features
* Single Sign-On
* Identity Brokering
* Social Login - Facebook, Google, etc...
* Centralized Administration Console
* Satandard Protocols - SAML 2.0, OAuth 2.0, OpenID Connect
* LDAP and Active Directory Integration
* MFA
* Fine grained Authorization Services

### Keycloack Installation and Setup
* https://www.keycloak.org/downloads
* https://github.com/keycloak/keycloak/releases/tag/22.0.5
* Download the zip or tar.gz file and extract it to your home directory
* Include the path to the keycloack in your .bash_profile or .zshrc file. Example:
  * export KEYCLOACK_HOME=/Users/dieguesroveda/keycloak-22.0.5
  * PATH="$KEYCLOACK_HOME/bin:$PATH:/Users/dieguesroveda/.nexustools"
  * export PATH
* Change the default keycloak port to 9090
  * file -> /Users/dieguesroveda/keycloak-22.0.5/keycloak.conf
  * add the following property at the end of the file ```http-port=9090```
* To start the keycloak server -> ```kc.sh start-dev```
* In order to access go to -> ```http://localhost:9090/```
  * Create an Admin
  * Login as an admin in the admin console


### Postgres Instalation and Setup
* http://postgresql.org/
  * Go to downloads and select your SO version
  * Download the installer
  * Instalation Directory /Library/PostgreSQL/16
  * Data directory /Library/PostgreSQL/16/data
  * postgres/admin 
  * port 5432
* use dbeaver or pgadmin4 to access
* create a new database called keycloakdb


### Keycloak Postgres and setup
* open the file -> /Users/dieguesroveda/keycloak-22.0.5/keycloak.conf
  * uncomment and fill the information about the postgres connection properly

```
# The database vendor.
db=postgres

# The username of the database user.
db-username=postgres

# The password of the database user.
db-password=admin

# The full database JDBC URL. If not provided, a default URL is set based on the selected database vendor.
db-url=jdbc:postgresql://localhost:5432/keycloakdb
```

* start the server -> kc.sh start-dev
* it should create the tables in the postgres db


```
what is a realm in Keycloak?
the users, groups, apps, etc... are organized into realms
your enterprise could have a single realm with all users,s apps and groups or you can have one realm for department
It is basically segregation and can be managed by diferent admins
```

* Create the realm called: oauthrealm



### Notes on Keycloak and Postgres setup

* You can download the latest Keycloak version (last tested with version 24.0.3)

* We will be having our applications running at ports 8080 and 8081, so it's important that we run Keycloak server at port 9090. Moreover, make sure the the hostname is set to 127.0.0.1 instead of localhost. This is just to be safe, so that there are no session conflicts between the applications we run locally and Keycloak (since they run on the machine). It's a good security practice because they share the same host machine.

```
        # Hostname for the Keycloak server.
        hostname=127.0.0.1
         
        # Port at which Keycloak server runs
        http-port=9090
```


* After installing Postgres, it will automatically be running on port **5432** and we don't need to separately run it. Update the keycloak.conf to set the database properties as below. Here I am assuming, you have created the user postgres with password postgres and a database called keycloakdb

```
        # The database vendor.
        db=postgres
         
        # The username of the database user.
        db-username=postgres
         
        # The password of the database user.
        db-password=postgres
         
        # The full database JDBC URL. 
        db-url=jdbc:postgresql://localhost:5432/keycloakdb
```


* On Windows, you can run the keycloak server using the following command.
```.\kc.bat start-dev```


### Server Administration Guide
For your reference, the Server Administration Guide for Keycloak details how to use the Keycloak Administration Console for various setups. It is located at. 
Keycloak Server Administration https://www.keycloak.org/docs/latest/server_admin/index.html    
