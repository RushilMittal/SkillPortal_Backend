import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { CalendarComponent } from 'ap-angular2-fullcalendar/src/calendar/calendar';
import { Options } from 'fullcalendar';
import { EventService } from '../../../services/event.service';
import { ToastService } from '../../../services/toast.service';
import { Router } from '@angular/router';
import { months } from 'moment';

@Component({
  selector: 'app-training-calender',
  templateUrl: './training-calender.component.html',
  styleUrls: ['./training-calender.component.css']
})
export class TrainingCalenderComponent implements OnInit {
  
  @ViewChild(CalendarComponent) myCalendar: CalendarComponent;
  errorMessage: any;
  // @Input() iconName: string;
  calendarOptions: Options = {
    editable: false,
    eventLimit: true,
    height: 500,
    fixedWeekCount: false,
    showNonCurrentDates: false,
    header: {
      left: 'prev,next, today, title',
      center: 'false',
      right: 'false'
    },

    events: null,

    eventClick: (event) => this.onRefresh(event.url,new Date(event.start).getMonth(),new Date(event.start).getFullYear())
    
  };
  events: Event[];

  constructor(private eventService: EventService, private toastService: ToastService,
    private router: Router ) { }
  

  onRefresh(url :string ,month?:number, year?:number){
    // console.log("before: ")
    // console.log(this.iconName);
    // this.iconName = 'date_range';
    // console.log("after : ")
    // console.log(this.iconName);
    this.router.navigate(['./trainings/myenrolledtrainings/traininglist',{ month :month, year: year }]);
  }

  ngOnInit() {

    this.eventService.getEvents()
      .subscribe( events=>{
         
         this.calendarOptions.events = events;
        },
        (error: any) => {
          this.errorMessage = <any>error;
          this.toastService.showErrorToast("Unable to Save Some Error Occured");
        }
        
      );
  }
}