# Task - Add pure OAuth functionality from JAUBS application

In this assignment, you will provide Administrators the ability to import a JSON file stored in GitLab. You will have to integrate GitLab using OAuth and NOT OpenID connect. This should make the difference between OAuth and OpenID Connect extremely clear.  

## Instructions
To complete this assignment, you will have to make changes to Spring Boot code and GitLab Client. Assume that code would be retrieving a file called **jaubs.json** in user project **jaubs**.  

* GitLab Client will have to redirect the response to the correct URI
* Implement the functionality for retrieving the JSON from GitLab project and import it.
* You will have to research on what GitLab APIs are available for this.


Hint : Two GitLab APIs will have to be used.  
https://gitlab.com/api/v4/projects?owned=true&search=jaubs (Retrieve the project is of project jaubs).  
https://gitlab.com/api/v4/projects/<proj-id>/repository/files/jaubs.json/raw?ref=main.  

## Task Questions
1. Were you able to complete the Assignment successfully ?