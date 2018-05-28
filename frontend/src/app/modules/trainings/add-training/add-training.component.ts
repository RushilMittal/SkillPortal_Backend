import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormControl, FormArray, FormBuilder } from '@angular/forms';
import { NewTraining } from '../../../model/New-Training';
import { TrainingDomain } from '../../../model/training-domain';
import { Validators } from '@angular/forms';
import { AddNewTrainingService } from '../../../services/addnewtraining.service';
import { Training } from '../../../model/Training';
import { TrainingSession } from '../../../model/training-sessions';
import { ToastService } from '../../../services/toast.service';
import { EmployeeService } from '../../../services/employee.service';

@Component({
  selector: 'app-add-training',
  templateUrl: './add-training.component.html',
  styleUrls: ['./add-training.component.css']
})

export class AddTrainingComponent implements OnInit {
  @Input() trainingDomain: TrainingDomain;
  errorMessage: any;
  dt: Date;
  newTrainingForm: FormGroup;
  seatPattern = "[-+]?[0-9]+";
  namePattern = "^[a-zA-Z]{1,20}$";
  @Output() saveClicked: EventEmitter<void> = new EventEmitter<void>();
  constructor(private newTrainingService: AddNewTrainingService,
    private fb: FormBuilder,
    private toastService: ToastService,
    private employeeService:EmployeeService) { }


  ngOnInit() {

    if (this.trainingDomain != undefined) {
      console.log(this.trainingDomain);
      // this.newTrainingForm.patchValue(this.trainingDomain.training.name)
      this.newTrainingForm = this.fb.group({
        name: [this.trainingDomain.training.name, [Validators.required, Validators.minLength(3)]],
        description: [this.trainingDomain.training.description, [Validators.required, Validators.minLength(10)]],
        location: [this.trainingDomain.training.location, [Validators.required]],
        trainer: [this.trainingDomain.training.trainer, [Validators.required, Validators.pattern(this.namePattern)]],
        seats: [this.trainingDomain.training.seats, [Validators.required, Validators.pattern(this.seatPattern)]],
        type: [this.trainingDomain.training.type, [Validators.required]],
        trainingSession: this.fb.array([this.buildDate()])
      });
      this.setSessions(this.trainingDomain.trainingSessions);

    }

    else {
      this.newTrainingForm = this.fb.group({
        name: ['', [Validators.required, Validators.minLength(3)]],
        description: ['', [Validators.required, Validators.minLength(10)]],
        location: ['', [Validators.required]],
        trainer: ['', [Validators.required, Validators.pattern(this.namePattern)]],
        seats: ['', [Validators.required, Validators.pattern(this.seatPattern)]],
        type: ['', [Validators.required]],
        trainingSession: this.fb.array([this.buildDate()])
      });
    }
  }



  currentDate(dt: Date) {
    let dd = '';
    let mm = '';//January is 0!  
    const yyyy = dt.getFullYear();
    if (dt.getDate() < 10) {
      dd = '0' + dt.getDate();
    }
    else {
      dd = '' + dt.getDate();
    }
    if (dt.getMonth() < 9) {
      mm = '0' + (dt.getMonth() + 1);
    }
    else {
      mm = '' + (dt.getMonth() + 1);
    }
    const today = yyyy + '-' + mm + '-' + dd;
    return today;
  }


  buildDate() {
    if (this.trainingDomain == undefined) {
      return this.fb.group({
        trainingDate: [, [Validators.required]],
        startTime: ['', [Validators.required]],
        endTime: ['', [Validators.required]]
      });
    }

    else {
      return this.fb.group({
        trainingId: [this.trainingDomain.training.id],
        trainingDate: [, [Validators.required]],
        startTime: ['', [Validators.required]],
        endTime: ['', [Validators.required]]
      });

    }
  }

  totime(value: string) {
    let a = value.substr(0, 2);
    a = a + value.substr(2, 3);
    console.log(a);
    let dt = new Date(a);
    return a;
  }
  setSessions(session: TrainingSession[]) {
    const sessionFGs = session.map(session => this.fb.group({
      trainingId: [this.trainingDomain.training.id],
      trainingDate: [this.currentDate(new Date(session.trainingDate)), [Validators.required]],
      startTime: [this.totime(session.startTime), [Validators.required]],
      endTime: [this.totime(session.endTime), [Validators.required]]

    }));
    console.log(this.currentDate(new Date(session[0].trainingDate)));
    const sessionFormArray = this.fb.array(sessionFGs);
    this.newTrainingForm.setControl('trainingSession', sessionFormArray);
  }

  add(event) {
    console.log("add trig");
    const control = <FormArray>this.newTrainingForm.controls['trainingSession'];
    control.push(this.buildDate());
  }

  remove(event, i: number) {
    const control = <FormArray>this.newTrainingForm.controls['trainingSession'];
    control.removeAt(i);
    //event.stopPropagation();
  }

  save() {
    // console.log(this.newTrainingForm);
    console.log(JSON.stringify(this.newTrainingForm.value));

    if (this.trainingDomain == undefined) {
      this.saveTraining(this.newTrainingForm);
      this.saveClicked.emit();
    } else {
      this.updateTraining(this.newTrainingForm);
      this.saveClicked.emit();
    }
  }


  saveTraining(trainingForwarded: FormGroup) {
    let email = this.employeeService.employeeDetails.mail;
    const training = new NewTraining('', trainingForwarded.value.name, trainingForwarded.value.description, trainingForwarded.value.location, trainingForwarded.value.trainer, trainingForwarded.value.seats, trainingForwarded.value.type,email);
    const newTraining = new TrainingDomain();
    newTraining.training = training;
    newTraining.trainingSessions = trainingForwarded.value.trainingSession;

    this.newTrainingService.saveNewTraining(newTraining)
      .subscribe(
        () => console.log('Product Passed to savefunction'),
        (error: any) => {
          this.errorMessage = <any>error;
          this.toastService.showErrorToast("Unable to Save, Some Error Occured");
        },

        () => {
          this.toastService.showSuccessToast("Training Saved Successfully");
        }
      );

    // window.location.reload();
  }

  updateTraining(trainingForwarded: FormGroup) {
    //console.log(trainingForwarded.value.trainingId)
    let email = this.employeeService.employeeDetails.mail;
    const training = new NewTraining(this.trainingDomain.training.id, trainingForwarded.value.name, trainingForwarded.value.description, trainingForwarded.value.location, trainingForwarded.value.trainer, trainingForwarded.value.seats, trainingForwarded.value.type,email);
    // console.log("hwerthfehu"+JSON.stringify(training));
    const newTraining = new TrainingDomain();
    newTraining.training = training;
    //console.log(trainingForwarded.value.trainingSession);
    newTraining.trainingSessions = trainingForwarded.value.trainingSession;

    this.newTrainingService.updateNewTraining(newTraining)
      .subscribe(
        () => console.log('Product Passed to savefunction'),
        (error: any) => {
          this.errorMessage = <any>error;
          this.toastService.showErrorToast("Unable to Save, Some Error Occured");
        },

        () => {
          this.toastService.showSuccessToast("Training Updated SuccessFfully");
        }
      );
  }

  clear() {
    this.newTrainingForm.reset();
  }

}
