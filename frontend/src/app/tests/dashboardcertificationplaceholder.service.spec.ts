import {Observable} from 'rxjs/';
import {async,
       getTestBed,
       inject,
       TestBed} from '@angular/core/testing';
import {BaseRequestOptions,
       Http,
       Response,
       ResponseOptions,
       XHRBackend} from '@angular/http';
import {
        MockBackend,
        MockConnection
      } from '@angular/http/testing';

import { Console } from '@angular/core/src/console';
import { DashboardCertificationPlaceholderService } from '../services/dashboardcertificationplaceholder.service';
import { EmployeeCertificatePlaceholderModel } from '../model/EmployeeCertificatePlaceholderModel';
   describe('certificationplaceholderservice', () => {
      let certificationPlaceholderService: DashboardCertificationPlaceholderService;
      let mockbackend: MockBackend;

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
         }]
      });
     const testbed = getTestBed();
     mockbackend = testbed.get(MockBackend);
     certificationPlaceholderService = testbed.get(DashboardCertificationPlaceholderService);
   }
   ));

   function setUpConnections(mockBackend: MockBackend, options: any) {
     mockBackend.connections.subscribe((connection: MockConnection) => {
       const responseOptions = new ResponseOptions(options);
       const response = new Response(responseOptions);
       connection.mockRespond(response);
     });
   }

    // tslint:disable-next-line:max-line-length
    it('to check certificationplaceholderservice exists', inject([DashboardCertificationPlaceholderService], (certificationPlaceholderService) => {
       expect(certificationPlaceholderService).toBeTruthy();
    }));
    it('to check getCertificatePlaceholder by empId', () => {
       setUpConnections(mockbackend, {
           body: [{

                   'certificationName': 'RHC',
                   'year': '2018',

                 },
                 {

                  'certificationName': 'AWS',
                  'year': '2018',

                }
          ],
           status: 200
         });

         certificationPlaceholderService.getCertificatePlaceholder('101').subscribe((data: EmployeeCertificatePlaceholderModel) => {
             expect(data[0].certificationName).toBe('RHC'),
             expect(data[0].year).toBe('2018'),
             expect(data[1].certificationName).toBe('AWS'),
             expect(data[0].year).toBe('2018');
       });
    });

});
