import { Injectable } from '@angular/core';
import { Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import {  baseUrlTraining } from '../baseUrl';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { Observable} from 'rxjs/Observable';
import { TrainingDomain } from '../model/training-domain';
import { HttpClient,HttpHeaders } from '@angular/common/http';


@Injectable()
export class AddNewTrainingService{
   private apiRoot = baseUrlTraining;
   constructor(private http: HttpClient) {}

    public saveNewTraining(training :TrainingDomain): Observable<TrainingDomain> {      
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
               })
           };
       // const options = new RequestOptions({ headers: headers});

        return this.saveTraining (training,httpOptions);
   }

   saveTraining(training: TrainingDomain, httpOptions): Observable<TrainingDomain> {
     
       const url = `${this.apiRoot}/add`;

       console.log("training service "+ JSON.stringify( training));
     
       const toSendTraining = JSON.stringify(training);      
       return this.http.post(url, toSendTraining,httpOptions)
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