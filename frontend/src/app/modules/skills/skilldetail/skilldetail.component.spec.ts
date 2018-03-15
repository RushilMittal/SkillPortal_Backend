import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkilldetailComponent } from './skilldetail.component';

describe('SkilldetailComponent', () => {
  let component: SkilldetailComponent;
  let fixture: ComponentFixture<SkilldetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkilldetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkilldetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
