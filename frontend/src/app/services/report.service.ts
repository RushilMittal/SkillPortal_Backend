import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SubSkill } from '../model/SubSkill';
import { EmployeeSkill } from '../model/EmployeeSkill';
import { EmployeeCertificate } from '../model/EmployeeCertification';
import { SkillReport } from '../model/skillreport';

@Injectable()
export class ReportService {

  constructor(private http: HttpClient) { }

  private apiRoot = 'http://localhost:8000/report';

  getSkillTrend(skill:number,date:number): Observable<SubSkill[]>{
    const url =  this.apiRoot + '/reportskilltrend?n='+skill+'&x='+date;
    return this.http.get<SubSkill[]>(url);
  }
  
  getTopSkill(skill:number): Observable<SubSkill[]>{
    const url =  this.apiRoot + '/reportskill?n='+skill;
    return this.http.get<SubSkill[]>(url);
  }
  
  getSkillsEmployee(empId:string): Observable<EmployeeSkill[]>{
    const url =  this.apiRoot + '/reportskillofemployee?empId='+empId;
    return this.http.get<EmployeeSkill[]>(url);
  }
  
  getExpiringCertificates(from:number,to:number): Observable<EmployeeCertificate[]>{
    const url =  this.apiRoot + '/reportcertificateexpiry?from='+from+'&to='+to;
    return this.http.get<EmployeeCertificate[]>(url);
  }
  
  getUpdatedSkills(from:number,to:number): Observable<SkillReport[]>{
    const url =  this.apiRoot + '/reportemployeeupdation?from='+from+'&to='+to;
    return this.http.get<SkillReport[]>(url);
  }

  getEmployeesWithASkill():Observable<String[]>{
    const url = this.apiRoot + '/getemployees';
    return this.http.get<String[]>(url);
  }

}