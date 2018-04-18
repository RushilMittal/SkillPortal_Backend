import { Injectable } from '@angular/core';
import { Http, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { baseUrlSkill } from '../baseUrl';
import { EmployeeSkillPlaceholder } from '../model/EmployeeSkillPlaceholder';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class DashBoardSkillPlaceHolderService {
    // private apiRoot = 'http://localhost:8000/api';
    private apiRoot = baseUrlSkill;
    constructor(private http: HttpClient) { }

    getemployeeSkillPlaceholder(): Observable<EmployeeSkillPlaceholder> {
        const url = `${this.apiRoot}/getEmployeeSkillPlaceholder`;
        return this.http.get(url)
                    .catch(this.handleError);
    }




    private handleError(error: any): Observable<any> {
        // in a real world app, we may send the server to some remote logging infrastructure
        // instead of just logging it to the console
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
      }

}
