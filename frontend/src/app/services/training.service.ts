import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import {EmployeeCertificate} from '../model/EmployeeCertification';
import { Training } from '../model/Training';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class trainingService {
    url = 'http://localhost:4200/assets/training.json';
    constructor(private http: HttpClient) {}
    getTrainingData(): Observable<Training> {
        return this.http.get(this.url).map((response: Response) => <Training>response.json());
    }
}

