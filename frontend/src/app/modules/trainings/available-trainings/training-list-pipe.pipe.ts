import { Pipe, PipeTransform } from '@angular/core';
import { Event } from '../../../model/Event';
import { TrainingDomain } from '../../../model/training-domain';

@Pipe({name: 'monthfilter2'})
export class AvailableTrainingPipe implements PipeTransform {
  
events : any=[] ;

  transform(value: TrainingDomain[], month: number, year:number): TrainingDomain[] {
      this.events=[];
  
    for(let event of value)
    {
      let dt = new Date(event.trainingSessions[0].trainingDate);
    
      if(dt.getMonth()==month && dt.getFullYear()==year)
      this.events.push(event);
    }
    return this.events;
  }
}