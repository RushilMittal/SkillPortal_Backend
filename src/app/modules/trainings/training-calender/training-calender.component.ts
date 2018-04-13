import { Component, OnInit, ViewChild } from '@angular/core';
import { CalendarComponent } from 'ap-angular2-fullcalendar/src/calendar/calendar';
import { Options } from 'fullcalendar';
import { EventService } from '../../../services/event.service';

@Component({
  selector: 'app-training-calender',
  templateUrl: './training-calender.component.html',
  styleUrls: ['./training-calender.component.css']
})
export class TrainingCalenderComponent implements OnInit {

  @ViewChild(CalendarComponent) myCalendar: CalendarComponent;
  errorMessage: any;
  calendarOptions: Options = {
    editable: true,
    eventLimit: true,
    height: 500,
    fixedWeekCount: false,
    showNonCurrentDates: false,
    // eventRender: function(events,element) {
    //   element.popover({
    //     title: events.title,
    //     content: events.description,
    //     trigger: 'hover',
    //     placement: 'top',
    //     container: 'body'
    //   });
    // },
    // -------------------------------
    // defaultView:'listMonth',

    // views: {
    //   agenda: {
    //     eventLimit: 4, // adjust to 6 only for agendaWeek/agendaDay 
    //   }
    // },

    header: {
      left: 'prev,next, today, title',
      center: 'false',
      right: 'false'
    },
    events: null,
  };
  constructor(private eventService: EventService) { }

  events: Event[];

  ngOnInit() {
    this.eventService.getEvents('10125')
      .subscribe(events => {
        this.calendarOptions.events = events;
      },
        error => this.errorMessage = <any>error);
  }
}
