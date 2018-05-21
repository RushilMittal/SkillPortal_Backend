import { Injectable } from "@angular/core";

import * as Msal from 'msal';
import '../config/rxjs-extensions'
import { CONFIG } from "../config/config";
import { JwtHelper } from "./JwtHelper";



const CONFIGS = CONFIG.Settings;
@Injectable()
export class AuthHelper {
  public access_token = null;

  private app: any;
  public user = null;
  public isAuthenticated = false;

  constructor() {
    this.app = new Msal.UserAgentApplication(
      CONFIGS.CLIENT_ID,
      CONFIGS.AUTHORITY,
      (errorDesc, token, error, tokenType) => {
        // callback for login redirect
        if (error) {
          console.log(JSON.stringify(error));
          return;
        }
        console.log('Callback for login');
        this.access_token = token;
      }, {
        redirectUri: CONFIG.Settings.REDIRECT_URI,
        cacheLocation: 'localStorage'
      }
    );

  }

  public login() {
    console.log("Login called");

    return this.app.loginRedirect(CONFIGS.SCOPES).then(
      idToken => {
        this.app.acquireTokenSilent(CONFIGS.SCOPES).then(
          accessToken => {
            this.access_token = accessToken;
            this.user = this.app.getUser(); // AZURE AD
            this.isAuthenticated = true;
            this.refreshToken('Access Token', accessToken);
          },
          error => {
            this.app.acquireTokenPopup(CONFIGS.SCOPES).then(accessToken => {
              console.log('Error acquiring the popup:\n' + error);
            });
          }
        );
        console.log("user token " + this.user.idToken);
      },
      error => {
        console.log('Error during login:\n' + error);
      }
    );
  }

  public getUser(): string {
    let toReturn = null;
    if (this.isOnline())
      toReturn = localStorage.getItem('msal.idtoken');
    else
      this.login();

    console.log("id token " + toReturn);
    if (!((toReturn !== null) && (this.isValid(toReturn)))) {
      toReturn = null;
    }
    return toReturn;
  }

  public getAccessToken(): string {
    let toReturn = null;
    if (this.isOnline())
      toReturn = localStorage.getItem('Access Token');
    else
      this.login();

    if (!((toReturn !== null) && (this.isValid(toReturn)))) {
      toReturn = null;
    }
    return toReturn;

  }
  public logout() {
    this.app.logout();
    this.isAuthenticated = false;
    this.user = null;
  }

  public isOnline(): boolean {
    return this.app.getUser() != null;
  }

  public getCurrentLogin() {
    const user = this.app.getUser();
    return user;
  }

  public getMSGraphAccessToken() {
    return this.app.acquireTokenSilent(CONFIGS.SCOPES).then(
      accessToken => {
        this.refreshToken('Access Token', accessToken);
        return accessToken;
      },
      error => {
        return this.app.acquireTokenSilent(CONFIGS.SCOPES).then(
          accessToken => {
            this.refreshToken('Access Token', accessToken);
            return accessToken;
          },
          err => {
            console.error(err);
          });
      });
  }

  public isValid(token: string): boolean {
    var jwtHelper = new JwtHelper();
    var parsedToken = jwtHelper.decodeToken(token);
    let exp = parsedToken.exp * 1000;
    console.log("expired in " + exp);


    let a = new Date(exp).getTime();
    let b = new Date().getTime();
    if (a < b) {
      return false;
    }
    return true;
  }

  // Function for updation of token value
  refreshToken(tokenName: string, tokenValue: string) {
    localStorage.removeItem(tokenName);
    localStorage.setItem(tokenName, tokenValue);
  }
}