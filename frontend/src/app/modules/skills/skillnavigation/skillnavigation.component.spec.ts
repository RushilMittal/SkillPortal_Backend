import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillnavigationComponent } from './skillnavigation.component';

describe('SkillnavigationComponent', () => {
  let component: SkillnavigationComponent;
  let fixture: ComponentFixture<SkillnavigationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillnavigationComponent ]
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
});
