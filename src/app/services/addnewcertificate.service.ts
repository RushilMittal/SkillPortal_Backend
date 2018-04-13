import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { baseUrlCertification } from '../baseUrl';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { Observable} from 'rxjs/Observable';
import { EmployeeCertificate } from '../model/EmployeeCertification';



@Injectable()
export class AddNewCertificateService {
    private apiRoot = baseUrlCertification;
    constructor(private http: Http) {}

    saveNewCertification(employeeCertification: EmployeeCertificate): Observable<EmployeeCertificate> {

        const headers = new Headers({'Content-Type': 'application/json'});
        const options = new RequestOptions({ headers: headers});

        return this.addNewCertification(employeeCertification, options);
    }


    addNewCertification(employeeCertification: EmployeeCertificate, options: RequestOptions): Observable<EmployeeCertificate> {
        // console.log('In the add certification component');
        const url = `${this.apiRoot}/addcertificate`;

        // console.log(url);
        const toSendEmployeeCertification = JSON.stringify(employeeCertification);
        // console.log(toSendEmployeeCertification);
        return this.http.post(url, toSendEmployeeCertification, options)
            .map(this.extractData)
            .do(data => console.log('createCertification: ' + JSON.stringify(data)))
            .catch(this.handleError);


    }

    private extractData(response: Response) {
        const body = response.text() ? response.json() : {};
        return body.data;

        // below mentioned snippet generates Unexpected end of JSON error
        // let body = response.json();
        // return body.data || {};
    }

    private handleError(error: Response): Observable<any> {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}
