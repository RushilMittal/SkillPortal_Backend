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
       } from '@angular/http/testing';
import { AllSkillService } from '../../../services/allskillservice.service';
import { AllskillComponent } from './allskill.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { by } from 'protractor';
import { By } from '@angular/platform-browser';




describe('AllSkillComponent', () => {
       //for component testing
       let component: AllskillComponent;
       let fixture: ComponentFixture<AllskillComponent>;
       let table, input, button: HTMLElement;
     beforeEach(async(() => {
         TestBed.configureTestingModule({
         providers: [
          BaseRequestOptions,
          MockBackend,
          Http,
          AllSkillService,
          { deps: [MockBackend, BaseRequestOptions],
            provide: Http,
            useFactory: (mockBackend: XHRBackend,
                         defaultOptions: BaseRequestOptions) => {
              return new Http(mockBackend, defaultOptions);
            }
          }],
          declarations: [ AllskillComponent ],
          schemas: [NO_ERRORS_SCHEMA]
       }).compileComponents();
      fixture = TestBed.createComponent(AllskillComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
      }
    ));

    it('should create', () => {
      expect(component).toBeDefined();
    });

    it('to check whether table exists', () => {
        table = fixture.nativeElement.querySelector('table');
        expect(table).toBeTruthy();
    });


    it('to check search input exists', () => {
      input = fixture.nativeElement.querySelector('input');
      expect(input.className).toBe('form-control');
   });

   it('to check explore button exists', () => {
    button = fixture.nativeElement.querySelector('.btn');
        expect(button).toBeDefined();
   });
});


