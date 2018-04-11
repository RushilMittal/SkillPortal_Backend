import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { TrainingsNavigationComponent } from './trainings-navigation.component';
describe('TrainingNavigationComponent', () => {
  let component: TrainingsNavigationComponent;
  let fixture: ComponentFixture<TrainingsNavigationComponent>;
  let a: HTMLElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingsNavigationComponent ],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  }));
  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingsNavigationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('to check enrolled trainings navigation', () => {
    a = fixture.nativeElement.querySelector('#myenrolledtrainings');
    expect(a.textContent.trim()).toBe('My Enrolled Trainings');
  });
  it('to check all trainings navigation', () => {
    a = fixture.nativeElement.querySelector('#availabletrainings');
    expect(a.textContent.trim()).toBe('Available Trainings');
  });
});
