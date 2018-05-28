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
import { Router } from '@angular/router';
import { Role } from '../model/Role';
import { AdminServices } from './adminService';



const GRAPH_V1_API = CONFIG.Settings.MSGRAPH_v1_API;
@Injectable()
export class EmployeeService {
    public employeeDetails: EmployeeDetails;
    private authHelperService: AuthHelper;
    private _headers = new HttpHeaders();
    private adminRoles = ADMINROLES;
    public isAdmin = false;
    source: Role[];

    constructor(private httpClient: HttpClient,
        private authHelper: AuthHelper,
        private handler: ErrorHandler,
        private router: Router,
        private adminService: AdminServices
    ) {
        this.authHelperService = authHelper;
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
                    () => console.log(this.checkRoleAdmin())
                );
        }, error => {
            console.log(error);
        });

        this.loadSource();
    }

    /*
    * Function to fetch all the roles present in the Collection
    * fills the source Roles array with data
    */
    loadSource() {
        this.adminService.getAllAdminRoles().subscribe(
            data => {
                this.source = data;

            }, error => {
                console.log("error error" + error);
            }
        );
    }

    /*
    * Function to determie whether the user is allowed to access the admin resources.
    * returns true for admin roles and viceversa
    */
    checkRoleAdmin(): boolean {
        if (this.employeeDetails) {
            let check = this.checkUser(this.employeeDetails.jobTitle) || this.checkUser(this.employeeDetails.mail);
            console.log("mail id" + this.employeeDetails.mail);
            if (check) {
                this.isAdmin = true;
                return true;
            } else {
                this.isAdmin = false;
                return false;
            }
        } else {
            this.initializeEmployeeDetails();
            this.router.navigate(['/dashboard']);
        }

    }


    /*
    * Helper function used in determine the admin role for the particular user.
    * param : role (jobtitle of the user) from graph.
    */
    checkUser(role: string): boolean {
        if (this.source) {
            let i = 0;
            for (i = 0; i < this.source.length; i++) {
                if (role === this.source[i].userRole)
                    return true;
            }
        } else {
            this.loadSource();
            return false;
        }

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

    /*
    * Function used to send the email to the admins
    */
    sendEmail(token: string, data: string, subjectRecieved: string) {
        if (!this._headers.has('Authorization')) {
            const graphToken = token;
            this._headers = this._headers.set('Authorization', 'bearer ' + graphToken);
            this._headers = this._headers.set('Content-Type', 'application/json');

        }
        // emails are hardcoded yet, later if allowed 
        // we can fetch the list of emails from the getGroupEmails() functin defined in this file.
        // Function is incomplete need permission for implementation as many emails will flow in the inbox ;-)
        let eobj = {
            message: {
                subject: subjectRecieved as string,
                body: {
                    contentType: "Text" as string,
                    content: data as string
                },
                toRecipients: [
                    {
                        emailAddress: {
                            address:
                                "sdsahib95@gmail.com" as string
                        }
                    },
                    {
                        emailAddress: {
                            address:
                                "sdsahib_singh@yahoo.co.in" as string
                        }
                    }
                ]
            }
        };


        return this.httpClient.post(GRAPH_V1_API + 'me/sendMail', eobj,
            {
                headers: this._headers

            })
            .pipe(
                catchError(this.handler.handleError)
            );
    }

    /*
    * Function used to fetch all the email ids of the admin.
    * Require to read the users from group
    * Complete it after confirmation from sir.
    * PENDING
    * Return array should contain items like below for proper working.
    * {
    *      emailAddress: {
    *           address: 
    *               "sdsahib_singh@yahoo.co.in" as string
    *       }
    *  }
    */
    getGroupEmails(): string[] {
        return null;

    }

}
