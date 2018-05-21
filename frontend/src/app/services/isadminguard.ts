import { Injectable } from "@angular/core";
import { CanActivate } from "@angular/router";
import { EmployeeService } from "./employee.service";

@Injectable()

export class IsAdminGuard implements CanActivate {
    constructor(private employeeService: EmployeeService) { }
    canActivate(): boolean {
        if (this.employeeService.checkRoleAdmin())
            return true;
        return false;
    }

}