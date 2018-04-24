import { Component, OnInit } from '@angular/core';
import {  DashboardCertificationPlaceholderService } from '../../../services/dashboardcertificationplaceholder.service';
import { EmployeeCertificatePlaceholderModel } from '../../../model/EmployeeCertificatePlaceholderModel';

@Component({
  selector: 'app-certification-placeholder',
  templateUrl: './certification-placeholder.component.html',
  styleUrls: ['./certification-placeholder.component.css']
})
export class CertificationPlaceholderComponent implements OnInit {
  employeeCertificatePlaceholderModel: EmployeeCertificatePlaceholderModel[];
  employeeId = '101';
  title = 'app';
  subtitle1 = 'Certifications Received';
  subtitle2 = 'Certifications Requested';

  errorMessage: any;
  constructor(private certificationPlaceholderService: DashboardCertificationPlaceholderService) { }
  ngOnInit() {
    // console.log('In ngOnInit');
    this.certificationPlaceholderService.getCertificatePlaceholder(this.employeeId)
      .subscribe(employeecertificatevar => {
        this.employeeCertificatePlaceholderModel = employeecertificatevar
        this.errorMessage = 'Hurry Up! Upload your First Certification';
      },
        (error: any) => {
          this.errorMessage = <any>error;
        },
        () => { }
    );


  }

  }


