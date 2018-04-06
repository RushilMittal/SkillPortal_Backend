import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { baseUrlSkill } from '../baseUrl';
import { EmployeeSkillPlaceholder } from '../model/EmployeeSkillPlaceholder';

@Injectable()
export class DashBoardSkillPlaceHolderService {
    //Ashwins computer IP
    // private apiRoot = 'http://localhost:8000/api';
    private apiRoot = baseUrlSkill;
    constructor(private http: Http) { }

    getemployeeSkillPlaceholder(employeeId: string): Observable<EmployeeSkillPlaceholder> {
        // console.log('inside getemployeeSkillPlaceholder ');
        const url = `${this.apiRoot}/getEmployeeSkillPlaceholder?empId=${employeeId}`;
        // console.log(url);

        return this.http.get(url)
                    .map((response: Response) => <EmployeeSkillPlaceholder>response.json())
                    .do(data => console.log(JSON.stringify(data)))
                    .catch(this.handleError);
    }

    // created the Single Domain ofr handling the DASHBOARD SKILL PLACEHOLER
    // //Service function to get the total number of rated skills of the employee
    // getNumberOfTotalRatedSkill():Observable<number>{
    //     // console.log('inside getNumberofTotalRatedSkill');
    //     let url = `${this.apiRoot}/getTotalSkillsRated?empId=101`;

    //     return this.http.get(url)
    //                 .map((response: Response) => <number>response.json())
    //                 .do(data => console.log(JSON.stringify(data)))
    //                 .catch(this.handleError);
    // }

    // // Service for fetching the higest rated skill by the employee.
    // getHighestRatedSkillOfEmployee():Observable<String>{
    //     console.log('Inside getHigestRatedSkillByUser');
    //     let url = `${this.apiRoot}/getHighestRatedSkill?empId=101`;
    //     console.log(url);
    //     return this.http.get(url)
    //                     .map((response: Response) => response.text())
    //                     .do(data => console.log("IN Higest Rated" + data))
    //                     .catch(this.handleError);
    // }

    // //Service for fetching the Highest Rating of the Employee
    // getHighestRatingofEmployeeSkill():Observable<number>{
    //     console.log('Inside getHighestRatingofEmployeeSkill ');
    //     let url =`${this.apiRoot}/getHighestRating?empId=101`;
    //     console.log(url);

    //     return this.http.get(url)
    //                     .map((response: Response) => <number>response.json())
    //                     .do(data => console.log(JSON.stringify(data)))
    //                     .catch(this.handleError);
    // }

    // //Service for fetching , time period since when he has updated/rated the skill.
    // getLastUpdatedPeriodofEmployee():Observable<number[]>{
    //     console.log('Inside getLastUpdatedPeriodofEmployee');
    //     let url = `${this.apiRoot}/getLastUpdatedOfEmployee?empId=101`;
    //     console.log(url);
    //     return this.http.get(url)
    //                     .map((response: Response) => <number[]>response.json())
    //                     .do(data => console.log(JSON.stringify(data)))
    //                     .catch(this.handleError);
    // }



    private handleError(error: Response): Observable<any> {
        // in a real world app, we may send the server to some remote logging infrastructure
        // instead of just logging it to the console
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
      }

}
