import { Component, OnInit, ViewChild, Input } from '@angular/core';
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
  }

  onActivate(component) {

    if (this.iconName === 'date_range') {
      this.iconName = 'list';
    } else {
      this.iconName = 'date_range';
    }
  }


  public changeIcon(): void {
    if (this.iconName === 'date_range') {
      this.router.navigateByUrl('trainings/myenrolledtrainings/trainingcalender');
    } else {
      this.router.navigateByUrl('trainings/myenrolledtrainings/traininglist');
    }
  }

  ngOnInit() { this.iconName = 'date_range';
  this.router.navigateByUrl('trainings/myenrolledtrainings/traininglist');

  }
}

