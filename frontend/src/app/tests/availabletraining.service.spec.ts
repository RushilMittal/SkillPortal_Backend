import { TestBed, inject } from "@angular/core/testing";
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { HttpEvent, HttpEventType } from '@angular/common/http';
import { AvailableTrainingService } from "../services/availabletraining.service";
import { NO_ERRORS_SCHEMA } from "@angular/core";

describe('AvailableTrainingService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [AvailableTrainingService],
            schemas:[NO_ERRORS_SCHEMA]
        });
    });

    it(
        'check whether event service exists',
        inject(
            [HttpTestingController,AvailableTrainingService],
            (
                httpMock: HttpTestingController,
                eventService: AvailableTrainingService
            ) => {
                expect(eventService).toBeDefined();
            }
        )
    );

    it(
        'check getAllTraining',
        inject(
            [HttpTestingController, AvailableTrainingService],
            (
                httpMock: HttpTestingController,
                availableTrainingService: AvailableTrainingService
            ) => {
                availableTrainingService. getAvailableTraining().subscribe((data:any) => {
                    expect(data[0].training.name).toBe('abc');
                });

                const req = httpMock.expectOne(`http://localhost:4200/assets/AvailableTraining.json`);
                expect(req.request.method).toBe("GET");
                req.flush([
                    {
                        "training": {
                            "id": "5",
                            "name": "abc",
                            "location": "Conference - 1",
                            "seats": 20,
                            "type": "Technical",
                            "description": "Traning for",
                            "trainer": "A A Jane"
                        },
                        "trainingSessions": [
                            {
                                "trainingId": "5",
                                "trainingDate": 1482172200000,
                                "startTime": "1530",
                                "endTime": "1630"
                            },
                            {
                                "trainingId": "5",
                                "trainingDate": 1508697000000,
                                "startTime": "1630",
                                "endTime": "1730"
                            }
                        ]
                       }
                ]);
            }

        )
    );


    it(
        'check enrollTraining',
        inject(
            [HttpTestingController, AvailableTrainingService],
            (
                httpMock: HttpTestingController,
                availableTrainingService: AvailableTrainingService
            ) => {



                availableTrainingService.postEnroll('6').subscribe((data: any) => {
                    expect(data).toBeTruthy();
                    expect(data[0].id).toEqual('6');
                });

                const req = httpMock.expectOne(`http://localhost:8000/training/enroll?trainingId=6`);
                expect(req.request.method).toBe("POST");
                req.flush([{
                    "id":"6"
                 }
                ]);

            }
        )
    );
   
})