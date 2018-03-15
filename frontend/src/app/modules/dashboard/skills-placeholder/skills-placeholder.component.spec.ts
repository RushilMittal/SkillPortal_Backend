import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillsPlaceholderComponent } from './skills-placeholder.component';

describe('SkillsPlaceholderComponent', () => {
  let component: SkillsPlaceholderComponent;
  let fixture: ComponentFixture<SkillsPlaceholderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillsPlaceholderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillsPlaceholderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
