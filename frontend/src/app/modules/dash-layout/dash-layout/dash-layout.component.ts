import { Component, OnInit } from '@angular/core';
import { SearchItem } from '../../../model/search-item';


@Component({
  selector: 'app-dash-layout',
  templateUrl: './dash-layout.component.html',
  styleUrls: ['./dash-layout.component.css']
})
export class DashLayoutComponent {
  filter: string;
  toShow = false;
  constructor() { }
  called() {
    // console.log("I am called ");
    this.toShow = true;
  }
  decalled() {
    // console.log("gone");
    this.toShow = false;
  }
  logout() {
    
 }

}
