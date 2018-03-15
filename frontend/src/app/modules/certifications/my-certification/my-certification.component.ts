import { Component, OnInit } from '@angular/core';
import {MyCertificationService} from '../../../services/mycertification.service';
import {Certification} from '../../../model/Certification';
import {EmployeeCertificate} from '../../../model/EmployeeCertification';

@Component({
  selector: 'app-my-certification',
  templateUrl: './my-certification.component.html',
  styleUrls: ['./my-certification.component.css']
})
export class MyCertificationComponent implements OnInit {
  certification: Certification;
  empCertification: EmployeeCertificate;
  constructor( private myCertificationService: MyCertificationService) { }

  ngOnInit() {
    this.myCertificationService.getEmployeeCertification().subscribe(
      empCertification => {this.empCertification = empCertification;
      console.log(this.empCertification); });
    }
  }
