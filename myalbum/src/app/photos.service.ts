import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { MyAlbumsResourceConfig } from './myphotos.model';

/**
 * This Service sends HTTP messages to the Resource API configured
 * in the class MyAlbumsResourceConfig which is imported above.
 * It retrieves the albums and Photos from the resource server and
 * has been tested with Okta and Google Authorization Servers.
 */
@Injectable({
  providedIn: 'root'
})
export class PhotosService {

  /**
   * Inject the OAuthService to get access to the OAuth 2.0
   * Access token. Inject the HttpClient to access the
   * Resource API
   */
  constructor(private oauthService: OAuthService, private http: HttpClient) { }

  /**
   * Starts the process of retrieving the Albums and returns
   * a Promise object for the caller to handle returned Albums.
   */
  retrieveAlbumsPromise(): Promise<any> {
    console.log("> Calling PhotosService.PhotosService()...")
    let token = this.oauthService.getAccessToken()
    //console.log('Using token = ' + token)

    // set the Authorization header
    let hdr = new HttpHeaders();
    hdr = hdr.set('Authorization', `Bearer ${token}`);

    // Make a get request to the Resource Server and pass
    // the Promise to the caller
    return this.http
      .get(MyAlbumsResourceConfig.albums_uri, { headers: hdr })
      .toPromise()
  }

  /**
   * Starts the process of retrieving the Photos for an album
   * and returns a Promise object for the caller to handle
   * returned Photos.
   */
  retrievePhotosPromise(albumId: string): Promise<any> {
    console.log("> Calling PhotosService.retrievePhotosPromise()...")
    let token = this.oauthService.getAccessToken()
    //console.log('token =' + token)

    // set the Authorization header
    let hdr = new HttpHeaders();
    hdr = hdr.set('Authorization', `Bearer ${token}`)
      .set('Content-Type', 'application/json');

    let body = `{ "albumId" : "${albumId}"}`

    // Make a post request to the Resource Server and pass
    // the Promise to the caller
    return this.http
      .post(MyAlbumsResourceConfig.photos_uri, body, { headers: hdr })
      .toPromise();
  }

}
