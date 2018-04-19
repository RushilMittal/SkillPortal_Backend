import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Certification } from '../model/Certification';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { baseUrlCertification } from '../baseUrl';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class NewCertificationService {

    private apiRoot = baseUrlCertification;

    constructor(private http: HttpClient) { }

    saveNewCertification(certification: Certification): Observable<Certification> {
        return this.addNewCertification(certification);
    }

    private addNewCertification(certificationReceived: Certification): Observable<Certification> {
        // const url = `${this.apiRoot}/addnewemployeecertificate?skillId=${certificationReceived.skillId}&`+
        //             `certificationName=${certificationReceived.certificationName}&`+
        //             `institution=${certificationReceived.institution}`;

        const url = `${this.apiRoot}/addcertificate`;   
        const  certification = JSON.stringify(certificationReceived);
        return this.http.post(url, certification)
            .catch(this.handleError);
    }

    private extractData(response: Response) {
        const body = response.text() ? response.json() : {};
        return body.data;
    }

    private handleError(error: Response): Observable<any> {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}
