import { Component, OnInit } from '@angular/core';
// import { EventService } from '../../../services/event.service';
import { Router } from '@angular/router';
import { TrainingSession } from '../../../model/training-sessions';

import { TrainingDomain } from '../../../model/training-domain';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { AvailableTrainingService } from '../../../services/availabletraining.service';


@Component({
  selector: 'app-available-trainings',
  templateUrl: './available-trainings.component.html',
  styleUrls: ['./available-trainings.component.css']
})
export class AvailableTrainingsComponent implements OnInit {
  trainingAvailable:TrainingDomain[]=[];
  errorMessage: any;
  date: Date;
  currMonth : number;
  currYear : number;
  month : number;
  year : number;
  closeResult: string;
  months: string[] = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", ];
  constructor(private availableTrainingService:AvailableTrainingService, private router: Router,
    private modalService: NgbModal) { }
  
trainingDomainToSend: TrainingDomain;

  
  ngOnInit() {
    console.log('initialized')
    this.availableTrainingService.getAvailableTraining()
      .subscribe(trainingAvailable => {
        this.trainingAvailable= trainingAvailable;
      },
        error => this.errorMessage = <any>error,
      ()=>console.log(this.trainingAvailable));
        

        this.date = new Date();
        this.month = this.date.getMonth();
        this.year = this.date.getFullYear();
        this.currMonth = this.date.getMonth();
        this.currYear = this.date.getFullYear();
      }

      
      previous(){
        this.month--;
        if(this.month==-1)
         {
           this.month=11;
           this.year--;
         }
         
      }
       
      next(){
        this.month++;
        if(this.month==12)
         {
           this.month=0;
           this.year++;
         }
      }
      
      enrollTraining(trainingId:string)
      {
        this.availableTrainingService.postEnroll(trainingId).subscribe(
          () => console.log('Certification Passed to Certification API'));
      }

      open(content , trainingdom: TrainingDomain) {
        this.trainingDomainToSend= trainingdom;
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
