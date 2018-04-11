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
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { by } from 'protractor';
import { By } from '@angular/platform-browser';

import { DashBoardSkillPlaceHolderService } from '../../../services/dashboardskillplaceholder.service';
import { SkillsPlaceholderComponent } from './skills-placeholder.component';





describe('SkillsPlaceholder', () => {

       //for component testing
       let component: SkillsPlaceholderComponent;
       let fixture: ComponentFixture<SkillsPlaceholderComponent>;
       let table, div, a, p: HTMLElement;
     beforeEach(async(() => {
         TestBed.configureTestingModule({
        providers: [

          BaseRequestOptions,
          MockBackend,
          Http,
          DashBoardSkillPlaceHolderService,
          { deps: [MockBackend, BaseRequestOptions],
            provide: Http,
            useFactory: (mockBackend: XHRBackend,
                         defaultOptions: BaseRequestOptions) => {
              return new Http(mockBackend, defaultOptions);
            }
          }],
          declarations: [ SkillsPlaceholderComponent ],
          schemas: [NO_ERRORS_SCHEMA]
       }).compileComponents();

      //for component testing
      fixture = TestBed.createComponent(SkillsPlaceholderComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
      }
    ));

    it('should create', () => {
      expect(component).toBeDefined();
    });
    it('to check whether card exists', () => {
        div = fixture.nativeElement.querySelector('.card');
        expect(div).toBeTruthy();
    });

    it('to check paragraph exists', () => {
      p = fixture.nativeElement.querySelector('p');
      expect(p).toBeTruthy();
   });
   it('to check view detail button exists', () => {
     a = fixture.nativeElement.querySelector('a');
        expect(a.textContent).toBe('View Details');
   });
});