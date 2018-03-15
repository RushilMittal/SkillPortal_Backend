import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingPlaceholderComponent } from './training-placeholder.component';

describe('TrainingPlaceholderComponent', () => {
  let component: TrainingPlaceholderComponent;
  let fixture: ComponentFixture<TrainingPlaceholderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainingPlaceholderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingPlaceholderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
