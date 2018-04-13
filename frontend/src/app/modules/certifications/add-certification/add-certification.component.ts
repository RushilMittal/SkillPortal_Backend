import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Certification } from '../../../model/Certification';
import { EmployeeCertificate } from '../../../model/EmployeeCertification';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AddNewCertificateService } from '../../../services/addnewcertificate.service';


@Component({
  selector: 'app-add-certification',
  templateUrl: './add-certification.component.html',
  styleUrls: ['./add-certification.component.css']
})
export class AddCertificationComponent implements OnInit {
  @Input() certificationReceived: Certification;
  @Input() certificationName: string;
  @Output() cancelClicked: EventEmitter<string> = new EventEmitter<string>();
  showValid = false;


  // @Output() cancelClicked: EventEmitter<string> = new EventEmitter<string>();

  certificationToAdd: EmployeeCertificate = new EmployeeCertificate();
  certificateForm: FormGroup;
  certificationValidityDate: Date;
  certificationDate: Date;
  certificateNumber: number;
  certificateUrl: string;

  errorMessage: any;

  constructor(private addNewCertificateService: AddNewCertificateService) { }




  save() {
    // console.log("Inside the add certification: " + this.certificationReceived.id);
    // console.log("Inside the add certification: " + this.certificationReceived.institution);
    // console.log("Inside the add certification: " + this.certificationReceived.skillId);
    // Print whole data and call the service to save all the certificate data..
    const tempEmployeeId = '101';
    this.certificationToAdd.certificationId.id = this.certificationReceived.id;
    this.certificationToAdd.certificationId.certificationName = this.certificationReceived.certificationName;
    this.certificationToAdd.certificationId.institution = this.certificationReceived.institution;
    this.certificationToAdd.certificationId.skillId = this.certificationReceived.skillId;

    this.certificationToAdd.empId = '101';
    this.certificationToAdd.certificationValidityDate = this.certificateForm.get('certificationValidityDate').value;
    this.certificationToAdd.certificationDate = this.certificateForm.get('certificationDate').value;
    this.certificationToAdd.certificationNumber = this.certificateForm.get('certificationNumber').value;
    this.certificationToAdd.certificationUrl = this.certificateForm.get('certificationUrl').value;

    // console.log('Data with temp id saved to Object');
    // Calling serivce method and passing the info.
      this.addNewCertificateService.saveNewCertification(this.certificationToAdd)
      .subscribe(
        () => console.log('Certification Passed to Certification API'),
        (error: any) => this.errorMessage = <any>error);

        this.cancel();
  }

  cancel() {
    // this.cancelClicked.emit(this.certificationToAdd.);
    this.cancelClicked.emit(this.certificationName);
  }

  ngOnInit() {

    this.certificateForm = new FormGroup({
      certificationDate: new FormControl(),
      certificationValidityPeriod: new FormControl(),
      certificationValidityDate: new FormControl(),
      certificationNumber: new FormControl(),
      certificationUrl: new FormControl()
    });

  }





  setValidity(validity: string): void {
    const validPeriod = this.certificateForm.get('certificationValidityDate');
    if (validity === 'Yes') {
      validPeriod.setValidators(Validators.required);
      validPeriod.enable();
      this.showValid = true;
    } else if (validity === 'No') {
      validPeriod.clearValidators();
      validPeriod.disable();
      this.showValid = false;

    }

    validPeriod.updateValueAndValidity();

  }





}
