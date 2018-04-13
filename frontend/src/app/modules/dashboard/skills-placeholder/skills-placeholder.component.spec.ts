
import { TestBed, async, ComponentFixture } from '@angular/core/testing';
import { AllSkillService } from '../../../services/allskillservice.service';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { SkillsPlaceholderComponent } from './skills-placeholder.component';
import { DashBoardSkillPlaceHolderService } from '../../../services/dashboardskillplaceholder.service';

describe('SkillPlaceholderComponent', () => {
  let component: SkillsPlaceholderComponent;
  let fixture: ComponentFixture<SkillsPlaceholderComponent>;
  let div, p , a: HTMLElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillsPlaceholderComponent ],
      providers: [
        DashBoardSkillPlaceHolderService,
        HttpClient,
        HttpHandler],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillsPlaceholderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeDefined();
  });
  it('to check whether card exists', () => {
      div = fixture.nativeElement.querySelector('.card');
      expect(div).toBeTruthy();
  });

  it('to check paragraph exists', () => {
    p = fixture.nativeElement.querySelector('p');
    expect(p).toBeTruthy();
 });
 it('to check view detail button exists', () => {
   a = fixture.nativeElement.querySelector('a');
      expect(a.textContent).toBe('View Details');
 });

 });
