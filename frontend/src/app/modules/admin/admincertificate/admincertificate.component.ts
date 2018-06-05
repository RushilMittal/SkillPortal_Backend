import { Component, OnInit } from '@angular/core';
import { Certification } from '../../../model/Certification';
import { AllCertificationService } from '../../../services/allcertification.service';
import { ToastService } from '../../../services/toast.service';
import { NewCertificationService } from '../../../services/newcertification.service';
import { AdminServices } from '../../../services/adminService';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-admincertificate',
  templateUrl: './admincertificate.component.html',
  styleUrls: ['./admincertificate.component.css']
})
export class AdmincertificateComponent implements OnInit {
  certifications: Certification[];
  errorMessage: any;
  settings = {
    actions: {
      delete: false
    },
    add: {
      confirmCreate: true,
    },
    edit: {
      confirmSave: true,
    },
    columns: {
      
      skillId: {
        title: 'Sub Skill'
      },
      certificationName: {
        title: 'Certification Name'
      },
      institution: {
        title: 'Institution'
      }

    },
    pager: {
      display: true,
      perPage: 10
    }
  };
  closeResult: string;
  constructor(private allCertificationService: AllCertificationService,
    private toastService: ToastService,
    private newCertificationService: NewCertificationService,
    private adminService: AdminServices,
    private modalService: NgbModal) { }

  ngOnInit() {
    this.getAllCertificate();
  }

  open(content) {
    this.modalService.open(content).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
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

  getAllCertificate() {
    this.allCertificationService.getAllCertificates()
      .subscribe(
        certifications => {
          this.certifications = certifications;
          this.errorMessage = 'Sorry, No New Certifications Available';
        },
        error => {
          this.errorMessage = error;

        },
        () => {

        });
  }

  /*
  * Method called on successfull update on the table.
  * will call the rest api for updating the certificate.
  */
  onSaveConfirm(event) {
    console.log(event.newData);

    if (this.validateData(event)) {
      console.log(event.newData['id']);
      this.adminService.UpdateCertificate(this.convertToSubskillObject(event.newData['id'], event.newData)).subscribe(
        () => console.log('Product Passed to savefunction'),
        (error: any) => {
          this.toastService.showErrorToast("Unable to Save Some Error Occured");
          event.confirm.reject();
        },
        () => {
          this.toastService.showSuccessToast("New Certification Added to the List");
          event.confirm.resolve(event.newData);
          this.getAllCertificate();
        });
    }
  }

  /*
  * Method called on create clicked on the table
  * Will call the service for adding the new Certificate to the list
  */
  onCreateConfirm(event) {
    if (this.validateData(event)) {
      let a = this.certifications.length + 1;
      console.log("on create" + a.toString());

      this.newCertificationService.saveNewCertification(this.convertToSubskillObject(a.toString(), event.newData)).subscribe(
        () => console.log('New Certification saved admin'),
        (error: any) => {
          this.toastService.showErrorToast("Unable to Save Some Error Occured");
          event.confirm.reject();
        },
        () => {
          this.toastService.showSuccessToast("New Certification Added to the List");
          event.confirm.resolve(event.newData);

          this.getAllCertificate();
        });
    }
  }

  //method used to validate the data received on save or edit
  validateData(event): boolean {
    if ((event.newData['skillId']) || (event.newData['certificationName']) || (event.newData['institution'])) {
      return true;
    }
    return false;
  }

  convertToSubskillObject(id: string, event): Certification {
    let toReturn = new Certification(id, event['skillId'], event['certificationName'], event['institution']);
    return toReturn;
  }


}
