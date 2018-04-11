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
  skill:string;
  subSkillList :SubSkill[];
  employeeSkillList= [];
  // hard coded will be changed
  employeeId = '101';
  errorMessage: any;
  showSpinner =true;
  constructor(private mySubSkillService :MySubSkillService,
    private route:ActivatedRoute,
    private mySkillService :MySkillService,
    private router:Router
  ) { }
  ngOnInit() {
    this.route.params.subscribe(
      params => {
        let stringToSplit = params['id'];
        let x;
        if(stringToSplit!=undefined)
        {
        x = stringToSplit.split("_");
        }
       this.skill = x[1];
       this.skillName = params['id'];
      });
      
      this.mySubSkillService.getEmployeeSubSkillExceptRatedSubSkill(this.employeeId,this.skillName)
          .subscribe( subskill =>{
            this.subSkillList =subskill
            },
          error =>this.errorMessage = <any>error,
          () => this.createEmployeeSkillList());
  }
  createEmployeeSkillList(){
    this.showSpinner =false;
    console.log("create EmployeeSkill");
      
      for(let subskill of this.subSkillList){
        let employeeSkill = new EmployeeSkill();    
          employeeSkill.employeeId = this.employeeId;
          employeeSkill.subSkill = subskill;
          employeeSkill.rating = 0;
          employeeSkill.lastModified = new Date();
          
          this.employeeSkillList.push(employeeSkill);
          
      }
      
  }

 
  toggle(param: string) {
    this.buttonNotClicked = !this.buttonNotClicked;
    if (this.activeTags.includes(param)) {
      let index = this.activeTags.indexOf(param,0);
      if(index >-1){
        this.activeTags.splice(index,1);
      }
    } else {
      this.activeTags.push(param);
    }
    
    // console.log(this.activeTags);
  }

  OnRatingUpdated(newEmployeeSkillRated: EmployeeSkill): void {


    // console.log('Run the post query to the Server with the data recieved' +
    //   'call the service again with the updated data');
    // console.log('Data recieved' + JSON.stringify(newEmployeeSkillRated));
    // console.log('Emnployee ID' + newEmployeeSkillRated.employeeId);
    newEmployeeSkillRated.lastModified = new Date();
    if (newEmployeeSkillRated.employeeId) {
        this.mySkillService.saveEmployeeSkill(newEmployeeSkillRated)
            .subscribe(
                () => console.log('Product Passed to savefunction'),
                (error: any) => this.errorMessage = <any>error
            );
            this.onRefresh();
            // this.getEmployeeSkill();
            // console.log('NO Error in ifss');
      } else {
            this.errorMessage = 'Invalid Id';
            // console.log('Employee Id missing cannot run query');
      }

    // this.onCanceledClicked(ne);
    // // console.log(this.updateButton.concat(newEmployeeSkillRated.subSkill.name.toString()));
    // const y = document.getElementById(this.updateButton.concat(newEmployeeSkillRated.subSkill.name.toString()));
    // y.hidden = !(y.hidden);
    // const x = document.getElementById(newEmployeeSkillRated.subSkill.name.toString());
    // x.hidden = !(x.hidden);
  }

  // Can be used to refresh the component to same page
  onRefresh() {
    this.router.routeReuseStrategy.shouldReuseRoute = function(){return false;};
  
    let currentUrl = this.router.url + '?';
  
    this.router.navigateByUrl(currentUrl)
      .then(() => {
        this.router.navigated = false;
        this.router.navigate([this.router.url]);
      });
    }
}
