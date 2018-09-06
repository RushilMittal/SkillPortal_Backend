import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SubSkill } from '../model/SubSkill';
import { EmployeeSkill } from '../model/EmployeeSkill';
import { EmployeeCertificate } from '../model/EmployeeCertification';
import { SkillReport } from '../model/skillreport';
import { AuthHelper } from './authHelper.service';
import { ErrorHandler } from './handleerror.service';
import { catchError } from 'rxjs/operators';

@Injectable()
export class ReportService {
  private _headers = new HttpHeaders();
  constructor(private http: HttpClient, private authHelper: AuthHelper,
    private handler: ErrorHandler) { }

  private apiRoot = 'http://localhost:8000/report';

  getSkillTrend(modelSkill: number, date: number): Observable<SubSkill[]> {
    const url = this.apiRoot + '/reportskilltrend?n=' + modelSkill + '&x=' + date;
    let token = this.authHelper.getAccessToken();
    let idToken = this.authHelper.getUser();
    if (!this._headers.has('Authorization')) {
      const graphToken = token;
      this._headers = this._headers.set('Token', graphToken);
      this._headers = this._headers.set('Authorization', 'Bearer ' + idToken)
    }
    return this.http.get<SubSkill[]>(url, { headers: this._headers })
      .pipe(
        catchError(this.handler.handleError)
      );
  }

  getTopSkill(modelSkill: number): Observable<SubSkill[]> {
    const url = this.apiRoot + '/reportskill?n=' + modelSkill;
    let token = this.authHelper.getAccessToken();
    let idToken = this.authHelper.getUser();
    if (!this._headers.has('Authorization')) {
      const graphToken = token;
      this._headers = this._headers.set('Token', graphToken);
      this._headers = this._headers.set('Authorization', 'Bearer ' + idToken)
    }
    return this.http.get<SubSkill[]>(url, { headers: this._headers })
      .pipe(
        catchError(this.handler.handleError)
      );
  }

  getSkillsEmployee(empId: string): Observable<EmployeeSkill[]> {
    const url = this.apiRoot + '/reportskillofemployee?empId=' + empId;
    let token = this.authHelper.getAccessToken();
    let idToken = this.authHelper.getUser();
    if (!this._headers.has('Authorization')) {
      const graphToken = token;
      this._headers = this._headers.set('Token', graphToken);
      this._headers = this._headers.set('Authorization', 'Bearer ' + idToken)
    }
    return this.http.get<EmployeeSkill[]>(url, { headers: this._headers });
  }

  getExpiringCertificates(from: number, to: number): Observable<EmployeeCertificate[]> {
    const url = this.apiRoot + '/reportcertificateexpiry?from=' + from + '&to=' + to;
    let token = this.authHelper.getAccessToken();
    let idToken = this.authHelper.getUser();
    if (!this._headers.has('Authorization')) {
      const graphToken = token;
      this._headers = this._headers.set('Token', graphToken);
      this._headers = this._headers.set('Authorization', 'Bearer ' + idToken)
    }
    return this.http.get<EmployeeCertificate[]>(url, { headers: this._headers }).pipe(
      catchError(this.handler.handleError)
    );
  }

  getUpdatedSkills(from: number, to: number): Observable<SkillReport[]> {
    const url = this.apiRoot + '/reportemployeeupdation?from=' + from + '&to=' + to;
    let token = this.authHelper.getAccessToken();
    let idToken = this.authHelper.getUser();
    if (!this._headers.has('Authorization')) {
      const graphToken = token;
      this._headers = this._headers.set('Token', graphToken);
      this._headers = this._headers.set('Authorization', 'Bearer ' + idToken)
    }
    return this.http.get<SkillReport[]>(url, { headers: this._headers }).pipe(
      catchError(this.handler.handleError)
    );
  }

  getEmployeesWithASkill(): Observable<String[]> {
    const url = this.apiRoot + '/getemployees';
    let token = this.authHelper.getAccessToken();
    let idToken = this.authHelper.getUser();
    if (!this._headers.has('Authorization')) {
      const graphToken = token;
      this._headers = this._headers.set('Token', graphToken);
      this._headers = this._headers.set('Authorization', 'Bearer ' + idToken)
    }
    return this.http.get<String[]>(url, { headers: this._headers }).pipe(
      catchError(this.handler.handleError)
    );
  }

}
