import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllskillComponent } from './allskill.component';

describe('AllskillComponent', () => {
  let component: AllskillComponent;
  let fixture: ComponentFixture<AllskillComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllskillComponent ]
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
});
