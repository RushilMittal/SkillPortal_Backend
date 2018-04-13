import { Injectable } from '@angular/core';
import { Http, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { Skill } from '../model/Skill';
import { baseUrlSkill } from '../baseUrl';
import { HttpClient } from '@angular/common/http';
import { SubSkill } from '../model/SubSkill';

@Injectable()
export class AllSkillService {
  // private apiRoot = 'http://localhost:8000/api';
  private apiRoot = baseUrlSkill;
  constructor(private http: HttpClient, private httpClient: HttpClient) { }

  getAllSkillsData() {
    const url =  this.apiRoot + '/getallskills';
    return this.httpClient.get<SubSkill>(url);

 }


  getAllSkill(): Observable<Skill> {
    // console.log('inside getAllSkill');
    const url = `${this.apiRoot}/all`;
    // console.log(url);
    return this.http.get(url)
            // .map((response: Response) => <Skill>response.json())
            // .do(data => console.log(JSON.stringify(data)))
            .catch(this.handleError);
  }

  // Getting One skill by providing SkillId.
  getSkillById(skillId: string): Observable<Skill> {
    // console.log('inside getSkillById');
    const url = `${this.apiRoot}/getBySkillId?skillId=${skillId}`;
    // console.log(url);

    return this.http.get(url)
            // .map((response: Response) => <Skill>response.json())
            // .do(data => console.log(JSON.stringify(data)))
            .catch(this.handleError);
  }

  getSkillByName(skillName: string): Observable<Skill> {
    // console.log('inside getSkillByName');
    const url = `${this.apiRoot}/getBySkillName?skillName=${skillName}`;

    // console.log(url);

    return this.http.get(url)
              // .map((response: Response) => <Skill>response.json())
              // .do(data => console.log('skilldata' + JSON.stringify(data)))
              .catch(this.handleError);
  }

  // private extractData(response: Response) {
  //   switch (response.status) {
  //     case 200:
  //   }

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
