import { Component, OnInit, ViewChild } from '@angular/core';
import { CalendarComponent } from 'ap-angular2-fullcalendar/src/calendar/calendar';
import { Options } from 'fullcalendar';
import { EventService } from '../../../services/event.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-training-calender',
  templateUrl: './training-calender.component.html',
  styleUrls: ['./training-calender.component.css']
})
export class TrainingCalenderComponent implements OnInit {

  @ViewChild(CalendarComponent) myCalendar: CalendarComponent;
  errorMessage: any;
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
  };
  events: Event[];

  constructor(private eventService: EventService, private toastService: ToastService) { }

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