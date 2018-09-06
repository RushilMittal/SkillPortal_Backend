import { TestBed, inject } from '@angular/core/testing';
import { HttpEvent, HttpEventType } from '@angular/common/http';
import {
  HttpClientTestingModule,
  HttpTestingController
} from '@angular/common/http/testing';
import { EmployeeSkill } from '../model/EmployeeSkill';
import { DashBoardSkillPlaceHolderService } from '../services/dashboardskillplaceholder.service';
import { EmployeeSkillPlaceholder } from '../model/EmployeeSkillPlaceholder';
describe('DashboardSkillPlaceholderService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DashBoardSkillPlaceHolderService]
    });
  });
  it(
    'check whether DashboardSkillPlaceholderService service exists',
    inject(
      [HttpTestingController, DashBoardSkillPlaceHolderService],
      (
        httpMock: HttpTestingController,
        eventService: DashBoardSkillPlaceHolderService
      ) => {
       expect(eventService).toBeDefined();
      }
    )
  );
  it(
    'check getEmployeeSkills',
    inject(
      [HttpTestingController, DashBoardSkillPlaceHolderService],
      (
        httpMock: HttpTestingController,
        dashboardService: DashBoardSkillPlaceHolderService
      ) => {
        dashboardService.getemployeeSkillPlaceholder().subscribe((data: EmployeeSkillPlaceholder) => {
        expect(data).toBeDefined();
        expect(data.numberOfSkillRated).toEqual(20);
        expect(data.higestRatedSkill).toEqual('EC2');
      });
      const req = httpMock.expectOne(`http://10.188.27.105:8745/skillportal-0.0.1/modelSkill/getEmployeeSkillPlaceholder?empId=101`);
      expect(req.request.method).toBe('GET');
      req.flush(
        {
          "numberOfSkillRated":20,
          'higestRatedSkill':'EC2',
          'highestRating':5,
          'lastUpdatedPeriod':[0,0,0]
        }

    );

      }
    )
  );
});
