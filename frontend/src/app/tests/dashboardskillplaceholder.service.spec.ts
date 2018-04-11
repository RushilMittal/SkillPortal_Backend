import { async, getTestBed, inject, TestBed } from '@angular/core/testing';
import { BaseRequestOptions, Http, Response, ResponseOptions, XHRBackend } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';

import { Observable } from 'rxjs/Observable';

import { EmployeeSkillPlaceholder } from '../model/EmployeeSkillPlaceholder';
import { DashBoardSkillPlaceHolderService } from '../services/dashboardskillplaceholder.service';

describe('DashboardSkillPlaceholderService',
  () => {
      let dashboardSkillPlaceholderService: DashBoardSkillPlaceHolderService;
      let mockBackend: MockBackend;

      beforeEach( async(
        () => {
        TestBed.configureTestingModule ({
          providers: [ BaseRequestOptions, MockBackend, Http, DashBoardSkillPlaceHolderService,
            {
              deps: [MockBackend, BaseRequestOptions],
              provide: Http,
              useFactory: (mockXHRBackend: XHRBackend, defaultOptions: BaseRequestOptions) => {
                return new Http(mockXHRBackend, defaultOptions);
              }
            }]
          });
      const testBed = getTestBed();
      mockBackend = testBed.get(MockBackend);
      dashboardSkillPlaceholderService = testBed.get(DashBoardSkillPlaceHolderService);
      }
    ));

    function setUpConnections(mockBackendSetup: MockBackend, options: any) {
        mockBackendSetup.connections.subscribe(
          (mockConnection: MockConnection) => {
            const responseOptions = new ResponseOptions(options);
            const response = new Response(responseOptions);
            mockConnection.mockRespond(response);
          });
      }

      it('to check dashboard service exists', inject([DashBoardSkillPlaceHolderService],
        // tslint:disable-next-line:no-shadowed-variable
        (dashboardSkillPlaceholderService) => { expect(dashboardSkillPlaceholderService).toBeDefined(); })
      );

      it('to check the getemployeeSkillPlaceholder', () => {
          setUpConnections(mockBackend, {
              body: [
                {
                    'numberOfSkillRated': 5,
                    'higestRatedSkill': 'Java',
                    'highestRating': 3,
                    'lastUpdatedPeriod': [0, 5, 3]
                }],
                status: 200
            });
            dashboardSkillPlaceholderService.getemployeeSkillPlaceholder('101')
                .subscribe(
                    (data: EmployeeSkillPlaceholder) => {
                        expect(data[0].numberOfSkillRated).toBe(5),
                        expect(data[0].higestRatedSkill).toBe('Java'),
                        expect(data[0].highestRating).toBe(3),
                        expect(data[0].lastUpdatedPeriod[0]).toBe(0),
                        expect(data[0].lastUpdatedPeriod[1]).toBe(5),
                        expect(data[0].lastUpdatedPeriod[2]).toBe(3);
                    });
      });
});