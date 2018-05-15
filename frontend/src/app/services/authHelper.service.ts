import { Injectable } from "@angular/core";

import * as Msal from 'msal';
import '../config/rxjs-extensions'
import { CONFIG } from "../config/config";



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
      },{
        redirectUri:CONFIG.Settings.REDIRECT_URI,
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
  public getUser():string{
    let toReturn = null;
    
      console.log("Access token:"+this.access_token);
    
    if(this.isOnline())
      toReturn = localStorage.getItem('msal.idtoken');
    return toReturn;
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
 
  // public getIDtoken(){
  //   /* If this project is upgraded at any point (npm update) then below line can give error
  //   * kindly add these lines in index.d.ts in lib-commonjs
  //   * export {Storage} from "./Storage";
  //   * export { Constants} from "./Constants";
  //   */ 
  //   console.log("getIDtoken");
  //   let storage = new Msal.Storage("sessionStorage");
  //   console.log("sahib singh");
  //   const idToken = storage.getItem("msal.idtoken");
  //   console.log("sahib"+ idToken);
  // }
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