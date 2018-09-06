import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillnavigationComponent } from './skillnavigation.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('SkillnavigationComponent', () => {
  let component: SkillnavigationComponent;
  let fixture: ComponentFixture<SkillnavigationComponent>;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillnavigationComponent ],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillnavigationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // it('to check all modelSkill navigation', () => {
  //   a = fixture.nativeElement.querySelector('#myskill');
  //   expect(a.textContent.trim()).toBe('My Skills');
  // });

  // it('to check my modelSkill navigation', () => {
  //   a = fixture.nativeElement.querySelector('#allskill');
  //   expect(a.textContent.trim()).toBe('All Skills');
  // });
});
