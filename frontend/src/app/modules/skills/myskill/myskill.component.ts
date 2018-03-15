import { Component, OnInit } from '@angular/core';
import { EmployeeSkill } from '../../../model/EmployeeSkill';
import { MySkillService } from '../../../services/myskillservice.service';
import {GetDataPipe} from './extractdata.pipe';

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

  constructor(private dataService: MySkillService) {

  }

  public Valid(isValid: string) {

    const x = document.getElementById(isValid);
    x.hidden = !(x.hidden);
    console.log(isValid);
    const y = document.getElementById(this.updateButton.concat(isValid));
    y.hidden = !(y.hidden);
    this.activeId = isValid;

  }

  OnRatingUpdated(newEmployeeSkillRated: EmployeeSkill): void {


    console.log('Run the post query to the Server with the data recieved' +
      'call the service again with the updated data');
    console.log('Data recieved' + JSON.stringify(newEmployeeSkillRated));
    console.log('Emnployee ID' + newEmployeeSkillRated.employeeId);
    newEmployeeSkillRated.lastModified = new Date();
    if (newEmployeeSkillRated.employeeId) {
        this.dataService.saveEmployeeSkill(newEmployeeSkillRated)
            .subscribe(
                () => console.log('Product Passed to savefunction'),
                (error: any) => this.errorMessage = <any>error
            );
            // this.getEmployeeSkill();
            console.log('NO Error in ifss');
      } else {
            this.errorMessage = 'Invalid Id';
            console.log('Employee Id missing cannot run query');
      }

    // this.onCanceledClicked(ne);
    // console.log(this.updateButton.concat(newEmployeeSkillRated.subSkill.name.toString()));
    // const y = document.getElementById(this.updateButton.concat(newEmployeeSkillRated.subSkill.name.toString()));
    // y.hidden = !(y.hidden);
    // const x = document.getElementById(newEmployeeSkillRated.subSkill.name.toString());
    // x.hidden = !(x.hidden);
  }

  onCanceledClicked(toHideId: string): void {
    console.log(this.updateButton.concat(toHideId));
    const y = document.getElementById(this.updateButton.concat(toHideId));
    y.hidden = !(y.hidden);
    const x = document.getElementById(toHideId);
    x.hidden = !(x.hidden);
  }

 getEmployeeSkill() {
  this.dataService.getEmployeeSkills('101')
  .subscribe(employeeSkill => {
        this.employeeSkill = employeeSkill;
        console.log(this.employeeSkill);

      },
              error => this.errorMessage = <any>error);
}



  ngOnInit() {
      this.getEmployeeSkill();
  }
}
