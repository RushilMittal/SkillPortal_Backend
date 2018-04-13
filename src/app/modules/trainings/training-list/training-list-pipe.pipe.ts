import { Pipe, PipeTransform } from '@angular/core';
import { Event } from '../../../model/Event';

@Pipe({name: 'monthfilter'})
export class TrainingListPipe implements PipeTransform {

events: any = [] ;

  transform(value: Event[], month: number, year: number): Event[] {
      this.events = [];
    //   console.log("At pipe");
    //   console.log(month);
    //   console.log(year);
    for (const event of value) {
      const dt = new Date(event.start);
    //   console.log(dt);
    //   console.log(dt.getMonth());
      if (dt.getMonth() == month && dt.getFullYear() == year) {
      this.events.push(event);
      }
    }
    return this.events;
  }
}
