import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CertificationNavigationComponent } from './certification-navigation.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
describe('CertificationNavigationComponent', () => {
  let component: CertificationNavigationComponent;
  let fixture: ComponentFixture<CertificationNavigationComponent>;
  let div, button, ul, anchor: HTMLElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CertificationNavigationComponent ],
      schemas: [NO_ERRORS_SCHEMA]
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
  it('to check div exists', () => {
    div = fixture.nativeElement.querySelector('div');
    expect(div).toBeTruthy();
  });
  it('to check button exists', () => {
    button = fixture.nativeElement.querySelector('button');
    expect(button.textContent.trim()).toBe('Request for Certification');
  });
  it('to check unordered list exists', () => {
    ul = fixture.nativeElement.querySelector('#certification-nav');
    expect(ul).toBeTruthy();
  });
  it('to check anchor exists', () => {
    anchor = fixture.nativeElement.querySelector('#mycertifications');
    expect(anchor.textContent.trim()).toBe('My Certifications');
  });
  it('to check anchor2 exists', () => {
    anchor = fixture.nativeElement.querySelector('#availablecertifications');
    expect(anchor.textContent.trim()).toBe('Available Certifications');
  });
});
