import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailableCertificationsComponent } from './available-certifications.component';

describe('AvailableCertificationsComponent', () => {
  let component: AvailableCertificationsComponent;
  let fixture: ComponentFixture<AvailableCertificationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AvailableCertificationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvailableCertificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
