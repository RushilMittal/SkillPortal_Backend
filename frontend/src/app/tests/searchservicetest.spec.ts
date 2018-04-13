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
// import { SearchService } from '../services/search.service';
// import { SearchItem } from '../model/search-item';
// import { HttpClient } from '@angular/common/http';
// import { HttpHandler } from '@angular/common/http';
// import { HttpClientModule } from '@angular/common/http';
// import { Certification } from '../model/Certification';


//  describe('SearchService',()=>{
//        let searchservice:SearchService;
//        let mockbackend:MockBackend;

//      beforeEach(async(()=>{
//          TestBed.configureTestingModule({
//         providers: [
//           BaseRequestOptions,
//           MockBackend,
//           Http,
//           HttpClient,
//           HttpHandler,
//           HttpClientModule,
//           SearchService,
//           { deps: [MockBackend, BaseRequestOptions],
//             provide:Http,
//             useFactory: (mockBackend: XHRBackend,
//                          defaultOptions: BaseRequestOptions) => {
//               return new Http(mockBackend, defaultOptions);
//             }
//           }]
//        });
//       const testbed=getTestBed();
//       mockbackend=testbed.get(MockBackend);
//       searchservice=testbed.get(SearchService);
//     }
//     ));

//     function setUpConnections(mockBackend:MockBackend,options:any){
//       mockBackend.connections.subscribe((connection:MockConnection)=>{
//         const responseOptions=new ResponseOptions(options);
//         const response=new Response(responseOptions);

//         connection.mockRespond(response);
//       });
//     }

//     it('to check searchservice exists',inject([SearchService],(searchservice)=>{
//         expect(searchservice).toBeDefined();
//     }));
//     it('to check getData',()=>{
//         setUpConnections(mockbackend,{
//             body:[
//                 {
//                     "skillId": "1",
//                     "name": "Programming",
//                     "isChild": false,
//                     "subSkillId": null
//                 },
//                 {
//                     "skillId": "2",
//                     "name": "AWS",
//                     "isChild": false,
//                     "subSkillId": null
//                 },
//                 {
//                     "skillId": "1",
//                     "name": "Java",
//                     "isChild": true,
//                     "subSkillId": "1"
//                 },
//                 {
//                     "skillId": "2",
//                     "name": "AWS Device Farm",
//                     "isChild": true,
//                     "subSkillId": "4"
//                 },
//                 {
//                     "skillId": "2",
//                     "name": "AWS Cloud",
//                     "isChild": true,
//                     "subSkillId": "5"
//                 },
//                 {
//                     "skillId": "3",
//                     "name": "Apache",
//                     "isChild": true,
//                     "subSkillId": "7"
//                 },
//                 {
//                     "skillId": "3",
//                     "name": "Angular",
//                     "isChild": true,
//                     "subSkillId": "8"
//                 }
//             ],
//           status:200
//          });
//          searchservice.getData().subscribe((data:SearchItem[])=>{
//             expect(data.length).toBe(7);
//             expect(data[6].subSkillId).toBe('8');
//             expect(data[6].skillId).toBe('3');
//             expect(data[6].name).toBe('Angular');
//             expect(data[6].isChild).toBeTruthy();
//         })
//     })

//     it('to check getCertData',()=>{
//         setUpConnections(mockbackend,{
//             body:[
//                 {
//                     "id": "101",
//                     "skillId": "1",
//                     "certificationName": "Amazon Web Services",
//                     "institution": "Amazon"
//                 },
//                 {
//                     "id": "102",
//                     "skillId": "2",
//                     "certificationName": "AutoCAD",
//                     "institution": "Autodesk"
//                 },
//                 {
//                     "id": "103",
//                     "skillId": "1",
//                     "certificationName": "Linux",
//                     "institution": "RedHat"
//                 },
//                 {
//                     "id": "104",
//                     "skillId": "6",
//                     "certificationName": "Android",
//                     "institution": "Google"
//                 },
//                 {
//                     "id": "106",
//                     "skillId": "1",
//                     "certificationName": "Java",
//                     "institution": "Oracle"
//                 },
//                 {
//                     "id": "107",
//                     "skillId": "10",
//                     "certificationName": "Matlab",
//                     "institution": "Mathworks"
//                 },
//                 {
//                     "id": "108",
//                     "skillId": "3",
//                     "certificationName": "Photoshop",
//                     "institution": "Adobe"
//                 },
//                 {
//                     "id": "109",
//                     "skillId": "9",
//                     "certificationName": "Neural Networks",
//                     "institution": "OpenAI"
//                 },
//                 {
//                     "id": "110",
//                     "skillId": "8",
//                     "certificationName": "Objective-C",
//                     "institution": "Apple"
//                 }
//             ],
//           status:200
//          });
//          searchservice.getCertData().subscribe((data:Certification[])=>{
//             expect(data.length).toBe(9);
//             expect(data[8].id).toBe('110');
//             expect(data[8].skillId).toBe('8');
//             expect(data[8].certificationName).toBe('Objective-C');
//             expect(data[8].institution).toBe('Apple');
//         })
//     })

//  })
