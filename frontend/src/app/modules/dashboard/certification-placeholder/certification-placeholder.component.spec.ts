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
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { by } from 'protractor';
import { By } from '@angular/platform-browser';
import { CertificationPlaceholderComponent } from './certification-placeholder.component';
import { DashboardCertificationPlaceholderService } from '../../../services/dashboardcertificationplaceholder.service';


describe('CertificationPlaceholder', () => {
       // for component testing
       let component: CertificationPlaceholderComponent;
       let fixture: ComponentFixture<CertificationPlaceholderComponent>;
       let div, a, ul: HTMLElement;
     beforeEach(async(() => {
         TestBed.configureTestingModule({
        providers: [
          BaseRequestOptions,
          MockBackend,
          Http,
          DashboardCertificationPlaceholderService,
          { deps: [MockBackend, BaseRequestOptions],
            provide: Http,
            useFactory: (mockBackend: XHRBackend,
                         defaultOptions: BaseRequestOptions) => {
              return new Http(mockBackend, defaultOptions);
            }
          }],
          declarations: [ CertificationPlaceholderComponent ],
          schemas: [NO_ERRORS_SCHEMA]
       }).compileComponents();
      // for component testing
      fixture = TestBed.createComponent(CertificationPlaceholderComponent);
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

    it('to check unordered list exists', () => {
      ul = fixture.nativeElement.querySelector('ul');
      expect(ul).toBeTruthy();
   });
   it('to check view detail button exists', () => {
     a = fixture.nativeElement.querySelector('a');
        expect(a.textContent).toBe('View Details');
   });
});
