import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyEnrolledTrainingsComponent } from './my-enrolled-trainings.component';

describe('MyEnrolledTrainingsComponent', () => {
  let component: MyEnrolledTrainingsComponent;
  let fixture: ComponentFixture<MyEnrolledTrainingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyEnrolledTrainingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyEnrolledTrainingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
