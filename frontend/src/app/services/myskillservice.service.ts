import { Injectable } from '@angular/core';
import { Http, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { EmployeeSkill } from '../model/EmployeeSkill';
import { baseUrlSkill } from '../baseUrl';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class MySkillService {
  // private url = './assets/EmployeeSkill.json';
  // private apiRoot = 'http://localhost:8000/api';
  private apiRoot = baseUrlSkill;
  constructor(private http: HttpClient) { }

  getEmployeeSkills(employeeId: string): Observable<EmployeeSkill[]> {
    // console.log('inside getEmployeeSkill');
    const url = `${this.apiRoot}/getEmployeeSkills?empId=101`;
    console.log(url);

    return this.http.get(url)
            .catch(this.handleError);
  }

saveEmployeeSkill(employeeSkill: EmployeeSkill): Observable<EmployeeSkill> {

    if (!employeeSkill.employeeId) {
        // console.log('PLease provide the EmployeeID');

    }
    return this.addEmployeeSkill(employeeSkill);

}

private addEmployeeSkill(employeeSkill: EmployeeSkill): Observable<EmployeeSkill> {
    // console.log('inside createEmployeeSkill');
    // console.log('sahib' + employeeSkill.subSkill.id + 'sahib');
    const url = `${this.apiRoot}/add?empId=${employeeSkill.employeeId}
                  &subSkillId=${employeeSkill.subSkill.id}
                &rating=${employeeSkill.rating}`;
                console.log(url);
   const  employeeSkilltemp  = JSON.stringify(employeeSkill);
    return this.http.post(url, { employeeSkilltemp})   // changed after httpclient by ashwin ' {} '
        // .map(this.extractData)
        // .do(data => console.log('createProduct: ' + JSON.stringify(data)))
        .catch(this.handleError);
}




  private handleError(error: any) {
    // in a real world app, we may send the server to some remote logging infrastructure
    // instead of just logging it to the console
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
}



}
