import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { EmployeeCertificate } from '../model/EmployeeCertification';
import { baseUrlCertification } from '../baseUrl';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class MyCertificationService {
    url = baseUrlCertification + '/getcertifications';

    constructor(private http: HttpClient) {}

    getEmployeeCertification(): Observable<EmployeeCertificate> {
        return this.http.get(this.url)
                            
                            .catch(this.handleError);
    }
    private handleError(error: Response): Observable<any> {
        // in a real world app, we may send the server to some remote logging infrastructure
        // instead of just logging it to the console
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
      }
}
