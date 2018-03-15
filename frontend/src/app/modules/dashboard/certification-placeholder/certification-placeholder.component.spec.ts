import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificationPlaceholderComponent } from './certification-placeholder.component';

describe('CertificationPlaceholderComponent', () => {
  let component: CertificationPlaceholderComponent;
  let fixture: ComponentFixture<CertificationPlaceholderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CertificationPlaceholderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificationPlaceholderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
