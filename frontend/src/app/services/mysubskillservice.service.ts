import { Injectable } from '@angular/core';
import { Http, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import { SubSkill } from '../model/SubSkill';
import { baseUrlSkill } from '../baseUrl';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class MySubSkillService {
  private apiRoot = baseUrlSkill;

  constructor(private http: HttpClient) { }

  getEmployeeSubSkillExceptRatedSubSkill(skillId: string): Observable<SubSkill[]> {
    const url = `${this.apiRoot}/getSubSkillsBySkill?skillName=${skillId}`;
    return this.http.get(url)
      .catch(this.handleError);



  }

  getEmployeeSubSkillById(subSkillId: string): Observable<SubSkill> {
    const url = `${this.apiRoot}/getBySubSkillId?subSkillId=${subSkillId}`;
    return this.http.get(url)
      .catch(this.handleError);
  }


  private handleError(error: any): Observable<any> {
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }
}


