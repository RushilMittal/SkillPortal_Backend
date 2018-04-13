import { Injectable, state } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
// import { EmployeeAuthorizationService } from './employee-authorization.service';
import { OidcSecurityService } from 'angular-auth-oidc-client';
// tslint:disable-next-line:import-blacklist
import { Subscription, Observable } from 'rxjs';

@Injectable()
export class AuthorizationGuard implements CanActivate {
    token: String = null;
    constructor(private router: Router, private oidcSecurityService: OidcSecurityService) {
    }
    canActivate(next: ActivatedRouteSnapshot, states: RouterStateSnapshot): Observable<boolean> | boolean {
        return this.oidcSecurityService.getIsAuthorized().map(
            (authorized) => {
                if (!authorized) {
                    this.oidcSecurityService.authorize();
                    console.log('Authorized ' + authorized);
                    console.log('Token ' + localStorage.getItem('id_token'));
                    return false;
                } else { console.log(authorized);
                    return true;
                }
            }
        )
        ;
    }
}
