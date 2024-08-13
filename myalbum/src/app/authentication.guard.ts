import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { OAuthService } from 'angular-oauth2-oidc';
import { Observable } from 'rxjs';

/**
 * If the access token has expired or is invalid, then routes should
 * not be accessible. This Guard protects the configured routes and
 * makes sure that the access token is still valid.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {

  constructor(private oauthService: OAuthService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      if (!this.oauthService.hasValidAccessToken()) {

        // reloading the home page to initiate
        // the authorize request again to get a token
        window.location.href  = "http://localhost:4200/"
        return false
      }
      else
        return true
  }

}
