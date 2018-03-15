import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions,URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import {EmployeeCertificate} from '../model/EmployeeCertification';
import {baseUrl} from '../baseUrl';
import {training} from '../../app/model/training';
@Injectable()
export class trainingService{
    url='http://localhost:4200/assets/training.json';
    constructor(private http:Http){}
    getTrainingData():Observable<training>{
        return this.http.get(this.url).map((response: Response) => <training>response.json());
    }
    
}

