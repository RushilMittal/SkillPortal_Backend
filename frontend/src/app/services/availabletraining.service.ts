import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { HttpClient } from '@angular/common/http';
import { TrainingDomain } from '../model/training-domain';
import { baseUrlTraining } from '../baseUrl';

@Injectable()
export class AvailableTrainingService {
  // private apiRoot = 'http://localhost:8000/api';
  apiRoot = baseUrlTraining;
  constructor( private httpClient: HttpClient) { }



  getAvailableTraining(): Observable<TrainingDomain[]> {
    console.log(this.apiRoot);
    const url = this.apiRoot+"/getalltraining";
    // console.log(url);
    return this.httpClient.get<TrainingDomain[]>(url);
  }

  postEnroll(trainingId:string) {
    const headers = new Headers({'Content-Type': 'application/json'});
    const options = new RequestOptions({ headers: headers});
    const url = this.apiRoot+"/enroll?trainingId="+trainingId;
     console.log(url)
     console.log(trainingId)
    //console.log(this.httpClient.pos<TrainingDomain[]>(this.apiRoot));
    return this.httpClient.post(url, options);
    
  }

 
  private handleError(error: any): Observable<any> {
    // in a real world app, we may send the server to some remote logging infrastructure
    // instead of just logging it to the console
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }
}