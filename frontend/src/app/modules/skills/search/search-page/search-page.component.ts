import { Component, OnInit } from '@angular/core';
import {Http} from '@angular/http';
import { Location } from '@angular/common';
import {Observable} from 'rxjs/Observable';
import { SearchItem } from '../../../../model/search-item';
import { NgModel } from '@angular/forms';
import 'rxjs/add/operator/map';

@Component({
 selector: 'app-search-page',
 templateUrl: './search-page.component.html',
 styleUrls: ['./search-page.component.css'],

})
export class SearchPageComponent implements OnInit {

 constructor(private location: Location) {

 }

filter: string;


goBack(): void {
  this.location.back();
}

 ngOnInit() {
   const x = document.getElementById('modelSkill-search');
   x.focus();
 }

}

