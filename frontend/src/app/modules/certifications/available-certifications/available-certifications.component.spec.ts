import { async, getTestBed, inject, TestBed, ComponentFixture } from '@angular/core/testing';
import { BaseRequestOptions, Http, Response, ResponseOptions, XHRBackend } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { by } from 'protractor';
import { By } from '@angular/platform-browser';

import { Observable } from 'rxjs/';
import { AllCertificationService } from '../../../services/allcertification.service';
import { AvailableCertificationsComponent } from './available-certifications.component';

describe('AvailablecertificationComponent',
    () => {

      // for component testing
      let component: AvailableCertificationsComponent;
      let fixture: ComponentFixture<AvailableCertificationsComponent>;
      let table, button: HTMLElement;

      beforeEach(async(
        () => {
        TestBed.configureTestingModule({
        providers: [ BaseRequestOptions, MockBackend, Http, AllCertificationService,
         {
           deps: [MockBackend, BaseRequestOptions],
           provide: Http,
           useFactory: (mockXHRBackend: XHRBackend,
                        defaultOptions: BaseRequestOptions) => {
             return new Http(mockXHRBackend, defaultOptions);
           }
         }],
         declarations: [ AvailableCertificationsComponent ],
         schemas: [NO_ERRORS_SCHEMA]
      }).compileComponents();

     // for component testing
     fixture = TestBed.createComponent(AvailableCertificationsComponent);
     component = fixture.componentInstance;
     fixture.detectChanges();
     }
   ));

  it('should create All Certification Component', () => {
     expect(component).toBeDefined();
   });

  it('to check #not-in-list button exists', () => {
    button = fixture.nativeElement.querySelector('#not-in-list');
        expect(button).toBeDefined();
   });

  it('to check whether "Available Certification" table exists', () => {
       table = fixture.nativeElement.querySelector('table');
       expect(table).toBeTruthy();
   });

  it('to check #add-certificate button exists', () => {
   button = fixture.nativeElement.querySelector('#add-certificate');
       expect(button).toBeDefined();
  });

});
