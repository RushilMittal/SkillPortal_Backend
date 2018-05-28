import { Component, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { AddTrainingComponent } from '../add-training/add-training.component';

@Component({
  selector: 'app-trainings-navigation',
  templateUrl: './trainings-navigation.component.html',
  styleUrls: ['./trainings-navigation.component.css']
})
export class TrainingsNavigationComponent implements OnInit {
  offerTraningModal =false;
  styles = 'none';
  closeResult: string;
  subject = 'Skill Portal:- Kindly Arrange the training';
  

  constructor(private modalService: NgbModal) { }

  ngOnInit() {
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
      return  `with: ${reason}`;
    }
  }
}
