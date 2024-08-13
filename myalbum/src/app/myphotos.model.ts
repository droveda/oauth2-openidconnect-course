import { AuthConfig } from 'angular-oauth2-oidc';

/* -------------- GOOGLE AUTHORIZATION CONFIGURATION ------------ */

export const oauthConfig: AuthConfig = {

  // Authorization Server Issuer URL
  issuer: 'https://accounts.google.com',

  // After login, the Authorization Server will redirect to this location
  redirectUri: window.location.origin,

  // Registered ClientID in the Authorization Server. No Client Secret Required
  clientId: '<PLACE GOOGLE CLIENT ID HERE>',

  // Logout URL is not in Discovery document. So, we need to include it for GOOGLE
  strictDiscoveryDocumentValidation: false,
  logoutUrl: 'https://www.google.com/accounts/Logout',

  // Default IMPLICIT. responseType will automatically use token and id_token.
  // responseType: 'code',

  // set the scope for the permissions the client should request
  scope: 'openid profile email https://www.googleapis.com/auth/photoslibrary.readonly',

  showDebugInformation: true

};

export class MyAlbumsResourceConfig {
  static myalbums_resource_uri = 'https://photoslibrary.googleapis.com'
  static albums_uri = `${MyAlbumsResourceConfig.myalbums_resource_uri}/v1/albums`
  static photos_uri = `${MyAlbumsResourceConfig.myalbums_resource_uri}/v1/mediaItems:search`
}

/* -------------- OKTA AUTHORIZATION CONFIGURATION ------------ */

// export const oauthConfig: AuthConfig = {

//   // Authorization Server Issuer URL e.g https://dev-<SOME_NUMBER>.okta.com/oauth2/default
//   issuer: '<PLACE OKTA ISSUER URL HERE FROM OKTA CONSOLE>',

//   // After login, the Authorization Server will redirect to this location
//   redirectUri: window.location.origin,

//   // Registered ClientID in the Authorization Server. No Client Secret Required
//   clientId: '<PLACE OKTA CLIENT ID HERE>',

//   // Use Authorization Code with PKCE
//   responseType: 'code',

//   // set the scope for the permissions the client should request
//   scope: 'openid profile email photolibrary.read',

//   showDebugInformation: true

//   // Disable PKCE. Not recommended
//   // disablePKCI: true,

// };

// export class MyAlbumsResourceConfig {
//   static myalbums_resource_uri = 'http://localhost:8081'
//   static albums_uri = `${MyAlbumsResourceConfig.myalbums_resource_uri}/fakealbums/albums`
//   static photos_uri = `${MyAlbumsResourceConfig.myalbums_resource_uri}/fakealbums/mediaItems`
// }

