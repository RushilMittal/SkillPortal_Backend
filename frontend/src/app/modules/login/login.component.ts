import { Component, OnInit, OnDestroy, Output } from '@angular/core';
// import { EmployeeAuthorizationService } from '../../services/employee-authorization.service';
import { Router } from '@angular/router';
// tslint:disable-next-line:import-blacklist
import { Subscription, Observable } from 'rxjs';

import { HttpClient } from '@angular/common/http';
import { AuthHelper } from '../../services/authHelper.service';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  constructor(private authHelperService: AuthHelper,
    private router: Router,
    private employeeDetailService: EmployeeService) {

  }

  ngOnInit() {
    //Call authhelper, determine if user is logged in, if not redirect to login
    if (this.authHelperService.isOnline()) {
      this.router.navigate(['/dashboard']);
    } else {
      this.login();
      
    }
  }

  login() {
    this.authHelperService.login();
  }
}

