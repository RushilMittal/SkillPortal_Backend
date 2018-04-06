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
   year: number ;
   month: number;
   day: number;

   skillPlaceholder: EmployeeSkillPlaceholder = new EmployeeSkillPlaceholder();

   errorMessage: any;
  constructor(private skillPlaceHolderService: DashBoardSkillPlaceHolderService) {

  }

  ngOnInit() {
    const employeeId = '101';
    this.skillPlaceHolderService.getemployeeSkillPlaceholder(employeeId)
        .subscribe(skillPlaceholder => {
            this.skillPlaceholder = skillPlaceholder,
            this.year = this.skillPlaceholder.lastUpdatedPeriod[0],
            this.month = this.skillPlaceholder.lastUpdatedPeriod[1],
            this.day = this.skillPlaceholder.lastUpdatedPeriod[2],
            // tslint:disable-next-line:no-unused-expressionnpm start
            // tslint:disable-next-line:no-unused-expression
            error => this.errorMessage = <any>error;
        });
        // console.log('SkillPlaceholder ' + JSON.stringify(this.skillPlaceholder));
//     //fetching total number of skills of the employee
//  this.skillPlaceHolderService.getNumberOfTotalRatedSkill()
//         .subscribe(totalRated =>{
//             this.totalRated = totalRated,
//             error => this.errorMessage =<any>error
//         });
//         console.log("Total NUmber of Skill Rated" + this.totalRated);


//     //fetching the highest rated skill of the employee
//     this.skillPlaceHolderService.getHighestRatedSkillOfEmployee()
//         .subscribe(skill =>{
//             this.highestRatedSkill = skill,
//             console.log("rating in component" + this.highestRatedSkill),
//             error => this.errorMessage = <any>error
//         });

//     console.log("Higest Rated" + this.highestRatedSkill);

//     //fetching the rating of the highest rated skill of employee.
//       this.skillPlaceHolderService.getHighestRatingofEmployeeSkill()
//           .subscribe(rating =>{
//             this.highestRating = rating,
//             console.log("rating in component" + this.highestRatedSkill),
//             error => this.errorMessage = <any>error

//           });

//    // Fetching the  period since when employee has rated/updated one's skill
//         this.skillPlaceHolderService.getLastUpdatedPeriodofEmployee()
//         .subscribe(period =>{
//             this.period = period,
//             this.year = this.period[0],
//             this.month =this.period[1],
//             this.day = this.period[2],
//             console.log("update period" + this.period),
//             error => this.errorMessage =<any>error


//         });



  }



}
