import { Injectable } from '@angular/core';
import { SubSkill } from '../model/SubSkill';
import { EmployeeSkill } from '../model/EmployeeSkill';
import { Observable} from 'rxjs/Observable';
import { baseUrlSkill } from '../baseUrl';

@Injectable()
export class EmployeeAuthorizationService {
    private apiRoot = baseUrlSkill;
    status = false;
    redirectUrl: string = null;
    constructor() {}

    provideEmployee(employeeSkill: EmployeeSkill): EmployeeSkill {
        return null;
    }

    isLoggedIn(): boolean {
        return true;
    }

    changeStatus(param: boolean) {
        this.status = param;
    }
}
