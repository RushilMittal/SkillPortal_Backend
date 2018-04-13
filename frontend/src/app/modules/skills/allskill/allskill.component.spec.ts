import { AllskillComponent } from './allskill.component';
import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { AllSkillService } from '../../../services/allskillservice.service';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('allskillComponent', () => {
  let component: AllskillComponent;
  let fixture: ComponentFixture<AllskillComponent>;
  let table, button: HTMLElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllskillComponent ],
      providers: [
        AllSkillService,
        HttpClient,
        HttpHandler],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllskillComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('to check whether table exists', () => {
    table = fixture.nativeElement.querySelector('table');
    expect(table).toBeTruthy();
});


it('to check explore button exists', () => {
  button = fixture.nativeElement.querySelector('#explore-button');
      expect(button).toBeDefined();
  });

 });
