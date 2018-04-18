import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { baseUrlCertification } from '../baseUrl';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { Observable} from 'rxjs/Observable';
import { EmployeeCertificate } from '../model/EmployeeCertification';
import { HttpClient, HttpHeaders } from '@angular/common/http';



@Injectable()
export class AddNewCertificateService {
    private apiRoot = baseUrlCertification;
    constructor(private http: HttpClient) {}

    saveNewCertification(employeeCertification: EmployeeCertificate): Observable<EmployeeCertificate> {
        console.log('In the add certification component');
        const url = `${this.apiRoot}/addCertification?certificationId=${employeeCertification.certificationId.id}`+
                    `&certificationDateString=${employeeCertification.certificationDate}`+
                    `&certificationValidityDateString=${employeeCertification.certificationValidityDate}`+
                    `&certificationNumber=${employeeCertification.certificationNumber}`+
                    `&certificationUrl=${employeeCertification.certificationUrl}`;

        const employeeCertificationDomain = JSON.stringify(employeeCertification);

        return this.http.post(url, employeeCertificationDomain)
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
