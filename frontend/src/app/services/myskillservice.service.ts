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

  private apiRoot = baseUrlSkill;
  constructor(private http: HttpClient) { }

  getEmployeeSkills(): Observable<EmployeeSkill[]> {

    const url = `${this.apiRoot}/getEmployeeSkills`;
    console.log(url);

    return this.http.get(url)
            .catch(this.handleError);
  }

saveEmployeeSkill(employeeSkill: EmployeeSkill): Observable<EmployeeSkill> {

    let toReturn ;
    if (employeeSkill) {
        toReturn = this.addEmployeeSkill(employeeSkill);

    }
    else{
        console.log("Employee SKill not present");
    }
    return toReturn;

}

private addEmployeeSkill(employeeSkill: EmployeeSkill): Observable<EmployeeSkill> {
    
    const url = `${this.apiRoot}/add?subSkillId=${employeeSkill.subSkill.id}&rating=${employeeSkill.rating}`;
    console.log(url);
    const  employeeSkilltemp  = JSON.stringify(employeeSkill);
    return this.http.post(url,employeeSkilltemp)   
        .catch(this.handleError);
}




  private handleError(error: any) {
    // in a real world app, we may send the server to some remote logging infrastructure
    // instead of just logging it to the console
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
}



}
