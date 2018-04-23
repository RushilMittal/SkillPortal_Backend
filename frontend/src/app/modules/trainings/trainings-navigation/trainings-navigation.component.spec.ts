import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingsNavigationComponent } from './trainings-navigation.component';

describe('TrainingsNavigationComponent', () => {
  let component: TrainingsNavigationComponent;
  let fixture: ComponentFixture<TrainingsNavigationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingsNavigationComponent ]
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
});
