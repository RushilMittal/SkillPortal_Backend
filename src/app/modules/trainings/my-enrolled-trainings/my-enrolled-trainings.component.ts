import { Component, OnInit, ViewChild } from '@angular/core';
import { CalendarComponent } from 'ap-angular2-fullcalendar/src/calendar/calendar';
import { Options } from 'fullcalendar';
import { EventService } from '../../../services/event.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-enrolled-trainings',
  templateUrl: './my-enrolled-trainings.component.html',
  styleUrls: ['./my-enrolled-trainings.component.css']
})
export class MyEnrolledTrainingsComponent {


  iconName = 'date_range';

  constructor(private router: Router) {
    // console.log("constructor" +this.activeType);
    // console.log("constructor" +this.iconName);
  }


  public changeIcon(): void {
    // console.log("before a "+this.activeType);
    // console.log("before i " +this.iconName);
    if (this.iconName === 'date_range') {
      this.iconName = 'list';
      this.router.navigateByUrl('trainings/myenrolledtrainings/trainingcalender');
    } else {
      this.iconName = 'date_range';
      this.router.navigateByUrl('trainings/myenrolledtrainings/traininglist');
    }
    // console.log("after a "+this.activeType);
    // console.log("after i " +this.iconName);
  }

  ngOnInit() { this.iconName = 'date_range';
  this.router.navigateByUrl('trainings/myenrolledtrainings/traininglist');

  }
}

