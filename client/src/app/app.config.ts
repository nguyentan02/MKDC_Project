import { ApplicationConfig, APP_INITIALIZER } from '@angular/core';
import { provideRouter, Router } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideOAuthClient, OAuthService, AuthConfig } from 'angular-oauth2-oidc';
import { routes } from './app.routes';
export const authConfig: AuthConfig = {
  issuer: 'https://localhost:9444/oauth2/token',
  redirectUri: 'http://localhost:4200/',
  clientId: 'bnfUjFjilytNcx83AnvMDk45WSka',
  responseType: 'code',
  scope: 'openid profile email internal_login',
  strictDiscoveryDocumentValidation: false,
};

function initializeOAuth(oauthService: OAuthService, router: Router): () => Promise<void> {
  return () => {
    oauthService.configure(authConfig);
    return oauthService.loadDiscoveryDocumentAndTryLogin().then(() => {
      if (oauthService.hasValidAccessToken()) {
        router.navigateByUrl('/home'); // Điều hướng sau khi đăng nhập
      } else {
        router.navigateByUrl('/login'); // Điều hướng đến trang đăng nhập nếu chưa đăng nhập
      }
    });
  };
}

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),
  provideHttpClient(),
  provideOAuthClient(),
  {
    provide: APP_INITIALIZER,
    useFactory: initializeOAuth,
    deps: [OAuthService,Router],
    multi: true
  }
  ]
};
