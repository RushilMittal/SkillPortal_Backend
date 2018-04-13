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
import { MySkillService } from '../services/myskillservice.service';
import { EmployeeSkill } from '../model/EmployeeSkill';
import { SubSkill } from '../model/SubSkill';


    describe('MySkillService', () => {
       let myskillservice: MySkillService;
       let mockbackend: MockBackend;

     beforeEach(async(() => {
         TestBed.configureTestingModule({
        providers: [

          BaseRequestOptions,
          MockBackend,
          Http,
          MySkillService,
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
      myskillservice = testbed.get(MySkillService);
    }
    ));

    function setUpConnections(mockBackend: MockBackend, options: any) {
      mockBackend.connections.subscribe((connection: MockConnection) => {
        const responseOptions = new ResponseOptions(options);
        const response = new Response(responseOptions);

        connection.mockRespond(response);
      });
    }

     it('to check myskillservice exists', inject([MySkillService], (myskillservice) => {
        expect(myskillservice).toBeDefined();
     }));
     it('to check  getEmployeeSkills by empId', () => {
        setUpConnections(mockbackend, {
            body: [{
                'employeeId': '101',
                'subSkill': {
                    'id': '5',
                    'name': 'Java',
                    'skillId': '1',
                    'ratedUsers': 1
                },
                'rating': 4,
                'lastModifiedDate': 1520580455002
            },
            {
                'employeeId': '101',
                'subSkill': {
                    'id': '6',
                    'name': 'Angular',
                    'skillId': '3',
                    'ratedUsers': 1
                },
                'rating': 5,
                'lastModifiedDate': 1520420725630
            }],
            status: 200
          });

           myskillservice.getEmployeeSkills('101').subscribe((data: EmployeeSkill[]) => {
              expect(data.length).toBe(2);
        });
     });
   //post method
    it('to check saveEmployeeSkill which updates the rating', () => {
       myskillservice.saveEmployeeSkill({
            'employeeId': '101',
            'subSkill': {
                'id': '5',
                'name': 'Java',
                'skillId': '1',
                'ratedUsers': 1
            },
            'rating': 3,
          }).subscribe((data: EmployeeSkill) => {
               expect(data).toBeDefined();
          });
    });
});
