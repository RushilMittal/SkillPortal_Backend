// import {Observable} from 'rxjs/';
// import {async,
//         getTestBed,
//         inject,
//         TestBed} from '@angular/core/testing';
// import {BaseRequestOptions,
//         Http,
//         Response,
//         ResponseOptions,
//         XHRBackend} from '@angular/http';
// import {
//          MockBackend,
//          MockConnection
//        }from '@angular/http/testing';
// import { MySubSkillService } from '../services/mysubskillservice.service';
// import { SubSkill } from '../model/SubSkill';


//  describe('MySubSkillService', () => {
//        let mysubskillservice: MySubSkillService;
//        let mockbackend: MockBackend;

//      beforeEach(async(() => {
//          TestBed.configureTestingModule({
//         providers: [
//           BaseRequestOptions,
//           MockBackend,
//           Http,
//           MySubSkillService,
//           { deps: [MockBackend, BaseRequestOptions],
//             provide: Http,
//             useFactory: (mockBackend: XHRBackend,
//                          defaultOptions: BaseRequestOptions) => {
//               return new Http(mockBackend, defaultOptions);
//             }
//           }]
//        });
//       const testbed = getTestBed();
//       mockbackend = testbed.get(MockBackend);
//       mysubskillservice = testbed.get(MySubSkillService);
//     }
//     ));

//     function setUpConnections(mockBackend: MockBackend, options: any) {
//       mockBackend.connections.subscribe((connection: MockConnection) => {
//         const responseOptions = new ResponseOptions(options);
//         const response = new Response(responseOptions);

//         connection.mockRespond(response);
//       });
//     }

//     it('to check mysubskillservice exists', inject([MySubSkillService], (mysubskillservice) => {
//         expect(mysubskillservice).toBeDefined();
//     }));
//     it('to check  getEmployeeSubSkillExceptRatedSubSkill', () => {
//        setUpConnections(mockbackend, {
//              body: [
//                {
//                    'id': '5',
//                    'name': 'Java',
//                    'skillId': '1',
//                    'ratedUsers': 0
//                 },
//                {
//                 'id': '7',
//                 'name': 'C++',
//                 'skillId': '1',
//                 'ratedUsers': 0
//                }
//              ],
//           status: 200
//          });
//       mysubskillservice.getEmployeeSubSkillExceptRatedSubSkill('101', '1').subscribe((data: SubSkill) => {
//             expect(data[0].id).toBe('5');
//             expect(data[0].name).toBe('Java');
//             expect(data[0].skillId).toBe('1');
//             expect(data[0].ratedUsers).toBe(0);
//         });
//      });

//      it('to check  getEmployeeSubSkillById', () => {
//         setUpConnections(mockbackend, {
//               body: [{
//                 'id': '7',
//                 'name': 'C++',
//                 'skillId': '1',
//                 'ratedUsers': 0
//             }],
//            status: 200
//           });
//        mysubskillservice.getEmployeeSubSkillById('7').subscribe((data: SubSkill) => {
//              expect(data[0].id).toBe('7');
//              expect(data[0].name).toBe('C++');
//              expect(data[0].skillId).toBe('1');
//              expect(data[0].ratedUsers).toBe(0);
//          });
//       });

//  });
