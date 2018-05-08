import { Component, OnInit, OnDestroy, Output } from '@angular/core';
// import { EmployeeAuthorizationService } from '../../services/employee-authorization.service';
import { Router } from '@angular/router';
// tslint:disable-next-line:import-blacklist
import { Subscription, Observable } from 'rxjs';

import { HttpClient } from '@angular/common/http';
import { AuthHelper } from '../../services/authHelper.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  

  constructor(private authHelperService: AuthHelper, private router: Router) {
    
  }

  ngOnInit() {
    //Call authhelper, determine if user is logged in, if not redirect to login
    if (this.authHelperService.isOnline()) {
      console.log("Inside the login true");
      this.router.navigate(['/dashboard']);
    } else {
      console.log("Inside the login false");
      this.login();
    }
  }
  
  login() {
    this.authHelperService.login();
  }


}

