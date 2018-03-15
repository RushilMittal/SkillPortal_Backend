import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { Skill } from '../model/Skill';
import {baseUrlSkill} from './baseUrl';


@Injectable()
export class AllSkillService {
  private apiRoot = baseUrlSkill;

  constructor(private http: Http) { }

  getAllSkill(): Observable<Skill> {
    console.log('inside getAllSkill');
    const url = `${this.apiRoot}/all`;
    console.log(url);
    return this.http.get(url)
            .map((response: Response) => <Skill>response.json())
            .do(data => console.log(JSON.stringify(data)))
            .catch(this.handleError);
  }

  // Getting One skill by providing SkillId.
  getSkillById(skillId: string): Observable<Skill> {
    console.log('inside getSkillById');
    const url = `${this.apiRoot}/getBySkillId?skillId=${skillId}`;
    console.log(url);

    return this.http.get(url)
            .map((response: Response) => <Skill>response.json())
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
