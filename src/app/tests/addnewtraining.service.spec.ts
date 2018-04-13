
import { async, getTestBed, inject, TestBed } from '@angular/core/testing';
import { BaseRequestOptions, Http, Response, ResponseOptions, XHRBackend } from '@angular/http';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { Observable } from 'rxjs/Observable';
import { AddNewTrainingService } from '../services/addnewtraining.service';
import { HttpClient } from '@angular/common/http';
import { TrainingDomain } from '../model/training-domain';


describe('AddNewTrainingService',
  () => {
      let addNewTrainingService: AddNewTrainingService;
      let mockBackend: MockBackend;

      beforeEach( async(
        () => {
        TestBed.configureTestingModule ({
          providers: [ BaseRequestOptions, MockBackend, HttpClient, AddNewTrainingService,
            {
              deps: [MockBackend, BaseRequestOptions],
              provide: HttpClient,
              useFactory: (mockXHRBackend: XHRBackend, defaultOptions: BaseRequestOptions) => {
                return new Http(mockXHRBackend, defaultOptions);
              }
            }]
          });
      const testBed = getTestBed();
      mockBackend = testBed.get(MockBackend);
      addNewTrainingService = testBed.get(AddNewTrainingService);
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

    it('to check addnewTrainingservice exists', inject([AddNewTrainingService],
            (addnewtraining) => { expect(addnewtraining).toBeDefined(); })
    );

    it('to check saveNewCertification', () => {
        addNewTrainingService.saveNewTraining(
            {
                'training': {
                    'id': '1',
                    'name': 'Java',
                    'location': 'Conference - 1',
                    'seats': 20,
                    'type': 'Technical',
                    'description': 'Traning for',
                    'trainer': 'A'
                },
                'trainingSessions': [
                    {
                        'trainingId': 'x',
                        'trainingDate': new Date(),
                        'startTime': '1530',
                        'endTime': '1630'
                    },
                    {
                        'trainingId': 'x',
                        'trainingDate': new Date(),
                        'startTime': '1630',
                        'endTime': '1730'
                    }
                ]
             }).subscribe(
          (data: TrainingDomain) => {
          expect(data[0].training.id).toBe('1'),
          expect(data[0].training.name).toBe('java'),
          expect(data[0].training.location).toBe('Conference - 1'),
          expect(data[0].training.seats).toBe(20),
          expect(data[0].training.type).toBe('Technical'),
          expect(data[0].training.description).toBe('Training for'),
          expect(data[0].training.trainer).toBe('A'),
          expect(data[0].trainingSessions.trainingDate).toBe(new Date()),
          expect(data[0].trainingSessions.startTime).toBe('1630'),
          expect(data[0].trainingSessions.endTime).toBe('1730');
        });
  });

  });
