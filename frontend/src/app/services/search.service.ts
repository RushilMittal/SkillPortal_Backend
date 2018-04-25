import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import {SearchItem} from '../model/search-item';
import { Response } from '@angular/http/src/static_response';
import { of } from 'rxjs/observable/of';

import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';

import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';

import {Certification} from '../../app/model/Certification';
import { baseUrlSkill } from '../baseUrl';
import { NewTraining } from '../model/New-Training';
@Injectable()
export class SearchService {
  url = baseUrlSkill + '/searchskill?searchTerm';
  certurl = baseUrlSkill + '/searchcertitems?searchTerm';
  trainingurl = baseUrlSkill + '/searchtraining?searchTerm';
res: Response;

constructor(private http: HttpClient,private handler:ErrorHandler) { }

searchCert(term: string): Observable<Certification[]> {
  if (!term.trim()) {
    // if not search term, return empty hero array.
    return of([]);
  }
  return this.http.get<Certification[]>(`${this.certurl}=${term}`).pipe(
    catchError(this.handler.handleError));
}

searchTraining(term: string): Observable<NewTraining[]> {
  if (!term.trim()) {
    // if not search term, return empty hero array.
    return of([]);
  }
  return this.http.get<NewTraining[]>(`${this.trainingurl}=${term}`).pipe(
    catchError(this.handler.handleError));
}

searchSkills(term: string): Observable<string[]> {
  // console.log('called it');
  if (!term.trim()) {
    // if not search term, return empty hero array.
    return of([]);
  }
  return this.http.get<string[]>(`${this.url}=${term}`).pipe(
    catchError(this.handler.handleError));
}



}
