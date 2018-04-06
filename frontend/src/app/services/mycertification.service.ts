import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { EmployeeCertificate } from '../model/EmployeeCertification';
import { baseUrlCertification } from '../baseUrl';


@Injectable()
export class MyCertificationService {
    url = baseUrlCertification + '/getcertifications?empId=101';

    constructor(private http: Http) {}

    getEmployeeCertification(): Observable<EmployeeCertificate> {
        return this.http.get(this.url)
                            .map((response: Response) => <EmployeeCertificate>response.json())
                            .do(data => console.log(JSON.stringify(data)))
                            .catch(this.handleError);
    }
    private handleError(error: Response): Observable<any> {
        // in a real world app, we may send the server to some remote logging infrastructure
        // instead of just logging it to the console
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
      }
}
