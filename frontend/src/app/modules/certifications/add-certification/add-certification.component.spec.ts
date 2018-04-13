import { Observable } from 'rxjs/';
import {
    async, getTestBed,
    inject,
    TestBed,
    ComponentFixture
} from '@angular/core/testing';
import {
    BaseRequestOptions,
    Http,
    Response,
    ResponseOptions,
    XHRBackend
} from '@angular/http';
import {
    MockBackend,
    MockConnection
} from '@angular/http/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AddNewCertificateService } from '../../../services/addnewcertificate.service';
import { AddCertificationComponent } from './add-certification.component';
describe('AddCertificationComponent', () => {
    // let addnewcertificateservice: AddNewCertificateService;
    // let mockbackend: MockBackend;
    // for component testing
    let component: AddCertificationComponent;
    let fixture: ComponentFixture<AddCertificationComponent>;
    let  input, button: HTMLElement;
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            providers: [
                BaseRequestOptions,
                MockBackend,
                Http,
                AddNewCertificateService,
                {
                    deps: [MockBackend, BaseRequestOptions],
                    provide: Http,
                    useFactory: (mockBackend: XHRBackend,
                        defaultOptions: BaseRequestOptions) => {
                        return new Http(mockBackend, defaultOptions);
                    }
                }],
            declarations: [AddCertificationComponent],
            schemas: [NO_ERRORS_SCHEMA]
        }).compileComponents();
        // for component testing
        fixture = TestBed.createComponent(AddCertificationComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }
    ));

    it('should create', () => {
        expect(component).toBeDefined();
    });
    it('to check search input1 exists', () => {
        input = fixture.nativeElement.querySelector('#certificateDateId');
        expect(input).toBeTruthy();
     });
     it('to check search input2 exists', () => {
        input = fixture.nativeElement.querySelector('#certificateNumberId');
        expect(input).toBeTruthy();
     });
     it('to check search input3 exists', () => {
        input = fixture.nativeElement.querySelector('#certificationUrlId');
        expect(input).toBeTruthy();
     });
     it('to check search input4 exists', () => {
        input = fixture.nativeElement.querySelector('#validityId');
        expect(input).toBeTruthy();
     });

     it('to check explore button exists', () => {
      button = fixture.nativeElement.querySelector('.btn btn-primary');
          expect(button).toBeDefined();
     });


});
