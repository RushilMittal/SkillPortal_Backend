import { Component, OnInit, Input, Output, EventEmitter, OnChanges, AfterViewInit } from '@angular/core';
import { SubSkill } from '../../../model/SubSkill';

import {  Router } from '@angular/router';
import { EmployeeSkill } from '../../../model/EmployeeSkill';



@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit {
  @Input() skills: EmployeeSkill;
  @Output() cancelClicked: EventEmitter<string> = new EventEmitter<string>();
  @Output() updateClicked: EventEmitter<EmployeeSkill> = new EventEmitter<EmployeeSkill>();


  tempRating = 0;
  originalRating: number;
  activeRatingId: string;
  cancelbutton = 'cancel-button';

  constructor(private route: Router, ee: EmployeeSkill) {

  }
  ngOnInit(): void {
    // console.log('Data recieved in App rating' + JSON.stringify(this.newEmployeeData));
  }

  cancelClickedFunction(): void {
    if (this.originalRating !== 0 && this.tempRating !== 0) {

      const originalRating = document.getElementById(this.skills.subSkill.id + '' + this.originalRating);
      const tempRating = document.getElementById(this.skills.subSkill.id + '' + this.tempRating);

      originalRating.style.backgroundColor = 'rgba(8, 133, 172, 0.59)';
      originalRating.style.color = 'white';

      tempRating.style.color = 'black';
      tempRating.style.backgroundColor = 'white';
    }


    this.cancelClicked.emit(this.skills.subSkill.id);
  }


  ngAfterViewInit(): void {
    if (this.skills.rating !== 0) {
      const activeRating = document.getElementById(this.skills.subSkill.id + '' + this.skills.rating);

      // console.log(activeRating);
      activeRating.style.backgroundColor = 'rgba(8, 133, 172, 0.59)';
      activeRating.style.color = 'white';
      this.originalRating = this.skills.rating;
    } else {
      this.originalRating = 0;
    }


  }

  onClick(ratingRecieved: number) {
    
    // below function is removing the click effects , on click.
    if (this.skills.rating !== 0) {
      const activeRating = document.getElementById(this.skills.subSkill.id + '' + this.skills.rating);

      console.log(activeRating);
      activeRating.style.backgroundColor = 'white';
      activeRating.style.color = 'black';
    }

    if (this.tempRating === 0 || this.tempRating === ratingRecieved) {
      const activeRating = document.getElementById(this.skills.subSkill.id + '' + ratingRecieved);
      activeRating.style.backgroundColor = 'rgba(8, 133, 172, 0.59)';
      activeRating.style.color = 'white';
      this.tempRating = ratingRecieved;
    } else {
      const activeRating = document.getElementById(this.skills.subSkill.id + '' + ratingRecieved);
      const previousRating = document.getElementById(this.skills.subSkill.id + '' + this.tempRating);
      activeRating.style.backgroundColor = 'rgba(8, 133, 172, 0.59)';
      activeRating.style.color = 'white';
      previousRating.style.color = 'black';
      previousRating.style.backgroundColor = 'white';
      this.tempRating = ratingRecieved;
    }
  }

  updateRating(): void {

    this.skills.rating = this.tempRating;
    if (this.tempRating !== this.originalRating) {
      console.log('inside if in rating new' + this.originalRating + ' temp rating' + this.tempRating);
      this.updateClicked.emit(this.skills);
      this.cancelClicked.emit(this.skills.subSkill.id);
    } else {
      
    }
    this.originalRating = this.skills.rating;

  }
}
