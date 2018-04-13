import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { NewTraining } from '../../../model/New-Training';
import { TrainingDomain } from '../../../model/training-domain';
import { Validators } from '@angular/forms';
import { AddNewTrainingService } from '../../../services/addnewtraining.service';
import { Training } from '../../../model/Training';
import { TrainingSession } from '../../../model/training-sessions';



@Component({
  selector: 'app-add-training',
  templateUrl: './add-training.component.html',
  styleUrls: ['./add-training.component.css']
})
export class AddTrainingComponent implements OnInit {
  errorMessage: any;
  newTrainingForm: FormGroup;
   constructor(private newTrainingService: AddNewTrainingService , private fb: FormBuilder) { }

  ngOnInit() {
    this.newTrainingForm = this.fb.group({
      name : ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      location: ['', [Validators.required]],
      trainer: ['', [Validators.required]],
      seats: ['', [Validators.required]],
      type: ['', [Validators.required]],
      trainingSession: this.fb.array([this.buildDate()])
          });
  }

   buildDate() {
    return  this.fb.group({
      trainingDate: ['',[Validators.required]],
      startTime: ['',[Validators.required]],
      endTime: ['',[Validators.required]]
  }) ;
   }

   add() {
     const control = <FormArray>this.newTrainingForm.controls['trainingSession'];
     control.push(this.buildDate());
  }

  remove(i: number) {
    const control = <FormArray>this.newTrainingForm.controls['trainingSession'];
    control.removeAt(i);
  }

  save() {
    console.log(this.newTrainingForm);
    console.log(JSON.stringify(this.newTrainingForm.value));
    this.saveTraining(this.newTrainingForm);
  }


  saveTraining(trainingForwarded: FormGroup) {
    const training = new NewTraining('', trainingForwarded.value.name, trainingForwarded.value.description, trainingForwarded.value.location, trainingForwarded.value.trainer, trainingForwarded.value.seats, trainingForwarded.value.type);
    const newTraining = new TrainingDomain();
    newTraining.training = training;
    newTraining.trainingSessions = trainingForwarded.value.trainingSession;

         this.newTrainingService.saveNewTraining(newTraining)
        .subscribe(
              () => console.log('Training Passed to Training API'),
              (error: any) => this.errorMessage = <any>error
        );

    // window.location.reload();
  }

  clear() {
    this.newTrainingForm.reset();
  }

}
