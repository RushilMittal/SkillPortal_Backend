import { Injectable } from '@angular/core';
import { Http, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { SubSkill } from '../model/SubSkill';
import { baseUrlSkill } from '../baseUrl';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AllSubSkillService {
  private apiRoot = baseUrlSkill;

  constructor(private http: HttpClient) { }

  getSubSkill(): Observable<SubSkill> {
    // console.log('inside getSkill');
    const url = `${this.apiRoot}/all`;
    // console.log(url);


    // we need to fetch the skill of employee except the rated ones.
    return this.http.get(url)

            .catch(this.handleError);

            // .do(data => console.log(JSON.stringify(data)));
  }

  // private extractData(response: Response) {
  //   const body = response.json();
  //   return body.data || {};
  // }

  private handleError(error: any): Observable<any> {
    // in a real world app, we may send the server to some remote logging infrastructure
    // instead of just logging it to the console
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }
}
