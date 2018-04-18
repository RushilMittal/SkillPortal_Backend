import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';

import { Certification } from '../model/Certification';
import { baseUrlCertification } from '../baseUrl';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AllCertificationService {

    private apiRoot = baseUrlCertification;
    constructor (private http: HttpClient) {}

    getAllCertificates(): Observable<Certification> {

        // console.log('These are Available Certifications:');
        const url = `${this.apiRoot}/all`;
        // console.log(url);
        return this.http.get(url)
            .catch(this.handleError);
    }

    private handleError(error: Response): Observable<any> {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}
