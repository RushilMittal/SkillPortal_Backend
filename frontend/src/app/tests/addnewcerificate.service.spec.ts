
import { async, getTestBed, inject, TestBed } from '@angular/core/testing';
import { BaseRequestOptions, Http, Response, ResponseOptions, XHRBackend } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';

import { Observable } from 'rxjs/Observable';
import { AddNewCertificateService } from '../services/addnewcertificate.service';
import { EmployeeCertificate } from '../model/EmployeeCertification';

describe('AddNewCertificateService',
  () => {
      let addNewCertificateService: AddNewCertificateService;
      let mockBackend: MockBackend;

      beforeEach( async(
        () => {
        TestBed.configureTestingModule ({
          providers: [ BaseRequestOptions, MockBackend, Http, AddNewCertificateService,
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
      addNewCertificateService = testBed.get(AddNewCertificateService);
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

    it('to check addnewcertificationservice exists', inject([AddNewCertificateService],
            (addNewCertificate) => { expect(addNewCertificate).toBeDefined(); })
    );

    it('to check saveNewCertification', () => {
        addNewCertificateService.saveNewCertification(
            {
                'empId': '101',
                'certificationId':
                {
                    'id': '1102',
                    'skillId': '2',
                    'certificationName': 'JavaScript',
                    'institution': 'SoloLearn'
                },
                'certificationDate': new Date(),
                'certificationValidityDate': new Date(),
                'certificationUrl': 'www.soloslsok.com',
                'certificationNumber': 102696
            })
        .subscribe(
          (data: EmployeeCertificate) => {
          expect(data[0].empId).toBe('101'),
          expect(data[0].certificationId.id).toBe('1102'),
          expect(data[0].certificationId.skillId).toBe('2'),
          expect(data[0].certificationId.certificationName).toBe('JavaScript'),
          expect(data[0].certificationId.institution).toBe('SoloLearn'),
          expect(data[0].certificationDate).toBe(new Date()),
          expect(data[0].certificationValidityDate).toBe(new Date()),
          expect(data[0].certificationUrl).toBe('www.soloslsok.com'),
          expect(data[0].certificationNumber).toBe(102696); });
        });
  });

