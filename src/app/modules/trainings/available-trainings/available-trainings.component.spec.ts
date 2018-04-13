import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailableTrainingsComponent } from './available-trainings.component';

describe('AvailableTrainingsComponent', () => {
  let component: AvailableTrainingsComponent;
  let fixture: ComponentFixture<AvailableTrainingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AvailableTrainingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvailableTrainingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
