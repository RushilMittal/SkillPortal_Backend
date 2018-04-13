import { Component, OnInit } from '@angular/core';
import { EventService } from '../../../services/event.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-training-list',
  templateUrl: './training-list.component.html',
  styleUrls: ['./training-list.component.css']
})
export class TrainingListComponent implements OnInit {
  errorMessage: any;
  date: Date;
  currMonth: number;
  currYear: number;
  month: number;
  year: number;
  months: string[] = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December', ];
  constructor(private eventService: EventService, private router: Router) { }


  events: Event[];
  eventsList: Event[];
  ngOnInit() {
    this.eventService.getEvents('10125')
      .subscribe(events => {
        this.events = events;
      },
        error => this.errorMessage = <any>error);

        this.eventService.getEventsList('10125')
        .subscribe(eventsList => {
          this.eventsList = eventsList;
        },
          error => this.errorMessage = <any>error);

        this.date = new Date();
        this.month = this.date.getMonth();
        this.year = this.date.getFullYear();
        this.currMonth = this.date.getMonth();
        this.currYear = this.date.getFullYear();
      }

      deleteTraining(trainingId): void {
        
        this.eventService.deleteTraining('10125', trainingId).subscribe(() => {
          let newEvents = this.eventsList.filter((event: any) => event.id !== trainingId);
          this.eventsList = newEvents
        });
        
      }

      previous() {
        this.month--;
        if (this.month == -1) {
           this.month = 11;
           this.year--;
         }
      }

      next() {
        this.month++;
        if (this.month == 12) {
           this.month = 0;
           this.year++;
         }
      }
        ngOnDestroy() {
        }

}
