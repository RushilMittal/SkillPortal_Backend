import { Injectable } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { baseUrlSkill } from '../baseUrl';
import { EmployeeSkillPlaceholder } from '../model/EmployeeSkillPlaceholder';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { ErrorHandler } from './handleerror.service';

@Injectable()
export class DashBoardSkillPlaceHolderService {

    private apiRoot = baseUrlSkill;
    constructor(private http: HttpClient, private handler: ErrorHandler) { }

    getemployeeSkillPlaceholder(): Observable<EmployeeSkillPlaceholder> {
        const url = `${this.apiRoot}/getEmployeeSkillPlaceholder`;
        return this.http.get(url)
            .pipe(
                catchError(this.handler.handleError)
            );
    }

}
