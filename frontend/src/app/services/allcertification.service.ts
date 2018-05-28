import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';


import { Certification } from '../model/Certification';
import { baseUrlCertification } from '../baseUrl';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';
@Injectable()
export class AllCertificationService {

    private apiRoot = baseUrlCertification;
    constructor (private http: HttpClient,private handler:ErrorHandler) {}

    getAllCertificates(): Observable<Certification[]> {
        const url = `${this.apiRoot}/all`;
        console.log(url);
        return this.http.get(url)
        .pipe(
            catchError(this.handler.handleError)
        );
    }

   
}
