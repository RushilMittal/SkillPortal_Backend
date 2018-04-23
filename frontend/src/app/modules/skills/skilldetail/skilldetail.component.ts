import { Component, OnInit } from '@angular/core';

import { AllSubSkillService } from '../../../services/allsubskillservice.service';
import { ActivatedRoute, Router } from '@angular/router';

import { Skill } from '../../../model/Skill';
import { MySubSkillService } from '../../../services/mysubskillservice.service';
import { NullTemplateVisitor } from '@angular/compiler';
import { EmployeeSkill } from '../../../model/EmployeeSkill';
import { MySkillService } from '../../../services/myskillservice.service';
import { SubSkill } from '../../../model/SubSkill';

@Component({
  selector: 'app-skilldetail',
  templateUrl: './skilldetail.component.html',
  styleUrls: ['./skilldetail.component.css']
})
export class SkilldetailComponent implements OnInit {
  activeTags = [];
  buttonNotClicked = true;
  skillName: string;
  skill: string;
  subSkillList: SubSkill[];
  employeeSkillList = [];
  errorMessage: any;
  showSpinner = true;
  constructor(private mySubSkillService: MySubSkillService,
    private route: ActivatedRoute,
    private mySkillService: MySkillService,
    private router: Router
  ) { }
  ngOnInit() {
    this.route.params.subscribe(
      params => {
        const stringToSplit = params['id'];
        let x;
        if (stringToSplit !== undefined) {
        x = stringToSplit.split('_');
        }
       this.skill = x[1];
       this.skillName = params['id'];
      });

      this.mySubSkillService.getEmployeeSubSkillExceptRatedSubSkill(this.skillName)
          .subscribe( subskill => {
            this.subSkillList = subskill;
            },
          error => this.errorMessage = <any>error,
          () => this.createEmployeeSkillList());
  }
  createEmployeeSkillList() {
    this.showSpinner = false;
    console.log('create EmployeeSkill');

      for (const subskill of this.subSkillList) {
        const employeeSkill = new EmployeeSkill();
          
          employeeSkill.subSkill = subskill;
          employeeSkill.rating = 0;
          employeeSkill.lastModified = new Date();

          this.employeeSkillList.push(employeeSkill);

      }

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

  OnRatingUpdated(newEmployeeSkillRated: EmployeeSkill): void {

console.log("onrating updated inn parent");
   
    newEmployeeSkillRated.lastModified = new Date();
    if (newEmployeeSkillRated.rating) {
        this.mySkillService.saveEmployeeSkill(newEmployeeSkillRated)
            .subscribe(
                () => console.log('Product Passed to savefunction'),
                (error: any) => this.errorMessage = <any>error,
                ()=> this.onRefresh()
            );
            
   
      } else {
            this.errorMessage = 'Invalid Rating';
            console.log(this.errorMessage);
   
      }

   
  }

  // Can be used to refresh the component to same page
  onRefresh() {
    this.router.routeReuseStrategy.shouldReuseRoute = function() {return false; };

    const currentUrl = this.router.url + '?';

    this.router.navigateByUrl(currentUrl)
      .then(() => {
        this.router.navigated = false;
        this.router.navigate([this.router.url]);
      });
    }
}
