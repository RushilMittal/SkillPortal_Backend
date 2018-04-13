import { Component, OnInit } from '@angular/core';
import { DashboardTrainingPlaceholder } from '../../../services/dashboardtrainingplaceholder.service';
import { Training } from '../../../model/Training';


@Component({
  selector: 'app-training-placeholder',
  templateUrl: './training-placeholder.component.html',
  styleUrls: ['./training-placeholder.component.css']
})
export class TrainingPlaceholderComponent implements OnInit {
  trainingPlaceholder: Training[];
  enrolledTraining: String;
  enrolledTrainingDate: String;
  errorMessage: any;
  subtitle1 = 'Enrolled trainings';
  subtitle2 = 'Upcoming trainings';
  constructor(private trainingService: DashboardTrainingPlaceholder) { }

  ngOnInit() {
    this.trainingService.gettTrainingData().
        subscribe(traningData => {
            this.trainingPlaceholder = traningData;
            this.enrolledTraining = this.trainingPlaceholder[0].enrolledTrainings,
            this.enrolledTrainingDate = this.trainingPlaceholder[0].enrolledTrainingDate,
            // tslint:disable-next-line:no-unused-expression
            error => this.errorMessage = <any>error;
        });
        // console.log('Training Data received ' + this.trainingPlaceholder );

        // for(let tp of this.trainingPlaceholder){
        //  this.enrolledTraining = tp.enrolledTrainings;
        //  this.enrolledTrainingDate = tp.enrolledTrainingDate;
        // }



  }

}