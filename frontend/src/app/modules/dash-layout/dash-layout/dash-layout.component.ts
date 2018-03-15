import { Component, OnInit } from '@angular/core';
import { SearchItem } from '../../../model/search-item';

@Component({
  selector: 'app-dash-layout',
  templateUrl: './dash-layout.component.html',
  styleUrls: ['./dash-layout.component.css']
})
export class DashLayoutComponent {
  filter: SearchItem = {skillId: '', name: '', isChild: false, subSkillId: ''};
  constructor() { }

}
