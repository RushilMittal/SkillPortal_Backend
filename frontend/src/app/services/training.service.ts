import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import {EmployeeCertificate} from '../model/EmployeeCertification';
import { Training } from '../model/Training';


@Injectable()
export class trainingService {
    url = 'http://localhost:4200/assets/training.json';
    constructor(private http: Http) {}
    getTrainingData(): Observable<Training> {
        return this.http.get(this.url).map((response: Response) => <Training>response.json());
    }

}

