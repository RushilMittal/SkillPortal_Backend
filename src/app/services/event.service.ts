import { Injectable } from '@angular/core';
import { URLSearchParams} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { baseUrlTraining } from '../baseUrl';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class EventService {
    apiRoot = baseUrlTraining;

    constructor(private httpClient: HttpClient) {
    }

    getEvents(employeeId: string): Observable<Event[]> {
        const url = `${this.apiRoot}/gettrainingevent?empId=${employeeId}`;
        return this.httpClient.get(url)
            .catch(this.handleError);
    }

    getEventsList(employeeId: string): Observable<Event[]> {
        const url = `${this.apiRoot}/gettrainingeventlist?empId=${employeeId}`;

        return this.httpClient.get(url)
            .catch(this.handleError);
    }


    deleteTraining(employeeId: string, trainingId: string): Observable<void> {
        const url = `${this.apiRoot}/delete?empId=${employeeId}&trainingId=${trainingId}`;
        //console.log(url);
        return this.httpClient.delete(url)
        .catch(this.handleError);
    }


    private handleError(error: any): Observable<any> {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
      }

}
