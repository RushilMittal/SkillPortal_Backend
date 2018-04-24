import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardLayoutComponent } from './dashboard-layout.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('DashboardLayoutComponent', () => {
 let component: DashboardLayoutComponent;
 let fixture: ComponentFixture<DashboardLayoutComponent>;
 let div, p, app_skills_placeholder, app_certification_placeholder, app_training_placeholder: HTMLElement;
beforeEach(async(() => {
   TestBed.configureTestingModule({
     declarations: [ DashboardLayoutComponent ],
     schemas: [NO_ERRORS_SCHEMA]
   })
   .compileComponents();
   fixture = TestBed.createComponent(DashboardLayoutComponent);
   component = fixture.componentInstance;
   fixture.detectChanges();
 }));


 it('should create', () => {
   expect(component).toBeTruthy();
 });
 it('to check myDashboard', () => {
     div = fixture.nativeElement.querySelector('#myDashboard');
     expect(div).toBeTruthy();
 });
 it('to check paragraph', () => {
   p = fixture.nativeElement.querySelector('#text');
   expect(p.textContent.trim()).toBe('My Dashboard');
});
it('to check notification', () => {
   div = fixture.nativeElement.querySelector('#notification');
   expect(div).toBeTruthy();
});

it('to check placeholder', () => {
   div = fixture.nativeElement.querySelector('#placeholder');
   expect(div).toBeTruthy();
});
it('to check app-skills-placeholder', () => {
   app_skills_placeholder = fixture.nativeElement.querySelector('app-skills-placeholder');
   expect(app_skills_placeholder).toBeTruthy();
});
it('to check app-certification-placeholder', () => {
   app_certification_placeholder = fixture.nativeElement.querySelector('app-certification-placeholder');
   expect(app_certification_placeholder).toBeTruthy();
});
it('to check app-training-placeholder', () => {
   app_training_placeholder = fixture.nativeElement.querySelector('app-training-placeholder');
   expect(app_training_placeholder).toBeTruthy();
});
});

