import { Injectable } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import { Skill } from '../model/Skill';
import { baseUrlSkill } from '../baseUrl';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SubSkill } from '../model/SubSkill';
import { httpOptions } from '../httpheaders';
import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';
import { AuthHelper } from './authHelper.service';

@Injectable()
export class AllSkillService {

  private apiRoot = baseUrlSkill;
  private _headers = new HttpHeaders();
  
  constructor(private http: HttpClient, 
    private handler: ErrorHandler,
    private authHelper: AuthHelper) { }

  getAllSkillsData() {
    const url = this.apiRoot + '/getallskills';
    return this.http.get<SubSkill>(url)
      .pipe(
        catchError(this.handler.handleError)
      );;

  }


  getAllSkill(): Observable<Skill> {
    const url = `${this.apiRoot}/all`;
    return this.http.get(url)
      .pipe(
        catchError(this.handler.handleError)
      );
  }

  // Getting One modelSkill by providing SkillId.
  getSkillById(skillId: string): Observable<Skill> {
    // console.log('inside getSkillById');
    const url = `${this.apiRoot}/getBySkillId?skillId=${skillId}`;
    return this.http.get(url)
      .pipe(
        catchError(this.handler.handleError)
      );
  }

  getSkillByName(skillName: string): Observable<Skill> {
    const url = `${this.apiRoot}/getBySkillName?skillName=${skillName}`;
    return this.http.get(url)
      .pipe(
        catchError(this.handler.handleError)
      );
  }

  
}
