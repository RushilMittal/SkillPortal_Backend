import { Component, OnInit } from '@angular/core';
import { EventService } from '../../../services/event.service';
import { Router } from '@angular/router';
import { ToastService } from '../../../services/toast.service';

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
  months: string[] = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December',];
  showSpinner = false;
  eventsList: Event[] = [];

  constructor(private eventService: EventService, private router: Router,
    private toastService: ToastService) { }

  ngOnInit() {
    this.showSpinner =true;

    this.eventService.getEventsList()
      .subscribe(eventsList =>{
        this.eventsList =eventsList;
        this.errorMessage = 'No training this Month';
      },
      error => {
        this.errorMessage = <any>error,
          this.showSpinner = false;
      },
      () => {
        this.showSpinner = false
      }
    );

    this.date = new Date();
    this.month = this.date.getMonth();
    this.year = this.date.getFullYear();
    this.currMonth = this.date.getMonth();
    this.currYear = this.date.getFullYear();
  }

  deleteTraining(trainingId): void {

    this.eventService.deleteTraining(trainingId).subscribe(
      ()=>{},
      (error: any) => {
        this.errorMessage = <any>error;
        this.toastService.showErrorToast("Unable to Delete");
      },
      () => {
        let newEvents = this.eventsList.filter((event: any) => event.id !== trainingId);
        this.eventsList = newEvents
        this.toastService.showSuccessToast('Unrolled Successfully');
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
