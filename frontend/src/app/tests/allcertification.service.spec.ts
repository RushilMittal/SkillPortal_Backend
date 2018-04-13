
import { async, getTestBed, inject, TestBed } from '@angular/core/testing';
import { BaseRequestOptions, Http, Response, ResponseOptions, XHRBackend } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';

import { Observable } from 'rxjs/Observable';
import { AllCertificationService } from '../services/allcertification.service';
import { Certification } from '../model/Certification';

describe('AllCertificationService',
  () => {
      let allCertificationService: AllCertificationService;
      let mockBackend: MockBackend;

      beforeEach( async(
        () => {
        TestBed.configureTestingModule ({
          providers: [ BaseRequestOptions, MockBackend, Http, AllCertificationService,
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
      allCertificationService = testBed.get(AllCertificationService);
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

    it('to check allcertificationservice exists', inject([AllCertificationService],
      // tslint:disable-next-line:no-shadowed-variable
      (allCertificationService) => { expect(allCertificationService).toBeDefined(); })
    );

    // Testing GET Method
    it('to check getAllCertificates', () => {
      setUpConnections(mockBackend, {
        body: [
          {
            'id': '1102',
            'skillId': '2',
            'certificationName': 'JavaScript',
            'institution': 'SoloLearn'
          }],
          status: 200
      });
      allCertificationService.getAllCertificates()
      .subscribe(
        (data: Certification) => {
        expect(data[0].id).toBe('1102'),
        expect(data[0].skillId).toBe('2'),
        expect(data[0].certificationName).toBe('JavaScript'),
        expect(data[0].institution).toBe('SoloLearn'); });
      });
});
