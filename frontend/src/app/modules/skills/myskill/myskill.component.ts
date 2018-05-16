import { Component, OnInit, Renderer2 } from '@angular/core';

import { MySkillService } from '../../../services/myskillservice.service';

import { EmployeeSkill } from '../../../model/EmployeeSkill';
import { SubSkill } from '../../../model/SubSkill';

import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-myskill',
  templateUrl: './myskill.component.html',
  styleUrls: ['./myskill.component.css']
})


export class MyskillComponent implements OnInit {
  errorMessage: any;
  employeeSkill: EmployeeSkill[];
  updateButton = 'update-button';
  activeId: string;
  buttonNotClicked = true;
  activeTags = [];
  showSpinner = false;

  constructor(private dataService: MySkillService, private toastService: ToastService) { }

  ngOnInit() {
    this.getEmployeeSkill();
  }

  toggle(param: string) {
    this.buttonNotClicked = !this.buttonNotClicked;
    if (this.activeTags.includes(param)) {
      const index = this.activeTags.indexOf(param, 0);
      if (index > -1) {
        this.activeTags.splice(index, 1);
      }
    } else {
      this.activeTags.push(param);
    }

  }

  onCanceledClicked(toHideId: string): void {
    // console.log(this.updateButton.concat(toHideId));
    const y = document.getElementById(this.updateButton.concat(toHideId));
    y.hidden = !(y.hidden);
    const x = document.getElementById(toHideId);
    x.hidden = !(x.hidden);
  }
  public Valid(isValid: string) {

    const x = document.getElementById(isValid);
    x.hidden = !(x.hidden);
    // console.log(isValid);
    const y = document.getElementById(this.updateButton.concat(isValid));
    y.hidden = !(y.hidden);
    this.activeId = isValid;

  }

  OnRatingUpdated(newEmployeeSkillRated: EmployeeSkill): void {
    newEmployeeSkillRated.lastModifiedDate = new Date();
    if (newEmployeeSkillRated.employeeId) {
      this.dataService.saveEmployeeSkill(newEmployeeSkillRated)
        .subscribe(
        () => console.log('Product Passed to savefunction'),
        (error: any) => {
          this.errorMessage = <any>error;
          this.toastService.showErrorToast("Unable to Save Some Error Occured");
        },
        () => {
          this.toastService.showSuccessToast("Skill Updated SuccessFully");
        }
        );

    } else {
      this.errorMessage = 'Invalid Id';
    }
  }



  getEmployeeSkill() {
    this.showSpinner = true;
    this.dataService.getEmployeeSkills()
      .subscribe(employeeSkillResponse => {
        this.employeeSkill = employeeSkillResponse.body;
        this.errorMessage = 'Hurry Up! Rate your First Skill';
      },
      error => {
        this.errorMessage = <any>error;
        this.showSpinner = false;
      },
      () => {
        this.showSpinner = false
      }
      );

  }
}
