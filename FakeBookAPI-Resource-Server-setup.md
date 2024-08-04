# FakeBookAPI Resource Server setup

## Instructions to set up FakeBook API Resource Server with Okta
* Download the **okta-fakebookapi-3.3.zip** (Spring Boot 3.3.x) file from lecture "Custom Resource Server with Spring Boot". There is also a okta-fakebookapi-2.7.zip which uses Spring Boot 2.7.x but I recommend that you use Spring Boot 3.3
* Unzip the zip file in your local directory 
* Change to [local directory]/okta-fakebookapi 
* Open the file how-to-use.txt for instructions on project setup  and follow the instructions to start the FakeBook API Resource Server
  * Note : Make sure JDK 21 or 17 is installed
  * Note : Depending on which zip you download, the Resource Server will use Spring Boot 2.7 or 3.3
* You can then use Postman to send messages to the Resource Server as mentioned in the Deep Dive Sessions for 'Client Credentials Grant Type' and 'Resource Owner Password Grant Type'. 

#### Follow the instructions below to create the Client ID and Client Secret. 
## Create Client with Grant Type Client Credentials
Note : This is explained in Deep Dive session for Client Credentials Grant  
To create an application with Client Credentials grant type, do the following:  
* From Applications page, click on "Create App Integration" 
* Select sign-on method as 'API Services'
* Enter an App Integration Name 'FakebookCron' 
* Important : Uncheck the checkbox for field **Proof Of Possession** (otherwise call to FakebookAPI will fail with error - Invalid DPOP Proof)
  * UI Change - Okta has added the option to select **Public KeyPrivate Key** as part of the Client Authentication. With this option, Client Authentication can be done using a **Client Secret** or a **JWT Bearer**. The Public/Private Key is used to support JWT Bearer token. However, in this course we will be using Client Secret throughout.
* Click Save


## Create Client with Grant Type Resource Owner Password 
Note : This is explained in Deep Dive session for Password Grant  
To create an application with Resource Owner Password grant type, do the following:  
* From Applications page, click on "Create App Integration" 
* Select sign-on method as 'OIDC - OpenID Connect'
* Select Application Type as 'Native Application' 
* Enter information on this page as in the Deep Dive for Resource Owner Password grant 
  * Name = FakebookClient
  * Grant types allowed = Authorization Code, Refresh Token, Resource Owner Password
* Select Controlled access and 'Allow everyone in your organization to access'
* Click Save