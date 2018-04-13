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
       }from '@angular/http/testing';
import { AllSubSkillService } from '../services/allsubskillservice.service';
import { SubSkill } from '../model/SubSkill';


describe('AllSubSkillService', () => {
       let allSubSkillService: AllSubSkillService;
       let mockbackend: MockBackend;

     beforeEach(async(() => {
         TestBed.configureTestingModule({
        providers: [

          BaseRequestOptions,
          MockBackend,
          Http,
          AllSubSkillService,
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
      allSubSkillService = testbed.get(AllSubSkillService);
    }
    ));

    function setUpConnections(mockBackend: MockBackend, options: any) {
      mockBackend.connections.subscribe((connection: MockConnection) => {
        const responseOptions = new ResponseOptions(options);
        const response = new Response(responseOptions);

        connection.mockRespond(response);
      });
    }

     it('to check allsubskillservice exists', inject([AllSubSkillService], (allSubSkillService) => {
      expect(allSubSkillService).toBeDefined();
     }));
     it('to check  getAllSubSkill', () => {
        setUpConnections(mockbackend, {
            body: [
               {
                'id': '1',
                'name': 'Programming',
                'ratedUsers': 1
             },
             {
                'id': '2',
                'name': 'AWS',
                'ratedUsers': 1
             },
             {
                'id': '3',
                'name': 'Front End',
                'ratedUsers': 1
             }
            ],
          status: 200
         });
            allSubSkillService. getSubSkill().subscribe((data: SubSkill) => {
            expect(data).toBeDefined();
            expect(data[0].id).toBe('1');
            expect(data[0].name).toBe('Programming');
            expect(data[0].ratedUsers).toBe(1);
        });
     });



});
