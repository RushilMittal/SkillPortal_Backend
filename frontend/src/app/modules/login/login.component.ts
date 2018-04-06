import { Component, OnInit } from '@angular/core';
import { EmployeeAuthorizationService } from '../../services/employee-authorization.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: string;
  password: string;
  constructor(private employeeAuthorization: EmployeeAuthorizationService,
              private router: Router) { }

  ngOnInit() {
  }

  check() {
    if (this.email === 'user' && this.password === 'user') {
      this.employeeAuthorization.changeStatus(true);

      if (this.employeeAuthorization.redirectUrl) {
        this.router.navigate([this.employeeAuthorization.redirectUrl]);
      } else {
        this.router.navigate(['dashboard']);
      }
    } else {
      console.log('User id not matched');
    }
  }

}
