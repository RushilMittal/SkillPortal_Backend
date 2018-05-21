import { Injectable } from "@angular/core";
import { Observable } from 'rxjs/Observable';
import { baseUrlSkill } from "../baseUrl";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ErrorHandler } from "./handleerror.service";
import { SubSkill } from "../model/SubSkill";
import { catchError } from 'rxjs/operators';
import { AuthHelper } from "./authHelper.service";
import { Subscriber } from "rxjs";

@Injectable()

export class AdminServices{
    private apiRoot = baseUrlSkill;
    private _headers = new HttpHeaders();
    constructor (private http: HttpClient,private handler:ErrorHandler,
        private authHelper: AuthHelper) {}
    
    
    getAllAdminSkill():Observable<SubSkill>{
        const url = `${this.apiRoot}/getAllAdminSkills`;
        console.log(url);
        let token = this.authHelper.getAccessToken();
        let idToken = this.authHelper.getUser();

        console.log("inside the admin calls " + token);
        if (!this._headers.has('Authorization')) {
            const graphToken = token;
            this._headers = this._headers.set('Token',graphToken);
            this._headers = this._headers.set('Authorization','Bearer ' +idToken)
        }

        return this.http.get(url,{headers:this._headers})
        .pipe(
            catchError(this.handler.handleError)
        );
        
    }
}