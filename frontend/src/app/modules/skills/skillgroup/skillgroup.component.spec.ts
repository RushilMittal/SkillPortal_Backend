import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillgroupComponent } from './skillgroup.component';

describe('SkillgroupComponent', () => {
  let component: SkillgroupComponent;
  let fixture: ComponentFixture<SkillgroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillgroupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillgroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
