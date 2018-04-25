import { Injectable } from '@angular/core';
import { URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { baseUrlTraining } from '../baseUrl';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';
import { Event } from '../model/Event';

@Injectable()
export class EventService {
    apiRoot = baseUrlTraining;

    constructor(private httpClient: HttpClient,private handler:ErrorHandler) {
    }

    getEvents(): Observable<Event[]> {
        const url = `${this.apiRoot}/gettrainingevent`;
        return this.httpClient.get<Event[]>(url)
            .pipe(
                catchError(this.handler.handleError)
            );
    }

    getEventsList(): Observable<Event[]> {
        const url = `${this.apiRoot}/gettrainingeventlist`;

        return this.httpClient.get<Event[]>(url)
            .pipe(
                catchError(this.handler.handleError)
            );
    }


    deleteTraining(trainingId: string): Observable<Event[]> {
        const url = `${this.apiRoot}/delete?trainingId=${trainingId}`;
        //console.log(url);
        return this.httpClient.delete(url)
            .pipe(
                catchError(this.handler.handleError)
            );
    }

}
