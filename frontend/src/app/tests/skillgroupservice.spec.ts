import { TestBed, inject } from '@angular/core/testing';
import { SkillGroupService } from '../services/SkillGroupService.service';
import {
  HttpClientTestingModule,
  HttpTestingController
} from '@angular/common/http/testing';


describe('skillgroupservice', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SkillGroupService]
    });
  });

  it(
    'check whether skillgroup service exists',
    inject(
      [HttpTestingController, SkillGroupService],
      (
        httpMock: HttpTestingController,
        eventService: SkillGroupService
      ) => {
       expect(eventService).toBeDefined();
      }
    )
  );

  it(
    'check getData',
    inject(
      [HttpTestingController, SkillGroupService],
      (
        httpMock: HttpTestingController,
        eventService: SkillGroupService
      ) => {
      eventService.getData().subscribe((data) => {
        expect(data).toBeDefined();

        expect(data['Cloud'][0]).toEqual('AWS');
        expect(data['Cloud'][1]).toEqual('Azure');
        expect(data['CMS'][0]).toEqual('Sitecore');


      });

      const req = httpMock.expectOne(`http://10.188.27.105:8745/skillportal-0.0.1/modelSkill/getallskillgroups`);
      expect(req.request.method).toBe('GET');
      req.flush(
        {
          'Cloud': [
              'AWS',
              'Azure'
          ],
          'CMS': [
              'Sitecore'
          ]
        }
    );

      }
    )
  );



});
