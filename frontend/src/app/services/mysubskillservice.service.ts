import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import { SubSkill } from '../model/SubSkill';
import {baseUrlSkill} from './baseUrl';
@Injectable()
export class MySubSkillService {
  private apiRoot = baseUrlSkill;

  constructor(private http: Http) { }

  getEmployeeSubSkillExceptRatedSubSkill(employeeId: string, skillId: string): Observable<SubSkill> {
    console.log('inside getEmployeeSubSkillExceptRatedSubSkill');
    const url = `${this.apiRoot}/getSubSkillsBySkillId?empId=${employeeId}&skillId=${skillId}`;
    console.log(url);
    return this.http.get(url)
            .map((response: Response) => <SubSkill>response.json())
            .do(data => console.log(JSON.stringify(data)))
            .catch(this.handleError);
            // .do(data => console.log(JSON.stringify(data)));
  }

  getEmployeeSubSkillById(subSkillId: string): Observable<SubSkill> {
    const url = `${this.apiRoot}/getBySubSkillId?subSkillId=${subSkillId}`;

    return this.http.get(url)
                .map((response: Response) => <SubSkill>response.json())
                .do(data => console.log(JSON.stringify(data)))
                .catch(this.handleError);


  }

  private extractData(response: Response) {
    const body = response.json();
    return body.data || {};
  }

  private handleError(error: Response): Observable<any> {
    // in a real world app, we may send the server to some remote logging infrastructure
    // instead of just logging it to the console
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }
}


