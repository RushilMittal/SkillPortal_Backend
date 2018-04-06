
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import {Observable} from 'rxjs/';

import {async,
        getTestBed,
        inject,
        TestBed,
        ComponentFixture} from '@angular/core/testing';
import {BaseRequestOptions,
        Http,
        Response,
        ResponseOptions,
        XHRBackend} from '@angular/http';
import {
         MockBackend,
         MockConnection
       }from '@angular/http/testing';
import { AllSkillService } from '../../../services/allskillservice.service';
import { SkilldetailComponent } from './skilldetail.component';
import { MySubSkillService } from '../../../services/mysubskillservice.service';
import { MySkillService } from '../../../services/myskillservice.service';


describe('SkillDetailComponent', () => {
        //for component testing
        let component: SkilldetailComponent;
        let fixture: ComponentFixture<SkilldetailComponent>;
        let table, button: HTMLElement;
     beforeEach(async(() => {
         TestBed.configureTestingModule({
           schemas: [NO_ERRORS_SCHEMA],
          imports: [RouterTestingModule],
        providers: [
          BaseRequestOptions,
          MockBackend,
          Http,
          AllSkillService,
          MySubSkillService,
          MySkillService,
          { deps: [MockBackend, BaseRequestOptions],
            provide: Http,
            useFactory: (mockBackend: XHRBackend,
                         defaultOptions: BaseRequestOptions) => {
              return new Http(mockBackend, defaultOptions);
            }
          }],
          declarations: [ SkilldetailComponent ],
       });

       //for component testing
       fixture = TestBed.createComponent(SkilldetailComponent);
       component = fixture.componentInstance;
       fixture.detectChanges();
    }
    ));


    it('should create', () => {
      expect(component).toBeDefined();
    });

    it('to check Back to Skill List button', () => {
      button = fixture.nativeElement.querySelector('#back-to-skill');
      expect(button).toBeTruthy();
    });

    it('to check table exists', () => {
      table = fixture.nativeElement.querySelector('.table');
      expect(table).toBeTruthy();
    });
});
