import { Injectable } from "@angular/core";

import * as Msal from 'msal';
import '../config/rxjs-extensions'
import { CONFIG } from "../config/config";



const CONFIGS = CONFIG.Settings;
@Injectable()
export class AuthHelper {
  public access_token = null;

  private app: any;
  public user;
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
      },{
        redirectUri:CONFIG.Settings.REDIRECT_URI
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
            console.log('ACCESS TOKEN: \n ' + this.access_token);
            this.user = this.app.getUser(); // AZURE AD
            this.isAuthenticated = true;
          },
          error => {
            this.app.acquireTokenPopup(CONFIGS.SCOPES).then(accessToken => {
              console.log('Error acquiring the popup:\n' + error);
            });
          }
        );
      },
      error => {
        console.log('Error during login:\n' + error);
      }
    );
  }
  public logout() {
    this.app.logout();
    this.isAuthenticated =false;
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
        return accessToken;
      },
      error => {
        return this.app.acquireTokenSilent(CONFIGS.SCOPES).then(
          accessToken => {
            return accessToken;
          },
          err => {
            console.error(err);
          });
      });
  }

}