import { Component, OnInit } from '@angular/core';
// import { EventService } from '../../../services/event.service';
import { Router } from '@angular/router';
import { TrainingSession } from '../../../model/training-sessions';
import { AvailableTrainingService } from '../../../services/availabletraining.service';
import { TrainingDomain } from '../../../model/training-domain';


@Component({
  selector: 'app-available-trainings',
  templateUrl: './available-trainings.component.html',
  styleUrls: ['./available-trainings.component.css']
})
export class AvailableTrainingsComponent implements OnInit {
  trainingAvailable:TrainingDomain[];
  errorMessage: any;
  date: Date;
  currMonth : number;
  currYear : number;
  month : number;
  year : number;
  months: string[] = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", ];
  constructor(private availableTrainingService:AvailableTrainingService, private router: Router) { }
  
trainingDomain: TrainingDomain[];

  
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
         console.log(this.trainingAvailable)
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


}







// import { Component, OnInit } from '@angular/core';
// import { EventService } from '../../../services/event.service';
// import { Router } from '@angular/router';

// @Component({
//   selector: 'app-training-list',
//   templateUrl: './training-list.component.html',
//   styleUrls: ['./training-list.component.css']
// })
// export class TrainingListComponent implements OnInit {
  



// }
