import { Injectable } from '@angular/core';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import { Skill } from '../model/Skill';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
@Injectable()
export class IdService {
    private subject = new BehaviorSubject<number>(1);

    sendMessage(message: number) {
        this.subject.next(message);
        // console.log('inside sendMessage:IdService' + message);
    }

    clearMessage() {
        this.subject.next(-1);
    }

    getMessage(): Observable<number> {
        // console.log('inside getMessage:IdService' + this.subject);
        return this.subject.asObservable();
    }
}
