import { Injectable } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { EmployeeSkill } from '../model/EmployeeSkill';
import { baseUrlSkill } from '../baseUrl';
import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';
import { httpOptions } from '../httpheaders';

@Injectable()
export class MySkillService {
    // Skill Url for fetching the skills
    private apiRoot = baseUrlSkill;
    constructor(private http: HttpClient,private handler:ErrorHandler) { }
    /*
    * Function used to Get all the Employee Skills.
    */
    getEmployeeSkills(): Observable<HttpResponse<EmployeeSkill[]>> {

        const url = `${this.apiRoot}/getEmployeeSkills`;
        console.log(url);

        return this.http.get<EmployeeSkill[]>(url, { observe: 'response' })
            .pipe(
                catchError(this.handler.handleError)
            );
    }

    saveEmployeeSkill(employeeSkill: EmployeeSkill): Observable<EmployeeSkill> {

        let toReturn;
        if (employeeSkill) {
            toReturn = this.addEmployeeSkill(employeeSkill);
        }
        else {
            console.log("Employee SKill not present");
        }
        return toReturn;

    }

    private addEmployeeSkill(employeeSkill: EmployeeSkill): Observable<EmployeeSkill> {

        const url = `${this.apiRoot}/add?subSkillId=${employeeSkill.subSkill.id}&rating=${employeeSkill.rating}`;
        console.log(url);
        return this.http.post<EmployeeSkill>(url, employeeSkill,httpOptions)
            .pipe(
                catchError(this.handler.handleError)
            );
    }
}
