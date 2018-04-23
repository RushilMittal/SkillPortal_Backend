import { Pipe, PipeTransform } from '@angular/core';
import { Event } from '../../../model/Event';

@Pipe({ name: 'monthfilter' })
export class TrainingListPipe implements PipeTransform {

  events: any = [];

  transform(value: Event[], month: number, year: number): Event[] {
    this.events = [];
    if (value.length == 0) {
      return null;
    }
    else {
      for (const event of value) {
        const dt = new Date(event.start);
        if (dt.getMonth() == month && dt.getFullYear() == year) {
          this.events.push(event);
        }
      }
      return this.events;
    }
  }
}
