import { Injectable } from '@angular/core';
import { SubSkill } from '../model/SubSkill';
import { EmployeeSkill } from '../model/EmployeeSkill';
import { Observable} from 'rxjs/Observable';

@Injectable()
export class EmployeeService {

    constructor() {}

    provideEmployee(employeeSkill: EmployeeSkill): EmployeeSkill {
        return null;
    }
}
