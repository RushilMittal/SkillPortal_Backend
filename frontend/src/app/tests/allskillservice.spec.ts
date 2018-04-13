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
//        } from '@angular/http/testing';
// import { AllSkillService } from '../services/allskillservice.service';
// import { Skill } from '../model/Skill';
//     describe('AllSkillService', () => {
//        let allSkillService: AllSkillService;
//        let mockbackend: MockBackend;

//      beforeEach(async(() => {
//          TestBed.configureTestingModule({
//         providers: [

//           BaseRequestOptions,
//           MockBackend,
//           Http,
//           AllSkillService,
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
//       allSkillService = testbed.get(AllSkillService);
//     }
//     ));

//     function setUpConnections(mockBackend: MockBackend, options: any) {
//       mockBackend.connections.subscribe((connection: MockConnection) => {
//         const responseOptions = new ResponseOptions(options);
//         const response = new Response(responseOptions);
//         connection.mockRespond(response);
//       });
//     }
//      it('to check allskillservice exists', inject([AllSkillService], (allSkillService) => {
//       expect(allSkillService).toBeDefined();
//      }));
//      it('to check getAllSkill', () => {
//             allSkillService.getAllSkill().subscribe((data: Skill) => {
//             expect(data).toBeDefined();
//           });

//        });
//        it('to check getSkillById', () => {
//              setUpConnections(mockbackend, {
//              body: [
//               {
//                    'id': '1',
//                    'name': 'Programming',
//                    'ratedUsers': 1
//               },
//               {
//                   'id': '2',
//                   'name': 'AWS',
//                   'ratedUsers': 0
//               },
//               {
//                    'id': '3',
//                    'name': 'Front End',
//                    'ratedUsers': 0
//               }
//              ],
//            status: 200
//          });
//         allSkillService.getSkillById('1').subscribe((data: Skill) => {
//             expect(data[0].id).toBe('1');
//             expect(data[0].name).toBe('Programming');
//             expect(data[0].ratedUsers).toBe(1);
//         });

//       });

// });
