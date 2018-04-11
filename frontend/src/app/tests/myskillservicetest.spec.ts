import { TestBed, inject } from '@angular/core/testing';
import { HttpEvent, HttpEventType } from '@angular/common/http';

import {
  HttpClientTestingModule,
  HttpTestingController
} from '@angular/common/http/testing';

import { MySkillService } from '../services/myskillservice.service';
import { EmployeeSkill } from '../model/EmployeeSkill';


describe('MySkillService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MySkillService]
    });
  });

  it(
    'check whether MySkill service exists',
    inject(
      [HttpTestingController, MySkillService],
      (
        httpMock: HttpTestingController,
        eventService: MySkillService
      ) => {
       expect(eventService).toBeDefined();
      }
    )
  );

  it(
    'check getEmployeeSkills',
    inject(
      [HttpTestingController, MySkillService],
      (
        httpMock: HttpTestingController,
        eventService: MySkillService
      ) => {
      eventService.getEmployeeSkills('101').subscribe((data: EmployeeSkill[]) => {
        expect(data).toBeDefined();
        expect(data[2].subSkill.subSkill).toEqual('VPC');
      });
      
      const req = httpMock.expectOne(`http://10.188.27.105:8745/skillportal-0.0.1/skill/getEmployeeSkills?empId=101`);
      expect(req.request.method).toBe("GET");
      req.flush([
        {
            "employeeId": "101",
            "subSkill": {
                "id": "37",
                "subSkill": "Glacier",
                "subSkillDesc": "Glacier",
                "skill": "AWS",
                "skillGroup": "Cloud",
                "practice": "ADM",
                "totalNumberofRatedUsers": 1
            },
            "rating": 2,
            "lastModifiedDate": 1523008178884
        },
        {
            "employeeId": "101",
            "subSkill": {
                "id": "28",
                "subSkill": "EC2",
                "subSkillDesc": "EC2",
                "skill": "AWS",
                "skillGroup": "Cloud",
                "practice": "ADM",
                "totalNumberofRatedUsers": 1
            },
            "rating": 5,
            "lastModifiedDate": 1523008173968
        },
        {
            "employeeId": "101",
            "subSkill": {
                "id": "29",
                "subSkill": "VPC",
                "subSkillDesc": "VPC",
                "skill": "AWS",
                "skillGroup": "Cloud",
                "practice": "ADM",
                "totalNumberofRatedUsers": 1
            },
            "rating": 2,
            "lastModifiedDate": 1523002664562
        }]
    );
      
      }
    )
  );

  

});