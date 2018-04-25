import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Certification } from '../../../model/Certification';
import { EmployeeCertificate } from '../../../model/EmployeeCertification';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AddNewCertificateService } from '../../../services/addnewcertificate.service';
import { ToastService } from '../../../services/toast.service';


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

  constructor(private addNewCertificateService: AddNewCertificateService,
    private toastService: ToastService) { }

  ngOnInit() {

    this.certificateForm = new FormGroup({
      certificationDate: new FormControl(),
      certificationValidityPeriod: new FormControl(),
      certificationValidityDate: new FormControl(),
      certificationNumber: new FormControl(),
      certificationUrl: new FormControl()
    });

  }



  save() {

    this.certificationToAdd.certificationId.id = this.certificationReceived.id;
    this.certificationToAdd.certificationId.certificationName = this.certificationReceived.certificationName;
    this.certificationToAdd.certificationId.institution = this.certificationReceived.institution;
    this.certificationToAdd.certificationId.skillId = this.certificationReceived.skillId;
    this.certificationToAdd.certificationValidityDate = this.certificateForm.get('certificationValidityDate').value;
    this.certificationToAdd.certificationDate = this.certificateForm.get('certificationDate').value;
    this.certificationToAdd.certificationNumber = this.certificateForm.get('certificationNumber').value;
    this.certificationToAdd.certificationUrl = this.certificateForm.get('certificationUrl').value;

    this.addNewCertificateService.saveNewCertification(this.certificationToAdd)
      .subscribe(
        ()=>{},
        (error: any) => {
          this.errorMessage = <any>error;
          this.toastService.showErrorToast("Unable to Save Some Error Occured");
        },
        () => {
          this.toastService.showSuccessToast("Certification Saved SuccessFully");
        }
      );
    this.cancel();
  }

  cancel() {
    this.cancelClicked.emit(this.certificationName);
	this.certificateForm.reset();
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
