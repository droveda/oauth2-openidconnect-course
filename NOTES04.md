# Google Authorization Server

## Client Registration

### LAB
* https://developers.google.com/photos/library/guides/get-started
* Login to the API console and Create a new Project. Example: "My Google Photos Project"
* Search for Google Photos API, Select it and click the Blue Button "ENABLE"
* Click on the Sandwich button, APIs & Services -> Click on CREDENTIALS
  * Click on CREATE CREDENTIALS, Select type = OAuth Cliend ID
    * Click on "Configure Conset Screen"
      * User Type = External, click on "CREATE"
        * App Name = "My Google Photos Reader"
        * Email Support = My Email
        * Click on "Save and Continue"
        * Do not need to define any scopes at this moment.
        * Click on "Save and Continue"
        * Test Users
          * Click + Add Users
            * inform my email, click ADD
        * Click on SAVE AND Continue, Summary
  * Go Back to APIs & Services -> Click on CREDENTIALS
    * Click on CREATE CREDENTIALS, Select type = OAuth Cliend ID
      * Application type, select "Web Application"
        * name = My Google Photos Reader
        * Authorized redirect URIs
          * click on ADD URI
        * Click ON CREATE
        * You will get a ClientID and a Client Secret, download it and keep it secure


### Finding Google Endpoints
* https://accounts.google.com/.well-known/openid-configuration
* Get the Google Photos Resource API endpoint: https://photoslibrary.googleapis.com
* Will be using postman to execute the http requests


### Deev Dive : Authorization Code Grant Type
https://developers.google.com/identity/protocols/oauth2  
The site above you can use it in order to search the scopes for the google APIs

1. Construct Authorization Request for grant type = Authorization code
   1. endpoint = https://accounts.google.com/o/oauth2/v2/auth
   2. http type = GET
   3. response_type=code
   4. client_id=<my-client-id>
   5. scope=openid%20profile%20email%20https://www.googleapis.com/auth/photoslibrary.readonly
   6. state=state123
   7. redirect_uri=http://localhost:8080
   8. access_type=offline
   9. prompt=consent
   10. the entire URL = https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=<my-client-id>&scope=openid%20profile%20email%20https://www.googleapis.com/auth/photoslibrary.readonly&state=state123&redirect_uri=http://localhost:8080&access_type=offline&prompt=consent
   11. start a python HTTP server, to catch the redirect URL:
       1.  **python3 -m http.server 8080**
   12. Paste the URI in a browser
   13. after executing the request copy the auth code
2. Send Request and extract the Auth Code
3. Construct a Token Request
   1. https://oauth2.googleapis.com/token
   2. HTTP TYPE = POST
   3. grant_type =
   4. client_id =
   5. client_secret =
   6. code =
   7. redirect_uri =
4. the curl command
   ```
    curl --location 'https://oauth2.googleapis.com/token' \
        --header 'Content-Type: application/x-www-form-urlencoded' \
        --data-urlencode 'grant_type=authorization_code' \
        --data-urlencode 'client_id=<my-client-id>' \
        --data-urlencode 'client_secret=<my-client-secret>' \
        --data-urlencode 'code=<my-auth-code>' \
        --data-urlencode 'redirect_uri=http://localhost:8080'
   ```
5. Send Request and extract Token
6. Send Google Photos Request with the Access Token
7. Send Request and see one Album 
    ```
    curl --location 'https://photoslibrary.googleapis.com/v1/albums/ANIwyWEO1rRoPxS3MWH5J7ao0kU7AtvTUwlQdfgEB-D0r0rtUbuZtOPJJw4N1mSmuspaJOwZhmLy' \
    --header 'Authorization: Bearer <my-bearer-token>'
    ```
8. Send Request to see All Album Photos
   ```
   curl --location 'https://photoslibrary.googleapis.com/v1/albums' \
        --header 'Authorization: Bearer <my-bearer-token>'
   ```
9. Get All Photos (Medias) from album
   ```
   curl --location 'https://photoslibrary.googleapis.com/v1/mediaItems:search' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer <my-bearer-token>' \
    --data '{
    "albumId": "ANIwyWEO1rRoPxS3MWH5J7ao0kU7AtvTUwlQdfgEB-D0r0rtUbuZtOPJJw4N1mSmuspaJOwZhmLy"
    }'
   ```