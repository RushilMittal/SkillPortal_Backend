import { Injectable } from '@angular/core';
import { baseUrlSkill } from '../baseUrl';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import { Training } from '../model/Training';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class DashboardTrainingPlaceholder {
    url = '../assets/training.json';

    constructor(private http: HttpClient) {}

    gettTrainingData(): Observable<Training[]> {
        // let url = `${this.apiRoot}`;

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
