import { Observable } from 'rxjs/';
import {async, getTestBed, inject, TestBed, ComponentFixture} from '@angular/core/testing';
import {BaseRequestOptions, Http, Response, ResponseOptions, XHRBackend} from '@angular/http';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AddTrainingComponent } from './add-training.component';
import { AddNewTrainingService } from '../../../services/addnewtraining.service';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';


describe('AddTrainingComponent', () => {
  let component: AddTrainingComponent;
  let fixture: ComponentFixture<AddTrainingComponent>;
  let  input, button;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      providers: [
        BaseRequestOptions,
        AddNewTrainingService,
        HttpClient,
      HttpHandler,
    FormBuilder],
      declarations: [ AddTrainingComponent ],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
    fixture = TestBed.createComponent(AddTrainingComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
  }));



  it('should create', () => {
    expect(component).toBeDefined();
  });

  it('to check search training name input exists', () => {
    input = fixture.nativeElement.querySelector('#trainingNameId');
    expect(input).toBeDefined();
 });

 it('to check search description input exists', () => {
  input = fixture.nativeElement.querySelector('#descriptionId');
  expect(input).toBeDefined();
});

it('to check search location input exists', () => {
  input = fixture.nativeElement.querySelector('#locationId');
  expect(input).toBeDefined();
});

it('to check search trainer name input exists', () => {
  input = fixture.nativeElement.querySelector('#trainerNameId');
  expect(input).toBeDefined();
});

it('to check search seat input exists', () => {
  input = fixture.nativeElement.querySelector('#seatsId');
  expect(input).toBeDefined();
});

it('to check search date input exists', () => {
  input = fixture.nativeElement.querySelector('#trainingDateId');
  expect(input).toBeDefined();
});

it('to check search start time input exists', () => {
  input = fixture.nativeElement.querySelector('#trainingStartTimeId');
  expect(input).toBeDefined();
});

it('to check search end time input exists', () => {
  input = fixture.nativeElement.querySelector('#trainingEndTimeId');
  expect(input).toBeDefined();
});

it('to check submit button exists', () => {
  button = fixture.nativeElement.querySelector('#save-training');
      expect(button).toBeDefined();
});

it('to check add button exists', () => {
  button = fixture.nativeElement.querySelector('#trainingDate');
      expect(button).toBeDefined();
});

});
