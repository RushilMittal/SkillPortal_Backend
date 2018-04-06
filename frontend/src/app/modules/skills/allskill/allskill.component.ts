import { Component, OnInit, Input } from '@angular/core';
import { AllSkillService } from '../../../services/allskillservice.service';
import { Skill } from '../../../model/Skill';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-allskill',
  templateUrl: './allskill.component.html',
  styleUrls: ['./allskill.component.css']
})
export class AllskillComponent implements OnInit {
  @Input() skills: string[];
  @Input() skillGroup : string;
  errorMessage: any;
  constructor(private allSkillService: AllSkillService) { }
  ngOnInit() {
    console.log("Printing " + this.skills);
    console.log("Skil group " + this.skillGroup);
    // this.allSkillService.getAllSkill()
    //     .subscribe( skill => {
    //         this.skills = skill,
    //         // tslint:disable-next-line:no-unused-expression
    //         error => this.errorMessage = <any>error;
    //       });
  }


}
