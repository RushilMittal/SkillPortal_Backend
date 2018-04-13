import { Injectable } from '@angular/core';
import { SubSkill } from '../model/SubSkill';
import { EmployeeSkill } from '../model/EmployeeSkill';
import { Observable} from 'rxjs/Observable';
import { baseUrlSkill } from '../baseUrl';

@Injectable()
export class EmployeeService {
    private apiRoot = baseUrlSkill;

    constructor() {}

    provideEmployee(employeeSkill: EmployeeSkill): EmployeeSkill {
        return null;
    }
}
