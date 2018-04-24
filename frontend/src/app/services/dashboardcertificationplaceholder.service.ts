import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { baseUrlCertification } from '../baseUrl';
import { EmployeeCertificatePlaceholderModel } from '../model/EmployeeCertificatePlaceholderModel';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';

@Injectable()
export class DashboardCertificationPlaceholderService {
  apiRoot = baseUrlCertification;
  constructor(private http: HttpClient,private handler:ErrorHandler) { }

  getCertificatePlaceholder(empId: string): Observable<EmployeeCertificatePlaceholderModel[]> {
    // console.log('inside GetCertificatePlaceholder');
    const url = `${this.apiRoot}/getcertificationplaceholder?employeeId=${empId}`;
    // console.log(url);
    return this.http.get<EmployeeCertificatePlaceholderModel[]>(url)
      .pipe(
        catchError(this.handler.handleError)
      );

  }

}
