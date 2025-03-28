import { Component } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'app-auth',
  template:`
`
})
export class AuthComponent {
  constructor(private oauthService: OAuthService) {}

  login() {
    this.oauthService.initLoginFlow(); 
  }

  logout() {
    this.oauthService.logOut(); 
  }

  get userProfile() {
    return this.oauthService.getIdentityClaims(); 
  }
}
