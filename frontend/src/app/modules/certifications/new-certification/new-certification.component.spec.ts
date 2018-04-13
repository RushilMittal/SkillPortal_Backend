import { async, getTestBed, inject, TestBed, ComponentFixture } from '@angular/core/testing';
import { BaseRequestOptions, Http, Response, ResponseOptions, XHRBackend } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { by } from 'protractor';
import { By } from '@angular/platform-browser';
import {SearchTransformPipe} from '../../search-transform.pipe';
import { Observable } from 'rxjs/';
import { NewCertificationService } from '../../../services/newcertification.service';
import { NewCertificationComponent } from './new-certification.component';
import { AllSkillService } from '../../../services/allskillservice.service';
import { HttpClient, HttpHandler, HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
describe('Pipe: stringManipulation', () => {
  it('create an instance', () => {
    const pipe = new SearchTransformPipe();
    expect(pipe).toBeTruthy();
  });
});

describe('NewCertificationComponent',
    () => {

      // for component testing
      let component: NewCertificationComponent;
      let fixture: ComponentFixture<NewCertificationComponent>;
      let form, select, button, input: HTMLElement;

      beforeEach(async(
        () => {
        TestBed.configureTestingModule({
          imports : [HttpClientModule, RouterTestingModule],
        providers: [ BaseRequestOptions, MockBackend, Http, HttpClient, AllSkillService, NewCertificationService,
         {
           deps: [MockBackend, BaseRequestOptions],
           provide: Http, HttpClient,
           useFactory: (mockXHRBackend: XHRBackend,
                        defaultOptions: BaseRequestOptions) => {
             return new Http(mockXHRBackend, defaultOptions);
           }
         }],
         declarations: [ NewCertificationComponent, SearchTransformPipe ],
         schemas: [NO_ERRORS_SCHEMA]
      }).compileComponents();

     // for component testing
     fixture = TestBed.createComponent(NewCertificationComponent);
     component = fixture.componentInstance;
     fixture.detectChanges();
     }
   ));

  it('should create New Certification Component', () => {
     expect(component).toBeDefined();
   });

   it('to check whether select exists', () => {
    form = fixture.nativeElement.querySelector('form');
    expect(form).toBeDefined();
  });

  it('to check whether select exists', () => {
    select = fixture.nativeElement.querySelector('select');
    expect(select).toBeTruthy();
  });

  it('to check #certificationNameId input exists', () => {
    input = fixture.nativeElement.querySelector('#certificationNameId');
    expect(input).toBeTruthy();
  });

  it('to check #institutionNameId input exists', () => {
    input = fixture.nativeElement.querySelector('#institutionNameId');
    expect(input).toBeTruthy();
  });

  it('to check #save-certificate button exists', () => {
   button = fixture.nativeElement.querySelector('.btn');
       expect(button).toBeDefined();
  });

});
