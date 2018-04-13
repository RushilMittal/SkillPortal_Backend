import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { EmployeeSkill } from '../model/EmployeeSkill';
import { baseUrlSkill } from '../baseUrl';

@Injectable()
export class MySkillService {
  // private url = './assets/EmployeeSkill.json';
  // private apiRoot = 'http://localhost:8000/api';
  private apiRoot = baseUrlSkill;
  constructor(private http: Http) { }

  getEmployeeSkills(employeeId: string): Observable<EmployeeSkill[]> {
    // console.log('inside getEmployeeSkill');
    const url = `${this.apiRoot}/getEmployeeSkills?empId=101`;
    // console.log(url);

    return this.http.get(url)
            .map((response: Response) => <EmployeeSkill[]>response.json())
            .do(data => console.log(JSON.stringify(data)))
            .catch(this.handleError);
  }

saveEmployeeSkill(employeeSkill: EmployeeSkill): Observable<EmployeeSkill> {
    const headers = new Headers({ 'Content-Type': 'application/json' });
    const options = new RequestOptions({ headers: headers });

    if (!employeeSkill.employeeId) {
        // console.log('PLease provide the EmployeeID');

    }
    return this.addEmployeeSkill(employeeSkill, options);

}

private addEmployeeSkill(employeeSkill: EmployeeSkill, options: RequestOptions): Observable<EmployeeSkill> {
    // console.log('inside createEmployeeSkill');
    // console.log('sahib' + employeeSkill.subSkill.id + 'sahib');
    const url = `${this.apiRoot}/update?empId=${employeeSkill.employeeId}
                  &subSkillId=${employeeSkill.subSkill.id}
                &rating=${employeeSkill.rating}`;
                // console.log(url);
   const  employeeSkilltemp  = JSON.stringify(employeeSkill);
    return this.http.post(url, employeeSkilltemp, options)
        .map(this.extractData)
        .do(data => console.log('createProduct: ' + JSON.stringify(data)))
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

  // doPOSTEmployeeSkill(employeeSkillData: EmployeeSkill){
  //     console.log("Inside thedoPOSTEmployeeSkill");
  //     let url =`${this.apiRoot}/add`;

  //     this.http.post(url,{employeeSkillData}).subscribe(res => console.log(res.json()));

  // }

  // doPUTEmployeeSkill(){

  // }
  // doDELETEEMployeeSskill(){

  // }


  // //For later stages and authorization purpose.
  // doGETWithHeaders() {
  //   console.log("GET WITH HEADERS");
  //   let headers = new Headers();
  //   headers.append('Authorization', btoa('username:password'));
  //   let opts = new RequestOptions();
  //   opts.headers = headers;
  //   let url = `${this.apiRoot}/get`;
  //   this.http.get(url, opts).subscribe(
  //     res => console.log(res.json()),
  //     msg => console.error(`Error: ${msg.status} ${msg.statusText}`)
  //   );
  // }

  // private handleError(err: any){
  //   console.log(err.message);
  //   return Observable.throw(err.message);
  // }
  // // private extractData(response: Response) {
  // //   let body = response.json();
  // //   return body.data || {};
  // // }



}
