import { Component, OnInit, OnDestroy, Output } from '@angular/core';
// import { EmployeeAuthorizationService } from '../../services/employee-authorization.service';
import { Router } from '@angular/router';
// tslint:disable-next-line:import-blacklist
import { Subscription, Observable } from 'rxjs';
import { OidcSecurityService, AuthorizationResult } from 'angular-auth-oidc-client';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  isAuthorized: boolean;
  isAuthorizedSubscription: Subscription;
  apiResult: string;

  constructor(
    private oidcSecurityService: OidcSecurityService,
    private http: HttpClient, private router: Router) {
    this.isAuthorized = false;
  }

  ngOnInit() {
    this.isAuthorizedSubscription = this.oidcSecurityService
      .getIsAuthorized()
      .subscribe(isAuthorized => {
        this.isAuthorized = isAuthorized;
        if (!isAuthorized) {
          this.oidcSecurityService.authorize();
        } else {
          return this.router.navigate(['dashboard']);
        }
      });
  }
  ngOnDestroy() {
    this.isAuthorizedSubscription.unsubscribe();
  }

  signOut() {
    this.oidcSecurityService.logoff();
  }
}

