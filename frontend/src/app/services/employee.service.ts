import { Injectable } from '@angular/core';
import { SubSkill } from '../model/SubSkill';
import { EmployeeSkill } from '../model/EmployeeSkill';
import { Observable } from 'rxjs/Observable';
import { AuthHelper } from './authHelper.service';
import { Headers } from '@angular/http'
import { HttpClient } from '@angular/common/http';
import { EmployeeDetails } from '../model/EmployeeDetail';
import { CONFIG } from '../config/config';
import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';
import { HttpHeaders } from '@angular/common/http';
import { RequestOptions } from '@angular/http/src/base_request_options';
import { HttpHeaderResponse } from '@angular/common/http/src/response';
import { ADMINROLES } from '../config/adminRoles';


const GRAPH_V1_API = CONFIG.Settings.MSGRAPH_v1_API;
@Injectable()
export class EmployeeService {
    public employeeDetails: EmployeeDetails;
    private authHelperService: AuthHelper;
    private _headers = new HttpHeaders();
    private adminRoles = ADMINROLES;
    public isAdmin = false;

    constructor(private httpClient: HttpClient,
        private authHelper: AuthHelper,
        private handler: ErrorHandler
    ) {
        this.authHelperService = authHelper;
        this._headers = this._headers.append('Content-Type', 'application/x-www-form-  urlencoded');
    }

    /* 
    * Fetching basic user profile from the grapgh api
    * Helper function for the InitalizeDetails
    */
    getUserprofile(token: string): Observable<any> {
        if (!this._headers.has('Authorization')) {
            const graphToken = token;
            this._headers = this._headers.set('Authorization', 'bearer ' + graphToken);
        }
        return this.httpClient.get(GRAPH_V1_API + 'me', { headers: this._headers })
            .pipe(
                catchError(this.handler.handleError)
            );

    }

    /*
    * Function used to initialize the userdetails.
    * Called at login time.
    */
    initializeEmployeeDetails() {
        this.authHelperService.getMSGraphAccessToken().then(token => {
            this.getUserprofile(token).
                subscribe(data => {
                    this.employeeDetails = data;
                    console.log(this.employeeDetails.jobTitle);
                },
                    err => console.log(err),
                    () => console.log(this.checkRole())
                );
        }, error => {
            console.log(error);
        });
    }

    /*
    * Function to determie whether the user is allowed to access the admin resources.
    * returns true for admin roles and viceversa
    */
    checkRole(): boolean {
        if (this.checkUser(this.employeeDetails.jobTitle)) {
            this.isAdmin = true;
            return true;
        } else {
            this.isAdmin = false;
            return false;
        }

    }

    /*
    * Helper function used in determine the admin role for the particular user.
    * param : role (jobtitle of the user) from graph.
    */
    checkUser(role: string): boolean {
        let i = 0;
        for (i = 0; i < this.adminRoles.length; i++) {
            if (role === this.adminRoles[i])
                return true
        }
        return false;
    }
    /**
    * Function used to fetch the image of the user from the graph. 
    */
    getImage(token: string): Observable<Blob> {
        if (!this._headers.has('Authorization')) {
            const graphToken = token;
            this._headers = this._headers.set('Authorization', 'bearer ' + graphToken);
        }
        return this.httpClient.get(GRAPH_V1_API + 'me/photos/48x48/$value',
            {
                headers: this._headers,
                responseType: "blob"
            })
            .pipe(
                catchError(this.handler.handleError)
            );
    }
}
