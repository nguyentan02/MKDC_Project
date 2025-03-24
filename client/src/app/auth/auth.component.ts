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
    this.oauthService.initLoginFlow(); // Chuyển hướng đến trang đăng nhập WSO2
  }

  logout() {
    this.oauthService.logOut(); // Đăng xuất khỏi hệ thống
  }

  get userProfile() {
    return this.oauthService.getIdentityClaims(); // Lấy thông tin người dùng
  }
}
