import { Component, OnInit } from '@angular/core';
import { SubSkill } from '../../../model/SubSkill';
import { AllSubSkillService } from '../../../services/allsubskillservice.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AllSkillService } from '../../../services/allskillservice.service';
import { Skill } from '../../../model/Skill';
import { MySubSkillService } from '../../../services/mysubskillservice.service';
import { NullTemplateVisitor } from '@angular/compiler';
import { EmployeeSkill } from '../../../model/EmployeeSkill';
import { MySkillService } from '../../../services/myskillservice.service';

@Component({
  selector: 'app-skilldetail',
  templateUrl: './skilldetail.component.html',
  styleUrls: ['./skilldetail.component.css']
})
export class SkilldetailComponent implements OnInit {
  subSkillList: SubSkill;
  skillId: string; // used to fetch the skill detail of selected skill on explore
  skill: Skill;
  errorMessage: any;
  rateButton = 'rate-button';
  activeId: string;
  employeeSkill: EmployeeSkill;
  constructor(private route: ActivatedRoute,
              private skillService: AllSkillService,
              private mySubSkillService: MySubSkillService,
              private dataService: MySkillService,
              private router: Router) { }

  public Valid(isValid: SubSkill) {


   // console.log('buttonname contains' + isValid);
    const x = document.getElementById(isValid.name);
    x.hidden = !(x.hidden);
    // console.log(isValid);
    const y = document.getElementById(this.rateButton.concat(isValid.name));
    y.hidden = !(y.hidden);
    this.activeId = isValid.name;

     this.mySubSkillService.getEmployeeSubSkillById(isValid.id)
          .subscribe( subSkill => {
            this.employeeSkill.subSkill = subSkill,
            (error: any) => this.errorMessage = <any>error;
          });


          // console.log('SUbSKill in employee in valid ' + JSON.stringify(this.employeeSkill.subSkill));

  }


  OnRatingUpdated(newEmployeeSkillRated: EmployeeSkill): void {


    // console.log('Run the post query to the Server with the data recieved' +
     // 'call the service again with the updated data in skilldetails');
    // console.log('Data recieved' + JSON.stringify(newEmployeeSkillRated));
    if (newEmployeeSkillRated.employeeId.length !== 0) {
        this.dataService.saveEmployeeSkill(newEmployeeSkillRated)
            .subscribe(
                () => console.log('Product Passed to savefunction'),
                (error: any) => this.errorMessage = <any>error
            );
            this.router.navigateByUrl('skills/allskill');
            // console.log('NO Error in ifss');
      } else {
            this.errorMessage = 'Invalid Id';
      }

  }

  onCanceledClicked(toHideId: string): void {
    // console.log(this.rateButton.concat(toHideId));
    const y = document.getElementById(this.rateButton.concat(toHideId));
    y.hidden = !(y.hidden);
    const x = document.getElementById(toHideId);
    x.hidden = !(x.hidden);
  }

  ngOnInit() {

    this.route.params.subscribe(
        params => {
          this.skillId = params['id'];
        }
    );
    // console.log('Skill ID received: ' + this.skillId);
    // now will fetch the name of the skill service for displaying as title.

    this.skillService.getSkillById(this.skillId)
        .subscribe( skill => {
            this.skill = skill,
            error => this.errorMessage = <any>error;
          });

    // Temporary Initalizng the employee variable
    this.employeeSkill = new EmployeeSkill;
    this.employeeSkill.employeeId = '101';
    this.employeeSkill.rating = 0;





    // now we need to fetch the query from mysubskills and fetch the unrated recoeds.
    // providing the employee skill manually, later we will call the service to get the employee data
    this.mySubSkillService.getEmployeeSubSkillExceptRatedSubSkill(this.employeeSkill.employeeId, this.skillId)
        .subscribe(subskill => {
            this.subSkillList = subskill,

            error => this.errorMessage = <any>error;
        });

        // console.log('Printing SubSkill Except:- ' + this.subSkillList);


  }

}
