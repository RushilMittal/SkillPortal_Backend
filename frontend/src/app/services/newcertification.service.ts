import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Certification } from '../model/Certification';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { baseUrlCertification } from '../baseUrl';

@Injectable()
export class NewCertificationService {

    private apiRoot = baseUrlCertification;

    constructor(private http: Http) { }

    saveNewCertification(certification: Certification): Observable<Certification> {
        // console.log('Inside save: ' + certification.certificationName + '' + certification.institution + '' + certification.skillId);
        const headers = new Headers({'Content-Type': 'application/json'});
        const options = new RequestOptions({ headers: headers});

        return this.addNewCertification(certification, options);
    }

    private addNewCertification(certification: Certification, options: RequestOptions): Observable<Certification> {

        // console.log('Adding New Certification:');

        const url = `${this.apiRoot}/add_new`;
        // console.log(url);

        const  newCertification = JSON.stringify(certification);
        // console.log(newCertification); // displays received data

        return this.http.post(url, newCertification, options)
            .map(this.extractData)
            .do(data => console.log('createCertification: ' + JSON.stringify(newCertification)))
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
