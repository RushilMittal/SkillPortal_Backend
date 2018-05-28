import { Component, OnInit } from '@angular/core';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
@Component({
  selector: 'app-certification-navigation',
  templateUrl: './certification-navigation.component.html',
  styleUrls: ['./certification-navigation.component.css']
})
export class CertificationNavigationComponent implements OnInit {
  subject = 'Skill Portal:- Kindly Add the new certificate in the Skill Portal';
  constructor( private modalService: NgbModal) { }

  ngOnInit() {
  }
  closeResult: string;
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
      return  `with: ${reason}`;
    }
  }
}
