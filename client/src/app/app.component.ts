import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { OAuthService } from 'angular-oauth2-oidc';
import { authConfig } from './app.config';
import { HomeComponent } from './home/home.component';
import { NgIf } from '@angular/common';
@Component({
  selector: 'app-root',
  standalone: true,
  imports:[RouterOutlet,NgIf],
  template: `
    <main>
      <header class="brand-name">
        <img class="brand-logo" src="/assets/logo.svg" alt="logo" aria-hidden="true" />
        <button (click)="login()" *ngIf="!isAuthorized">Login</button>
        <button (click)="logout()" *ngIf="isAuthorized">Logout</button>
      </header>
 <section class="content">
        <router-outlet></router-outlet> 
      </section>
    </main>
  `, 
})
export class AppComponent implements OnInit {
  isAuthorized = false;

  constructor(private oAuthService: OAuthService, private router: Router) {}

  ngOnInit() {
    this.oAuthService.configure(authConfig);
    this.oAuthService.loadDiscoveryDocumentAndTryLogin().then(() => {
      this.isAuthorized = this.oAuthService.hasValidAccessToken();
      if (!this.isAuthorized) {
        this.login(); // Nếu chưa đăng nhập, tự động điều hướng đến WSO2 để login
      } else {
        this.router.navigate(['/home']); // Nếu đã đăng nhập, vào trang home
      }
    });
  }

  login() {
    this.oAuthService.initLoginFlow();
  }

  logout() {
    this.oAuthService.logOut();
    this.isAuthorized = false;
    this.router.navigate(['/login']); // Điều hướng về trang login sau khi logout
  }
}
