import { Component } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { oauthConfig } from './myphotos.model';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private oauthService: OAuthService ) {
  }

  ngOnInit(): void {
    console.log("> Calling AppComponent.ngOnInit()...")

    // Initiate the authorize request to the Authorization
    // Server.
    this.oauthService.configure(oauthConfig);
    this.oauthService.loadDiscoveryDocumentAndLogin();
  }

  /**
   * Logout of the application. Revokes the access token
   * and initiates a complete logout.
   */
  logout():boolean {
    console.log("> Calling AppComponent.logout()...")

    this.oauthService.revokeTokenAndLogout().catch(

      // This takes care of the case when revoking a token fails
      // because its already invalid. So this makes sure that we
      // still log out of the application and session cleanup is
      // done
      (error:any) => {
        console.log("> Calling just logout...")
        this.oauthService.logOut();
      }
    )

    // important
    return false
  }

  /**
   * Returns the url of the picture if available
   * Otherwise returns the default picture.
   */
  get picture() {

    let picture = 'assets/person.svg'
    let claims = this.oauthService.getIdentityClaims()
    if (claims && claims['picture']) {
      picture = claims['picture']
    }

    return picture
  }
}
