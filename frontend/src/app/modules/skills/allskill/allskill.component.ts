import { Component, OnInit } from '@angular/core';
import { AllSkillService } from '../../../services/allskillservice.service';
import { Skill } from '../../../model/Skill';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-allskill',
  templateUrl: './allskill.component.html',
  styleUrls: ['./allskill.component.css']
})
export class AllskillComponent implements OnInit {
  skills: Skill;

  errorMessage: any;
  constructor(private allSkillService: AllSkillService) { }

  ngOnInit() {

    this.allSkillService.getAllSkill()
        .subscribe( skill => {
            this.skills = skill,
            // tslint:disable-next-line:no-unused-expression
            error => this.errorMessage = <any>error;
          });
  }

}
