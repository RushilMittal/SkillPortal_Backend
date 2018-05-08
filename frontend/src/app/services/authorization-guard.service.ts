import { Injectable, state } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
// import { EmployeeAuthorizationService } from './employee-authorization.service';

// tslint:disable-next-line:import-blacklist
import { Subscription, Observable } from 'rxjs';
import { AuthHelper } from './authHelper.service';


@Injectable()
export class AuthorizationGuard implements CanActivate {
    token: String = null;
    constructor(private router: Router, private authHelperService: AuthHelper) {
    }
    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        
        return this.checkloggedIn();
      }
    
     
  checkloggedIn():boolean{
    console.log("helper" + this.authHelperService.isOnline());
    if(this.authHelperService.isOnline()){
      return true;
    }
    console.log('outside the if')
    this.router.navigate(['login']);
    return false;

  }
}
