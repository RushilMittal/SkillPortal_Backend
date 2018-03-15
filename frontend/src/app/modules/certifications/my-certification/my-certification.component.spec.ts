import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyCertificationComponent } from './my-certification.component';

describe('MyCertificationComponent', () => {
  let component: MyCertificationComponent;
  let fixture: ComponentFixture<MyCertificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyCertificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyCertificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
