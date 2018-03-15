import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import {EmployeeCertificate} from '../model/EmployeeCertification';
import {baseUrlCertification} from './baseUrl';
@Injectable()
export class MyCertificationService {
    url = baseUrlCertification + '/getcertifications?empId=101';
    constructor(private http: Http) {}
    getEmployeeCertification(): Observable<EmployeeCertificate> {
        return this.http.get(this.url).map((response: Response) => <EmployeeCertificate>response.json());
    }
}

