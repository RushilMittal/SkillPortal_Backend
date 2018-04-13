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
  uparrow = 'fas fa-angle-up';
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
    // console.log(this.activeTags);
  }

  ngOnInit() {
    this.showSpinner = true;
      this.skillGroupService.getData()
      .subscribe(
        skillGroup => {
          this.skillGroup = skillGroup;
          this.temp = new Map();
          // tslint:disable-next-line:forin
          for (const key in this.skillGroup) {
            this.temp.set(key, this.skillGroup[key]);
          }
        }
        , (error: any) => this.errorMessage = <any>error,
        () => this.gettingKeys()


      );

  }

  gettingKeys() {

    this.keys = this.temp.keys();
    this.showSpinner = false;
  }
}
