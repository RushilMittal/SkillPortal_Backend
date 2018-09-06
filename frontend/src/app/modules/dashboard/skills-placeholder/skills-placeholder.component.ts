import { Component, OnInit } from '@angular/core';
import { DashboardLayoutComponent } from '../dashboard-layout/dashboard-layout.component';

import { EmployeeSkillPlaceholder } from '../../../model/EmployeeSkillPlaceholder';
import { DashBoardSkillPlaceHolderService } from '../../../services/dashboardskillplaceholder.service';

@Component({
  selector: 'app-skills-placeholder',
  templateUrl: './skills-placeholder.component.html',
  styleUrls: ['./skills-placeholder.component.css']
})
export class SkillsPlaceholderComponent implements OnInit {
  //    highestRatedSkill: String;
  //    totalRated: number;
  //    highestRating:number;
  //    period: number[];
  year: number;
  month: number;
  day: number;

  skillPlaceholder: EmployeeSkillPlaceholder = new EmployeeSkillPlaceholder();

  errorMessage: any;
  constructor(private skillPlaceHolderService: DashBoardSkillPlaceHolderService) {

  }

  ngOnInit() {

    this.skillPlaceHolderService.getemployeeSkillPlaceholder()
      .subscribe(skillPlaceholder => {
        this.skillPlaceholder = skillPlaceholder;
        if (this.skillPlaceholder) {
          this.year = this.skillPlaceholder.lastUpdatedPeriod[0];
          this.month = this.skillPlaceholder.lastUpdatedPeriod[1];
          this.day = this.skillPlaceholder.lastUpdatedPeriod[2];
        }
        this.errorMessage = 'Hurry Up! Rate your first modelSkill';
      },
      (error: any) => {
        this.errorMessage = <any>error;
      },
      () => { }
      );


  }



}
