import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdmincertificateComponent } from './admincertificate.component';

describe('AdmincertificateComponent', () => {
  let component: AdmincertificateComponent;
  let fixture: ComponentFixture<AdmincertificateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdmincertificateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdmincertificateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
