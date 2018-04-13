import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AllCertificationService } from '../../../services/allcertification.service';
import { Certification } from '../../../model/Certification';

@Component({
  selector: 'app-available-certifications',
  templateUrl: './available-certifications.component.html',
  styleUrls: ['./available-certifications.component.css']
})

export class AvailableCertificationsComponent implements OnInit {

  certifications: Certification;
  errorMessage: any;
  addCertificate: String = 'add-certificate';
  showSpinner = false;
  activeId: string;

  constructor(private allCertificationService: AllCertificationService) { }

  // Page Refresh
  redirect() {
    window.location.reload();
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
      certifications => this.certifications = certifications,
      error => this.errorMessage = error,
      () => this.showSpinner = false
      );
         }

  ngOnInit() {

    this.getAllCertificate();

  }
}
