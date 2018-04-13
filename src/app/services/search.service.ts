import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import {SearchItem} from '../model/search-item';
import { Response } from '@angular/http/src/static_response';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';

import {Certification} from '../../app/model/Certification';
import { baseUrlSkill } from '../baseUrl';
@Injectable()
export class SearchService {
  url = baseUrlSkill + '/searchitems?searchTerm';
  certurl = baseUrlSkill + '/searchcertitems?searchTerm';
res: Response;

searchCert(term: string): Observable<Certification[]> {
  if (!term.trim()) {
    // if not search term, return empty hero array.
    return of([]);
  }
  return this.http.get<Certification[]>(`${this.certurl}=${term}`).pipe(
    tap(_ => console.log(`found heroes matching "${term}"`))
  );
}

searchSkills(term: string): Observable<SearchItem[]> {
  // console.log('called it');
  if (!term.trim()) {
    // if not search term, return empty hero array.
    return of([]);
  }
  return this.http.get<SearchItem[]>(`${this.url}=${term}`).pipe(
    tap(_ => console.log(`found heroes matching "${term}"`))
  );
}
constructor(private http: HttpClient) { }


}
