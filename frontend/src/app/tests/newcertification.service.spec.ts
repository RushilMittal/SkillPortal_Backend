import { async, getTestBed, inject, TestBed } from '@angular/core/testing';
import { BaseRequestOptions, Http, Response, ResponseOptions, XHRBackend } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';

import { Observable } from 'rxjs/Observable';
import { NewCertificationService } from '../services/newcertification.service';
import { Certification } from '../model/Certification';

describe('NewCertificationService',
  () => {
      let newCertificationService: NewCertificationService;
      let mockBackend: MockBackend;

      beforeEach( async(
        () => {
        TestBed.configureTestingModule ({
          providers: [ BaseRequestOptions, MockBackend, Http, NewCertificationService,
            {
              deps: [MockBackend, BaseRequestOptions],
              provide: Http,
              useFactory: (mockXHRBackend: XHRBackend, defaultOptions: BaseRequestOptions) => {
                return new Http(mockXHRBackend, defaultOptions);
              }
            }]
          });
      const testBed = getTestBed();
      mockBackend = testBed.get(MockBackend);
      newCertificationService = testBed.get(NewCertificationService);
      }
    ));

    function setUpConnections(mockBackendSetup: MockBackend, options: any) {
      mockBackendSetup.connections.subscribe(
        (mockConnection: MockConnection) => {
          const responseOptions = new ResponseOptions(options);
          const response = new Response(responseOptions);
          mockConnection.mockRespond(response);
        });
    }

    it('to check newcertificationservice exists', inject([NewCertificationService],
      // tslint:disable-next-line:no-shadowed-variable
      (newCertificationService) => { expect(newCertificationService).toBeDefined(); })
    );

    // Testing POST Method
    it('to check saveNewCertification', () => {
      newCertificationService.saveNewCertification(
        {
          'id': '1102',
          'skillId': '2',
          'certificationName': 'JavaScript',
          'institution': 'SoloLearn'
        })
      .subscribe(
        (data: Certification) => {
        expect(data[0].id).toBe('1102'),
        expect(data[0].skillId).toBe('2'),
        expect(data[0].certificationName).toBe('JavaScript'),
        expect(data[0].institution).toBe('SoloLearn'); });
      });
});
