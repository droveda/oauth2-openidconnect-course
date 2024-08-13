import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private route:Router, private oauthService: OAuthService) { }

  ngOnInit(): void {
    console.log("> Calling HomeComponent.ngOnInit()...")
  }

  get validAccessToken() {
    console.log("> Calling HomeComponent.validAccessToken()...")
    return this.oauthService.hasValidAccessToken();
  }

  private extract(property:string):String {

    let claims = this.oauthService.getIdentityClaims()
    if (claims == null) {
      return null
    }

    return claims[property]
  }

  /**
   * Returns 'name' property
   */
  get name() {
    return this.extract('name');
  }

  /**
   * Returns 'email' property
   */
  get email() {
    return this.extract('email');
  }

  /**
   * Returns 'picture' property
   */
  get picture() {
    return this.extract('picture') || "assets/person.svg"
  }

  showAlbums() {

    console.log("> Calling HomeComponent.showAlbums()...")

    // reach out to the API and show the albums
    this.route.navigate(["/albums"]);
  }

}
