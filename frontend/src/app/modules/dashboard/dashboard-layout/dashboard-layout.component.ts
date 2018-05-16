import { Component, OnInit } from '@angular/core';
import { AuthHelper } from '../../../services/authHelper.service';
import { EmployeeService } from '../../../services/employee.service';
import { EmployeeDetails } from '../../../model/EmployeeDetail';

@Component({
  selector: 'app-dashboard-layout',
  templateUrl: './dashboard-layout.component.html',
  styleUrls: ['./dashboard-layout.component.css']
})
export class DashboardLayoutComponent implements OnInit {

  constructor(private employeeDetailService: EmployeeService,
    private authHelperService: AuthHelper) {
  }

  ngOnInit() {
  }

  
}
