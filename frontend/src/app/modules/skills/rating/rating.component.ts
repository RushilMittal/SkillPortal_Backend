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






  // tslint:disable-next-line:use-life-cycle-interface
  ngAfterViewInit(): void {

    // this.originalRating = this.newEmployeeData.rating;
    // console.log("Original Rating" + this.originalRating);
    // activeRating.style.backgroundColor = "rgba(8, 133, 172, 0.59)";
    // activeRating.style.color ="white";
    // console.log('JOb DONE');`

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
    // this.newEmployeeData.rating = ratingRecieved;
    // console.log('inside the rating click event');
    // console.log("temp rqating "+ this.tempRating + " rating recieved" + ratingRecieved  + "original rating" + this.originalRating);

    // below function is removing the click effects , on click.
    if (this.skills.rating !== 0) {
      const activeRating = document.getElementById(this.skills.subSkill.id + '' + this.skills.rating);

      console.log(activeRating);
      activeRating.style.backgroundColor = 'white';
      activeRating.style.color = 'black';
    }

    if (this.tempRating === 0 || this.tempRating === ratingRecieved) {
      // console.log('Inside if');
      // console.log('New Rating ' + ratingRecieved);


      // console.log(this.subSkillName + '' + ratingRecieved);
      const activeRating = document.getElementById(this.skills.subSkill.id + '' + ratingRecieved);
      // const previousRating = document.getElementById(this.subSkillName + "" + this.tempRating);
      // console.log(activeRating);
      // console.log("prev" + previousRating);
      activeRating.style.backgroundColor = 'rgba(8, 133, 172, 0.59)';
      activeRating.style.color = 'white';

      // previousRating.style.color = "black";
      // previousRating.style.backgroundColor ="white";
      this.tempRating = ratingRecieved;
    } else {
      // console.log('Inside else');
      // console.log('New Rating ' + ratingRecieved);


      // console.log(this.subSkillName + '' + ratingRecieved);
      const activeRating = document.getElementById(this.skills.subSkill.id + '' + ratingRecieved);
      const previousRating = document.getElementById(this.skills.subSkill.id + '' + this.tempRating);
      // console.log(activeRating);
      // console.log(previousRating);

      activeRating.style.backgroundColor = 'rgba(8, 133, 172, 0.59)';
      activeRating.style.color = 'white';


      previousRating.style.color = 'black';
      previousRating.style.backgroundColor = 'white';
      this.tempRating = ratingRecieved;
    }
    // console.log("temp rqating "+ this.tempRating + " rating recieved" + ratingRecieved  + "original rating" + this.originalRating);
  }

  updateRating(): void {

    this.skills.rating = this.tempRating;
    // console.log("in rating new" +this.originalRating + " temp rating" + this.tempRating);
    if (this.tempRating !== this.originalRating) {

      console.log('inside if in rating new' + this.originalRating + ' temp rating' + this.tempRating);
      // Also provide the new Date Stamp here as post query will be executed
      this.updateClicked.emit(this.skills);

      this.cancelClicked.emit(this.skills.subSkill.id);

    } else {
      // console.log('Same rating,, Select other to apply');
    }
    this.originalRating = this.skills.rating;

  }

  // cancelClickedFunction(): void {


  //   if (this.originalRating !== 0  && this.tempRating !== 0)  {

  //     const originalRating = document.getElementById(this.subSkillName + '' + this.originalRating);
  //     const tempRating = document.getElementById(this.subSkillName + '' + this.tempRating);

  //     originalRating.style.backgroundColor = 'rgba(8, 133, 172, 0.59)';
  //     originalRating.style.color = 'white';

  //     tempRating.style.color = 'black';
  //     tempRating.style.backgroundColor = 'white';
  //   }



  //   this.cancelClicked.emit(this.subSkillName);
  // }
}