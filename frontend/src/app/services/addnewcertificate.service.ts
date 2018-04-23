import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { baseUrlCertification } from '../baseUrl';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { Observable} from 'rxjs/Observable';
import { EmployeeCertificate } from '../model/EmployeeCertification';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';
import { httpOptions } from '../httpheaders';



@Injectable()
export class AddNewCertificateService {
    private apiRoot = baseUrlCertification;
  
    constructor(private http: HttpClient, private handler:ErrorHandler) {}

    saveNewCertification(employeeCertification: EmployeeCertificate): Observable<EmployeeCertificate> {
        console.log('In the add certification component');
        const url = `${this.apiRoot}/addcertificate`;
        console.log(url);
        const employeeCertificationDomain = JSON.stringify(employeeCertification);

        return this.http.post<EmployeeCertificate>(url, employeeCertification,httpOptions)
            .pipe(
                catchError(this.handler.handleError)
            );
    }
}
