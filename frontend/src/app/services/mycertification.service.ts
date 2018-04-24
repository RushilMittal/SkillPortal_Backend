import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { EmployeeCertificate } from '../model/EmployeeCertification';
import { baseUrlCertification } from '../baseUrl';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';


@Injectable()
export class MyCertificationService {
    url = baseUrlCertification + '/getcertifications';

    constructor(private http: HttpClient,
        private handler:ErrorHandler) {}

    getEmployeeCertification(): Observable<EmployeeCertificate[]> {
        return this.http.get<EmployeeCertificate[]>(this.url)
        .pipe(
            catchError(this.handler.handleError)
        );
    }
    
}
