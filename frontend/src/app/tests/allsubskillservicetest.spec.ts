import { TestBed, inject } from '@angular/core/testing';
import { HttpEvent, HttpEventType } from '@angular/common/http';

import {
  HttpClientTestingModule,
  HttpTestingController
} from '@angular/common/http/testing';
import { AllSubSkillService } from '../services/allsubskillservice.service';
import { SubSkill } from '../model/SubSkill';


describe('allsubskillservice', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AllSubSkillService]
    });
  });

  it(
    'check whether event service exists',
    inject(
      [HttpTestingController, AllSubSkillService],
      (
        httpMock: HttpTestingController,
        eventService: AllSubSkillService
      ) => {
       expect(eventService).toBeDefined();
      }
    )
  );

  it(
    'check getEvents',
    inject(
      [HttpTestingController, AllSubSkillService],
      (
        httpMock: HttpTestingController,
        eventService: AllSubSkillService
      ) => {
      eventService.getSubSkill().subscribe((data: SubSkill) => {
        expect(data).toBeDefined();
        expect(data[1].totalNumberofRatedUsers).toEqual('6');
      });

      const req = httpMock.expectOne(`http://10.188.27.105:8745/skillportal-0.0.1/modelSkill/all`);
      expect(req.request.method).toBe('GET');
      req.flush([
        {
          'id': '1' ,
          'subSkill': 'Project/Program vision',
          'modelSubSkillDesc': 'Elicit or identify the vision as applicable',
          'modelSkill': 'Analysis',
          'modelSkillGroup': 'BPMG',
          'modelPractice': 'ADM',
          'totalNumberofRatedUsers': '2'
        },
        {
          'id': '1' ,
          'subSkill': 'Project/Program vision',
          'modelSubSkillDesc': 'Derive the project scope',
          'modelSkill': 'Analysis',
          'modelSkillGroup': 'BPMG',
          'modelPractice': 'ADM',
          'totalNumberofRatedUsers': '6'
        }
    ]);

      }
    )
  );



});
