import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingListComponent } from './training-list.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { EventService } from '../../../services/event.service';
import { HttpClient, HttpHandler } from '@angular/common/http';


describe('TrainingListComponent', () => {
  let component: TrainingListComponent;
  let fixture: ComponentFixture<TrainingListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingListComponent ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        EventService,
        HttpClient,
        HttpHandler]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
