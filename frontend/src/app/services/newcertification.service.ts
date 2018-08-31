import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Certification } from '../model/Certification';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import {  baseUrlAdmin } from '../baseUrl';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';
import { httpOptions } from '../httpheaders';
import { AuthHelper } from './authHelper.service';

@Injectable()
export class NewCertificationService {

    private apiRoot = baseUrlAdmin;
    private _headers = new HttpHeaders();
    constructor(private http: HttpClient,private handler:ErrorHandler,private authHelper: AuthHelper) { }

    saveNewCertification(certification: Certification): Observable<Certification> {
        return this.addNewCertification(certification);
    }

    private addNewCertification(certificationReceived: Certification): Observable<Certification> {
        const url = `${this.apiRoot}/add_new`;
        let token = this.authHelper.getAccessToken();
        let idToken = this.authHelper.getUser();

        console.log("inside the admin calls " + token);
        if (!this._headers.has('Authorization')) {
            const graphToken = token;
            this._headers = this._headers.set('Token', graphToken);
            this._headers = this._headers.set('Authorization', 'Bearer ' + idToken)
        }
        return this.http.post<Certification>(url, certificationReceived,{ headers: this._headers })
            .pipe(
                catchError(this.handler.handleError)
            );
    }  
}
