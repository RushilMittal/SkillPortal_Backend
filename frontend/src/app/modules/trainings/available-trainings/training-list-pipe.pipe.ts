import { Pipe, PipeTransform } from '@angular/core';
import { Event } from '../../../model/Event';
import { TrainingDomain } from '../../../model/training-domain';

@Pipe({name: 'monthfilter2'})
export class AvailableTrainingPipe implements PipeTransform {
  
events : any=[] ;

  transform(value: TrainingDomain[], month: number, year:number): Event[] {
      this.events=[];
    //   console.log("At pipe");
    //   console.log(month);
    //   console.log(year);
    for(let event of value)
    {
      let dt = new Date(event.trainingSessions[0].trainingDate);
    //   console.log(dt);
    //   console.log(dt.getMonth());
      if(dt.getMonth()==month && dt.getFullYear()==year)
      this.events.push(event);
    }
    return this.events;
  }
}