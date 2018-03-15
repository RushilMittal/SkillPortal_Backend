import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificationNavigationComponent } from './certification-navigation.component';

describe('CertificationNavigationComponent', () => {
  let component: CertificationNavigationComponent;
  let fixture: ComponentFixture<CertificationNavigationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CertificationNavigationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificationNavigationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
