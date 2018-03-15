import {Pipe} from '@angular/core';
import { PipeTransform } from '@angular/core/src/change_detection/pipe_transform';

@Pipe({
    name : 'getData'
})
export class GetDataPipe implements PipeTransform {
    transform(value, args: string[])
     : any {
        const keys = [];
        // tslint:disable-next-line:forin
        for (const key in value) {
          keys.push({key: key, value: value[key]});
        }
        console.log('keys' + JSON.stringify(keys));
        return keys;
      }
}
