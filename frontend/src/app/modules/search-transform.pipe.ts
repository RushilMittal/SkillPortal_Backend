import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'stringManipulation'})
export class SearchTransformPipe implements PipeTransform {
  transform(value: string): string {
    let re = /\_/gi;
    value = value.replace(re," ");
    return value;
  }
}