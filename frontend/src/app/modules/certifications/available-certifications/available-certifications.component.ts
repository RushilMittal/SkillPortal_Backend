import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AllCertificationService } from '../../../services/allcertification.service';
import { Certification } from '../../../model/Certification';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { EmployeeService } from '../../../services/employee.service';

@Component({
  selector: 'app-available-certifications',
  templateUrl: './available-certifications.component.html',
  styleUrls: ['./available-certifications.component.css']
})

export class AvailableCertificationsComponent implements OnInit {

  certifications: Certification[];
  errorMessage: any;
  addCertificate: String = 'add-certificate';
  showSpinner = false;
  activeId: string;
  closeResult: string;
  
  constructor(private allCertificationService: AllCertificationService,
    private modalService: NgbModal,
    private router: Router,
    private employeeDetailService:EmployeeService) { 
      this.getAllCertificate();

    }

  ngOnInit() {

   

  }

  isadmin():boolean {
    if (this.employeeDetailService.checkRoleAdmin()){
      return true;
    }
    return false;
  }
  
  open(content) {
    this.modalService.open(content).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
      console.log(this.closeResult);
      this.onRefresh();
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  // Can be used to refresh the component to same page
  onRefresh() {
    this.router.routeReuseStrategy.shouldReuseRoute = function () { return false; };

    const currentUrl = this.router.url + '?';

    this.router.navigateByUrl(currentUrl)
      .then(() => {
        this.router.navigated = false;
        this.router.navigate([this.router.url]);
      });
    this.getAllCertificate();
  }

  public Valid(isValid: string) {
    // console.log('is valid' + isValid);
    const x = document.getElementById(isValid);
    // console.log('id recieved' + x);
    x.hidden = !(x.hidden);
    // console.log(isValid);
    const y = document.getElementById(this.addCertificate.concat(isValid));
    y.hidden = !(y.hidden);
    this.activeId = isValid;

  }

  onCanceledClicked(toHideId: string): void {
    // console.log(this.addCertificate.concat(toHideId));
    const y = document.getElementById(this.addCertificate.concat(toHideId));
    y.hidden = !(y.hidden);
    const x = document.getElementById(toHideId);
    x.hidden = !(x.hidden);
  }

  getAllCertificate() {

    this.showSpinner = true;
    this.allCertificationService.getAllCertificates()
      .subscribe(
        certifications => {
          this.certifications = certifications;
          this.errorMessage = 'Sorry, No New Certifications Available';
        },
        error => {
          this.errorMessage = error;
          this.showSpinner = false;
        },
        () => {
          this.showSpinner = false
        });
  }


}
