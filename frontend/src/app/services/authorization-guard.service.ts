import { Injectable, state } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { EmployeeAuthorizationService } from './employee-authorization.service';

@Injectable()
export class AuthorizationGuard implements CanActivate {
    constructor(private employeeAuthorization: EmployeeAuthorizationService,
                private router: Router) {

    }
    canActivate(route: ActivatedRouteSnapshot, states: RouterStateSnapshot): boolean {
        console.log(states.url);
        return this.checkLoggedIn(states.url);
    }

    checkLoggedIn(redirectUrl: string): boolean {
        if (this.employeeAuthorization.isLoggedIn()) {
            return true;
        }
        this.employeeAuthorization.redirectUrl = redirectUrl;
        this.router.navigate(['login']);
        return false;
    }
}
