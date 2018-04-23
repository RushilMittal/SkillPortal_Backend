import { Injectable } from '@angular/core';
import { baseUrlSkill } from '../baseUrl';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Training } from '../model/Training';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';

@Injectable()
export class DashboardTrainingPlaceholder {
    url = '../assets/training.json';

    constructor(private http: HttpClient,private handler:ErrorHandler) { }

    gettTrainingData(): Observable<Training[]> {
        // let url = `${this.apiRoot}`;

        return this.http.get(this.url)
            .pipe(
                catchError(this.handler.handleError)
            );
    }

}
