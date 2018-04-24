import { Component, OnInit } from '@angular/core';
import { Skill } from '../../../model/Skill';
import { SkillGroupService } from '../../../services/SkillGroupService.service';
import { SkillGroup } from '../../../model/SkillGroup';

@Component({
  selector: 'app-skillgroup',
  templateUrl: './skillgroup.component.html',
  styleUrls: ['./skillgroup.component.css']
})
export class SkillGroupComponent implements OnInit {
  skills: Skill;
  uparrow = 'fa fa-angle-up';
  errorMessage: any;
  activeTags = '';
  skillGroup: SkillGroup;
  showSpinner = false;
  keys;
  temp;
  constructor(private skillGroupService: SkillGroupService) { }

  toggle(param: string) {
    if (this.activeTags.includes(param)) {
      this.activeTags = this.activeTags.replace(param, '');
    } else {
      this.activeTags = this.activeTags.concat(param);
    }

  }

  ngOnInit() {
    this.showSpinner = true;
    this.skillGroupService.getData()
      .subscribe(
      skillGroup => {
        this.skillGroup = skillGroup;
        this.temp = new Map();
        console.log("Skill group recieved" +this.skillGroup);
        for (const key in this.skillGroup) {
          this.temp.set(key, this.skillGroup[key]);
        }
      
      }
      , error => {
        this.temp  = new Map();
        this.errorMessage = <any>error;
        this.showSpinner = false;
      },
      () => this.gettingKeys()
      );

  }

  gettingKeys() {

    this.keys = this.temp.keys();
    this.showSpinner = false;
  }
}
