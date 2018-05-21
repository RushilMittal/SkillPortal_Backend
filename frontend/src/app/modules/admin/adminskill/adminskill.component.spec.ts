import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminskillComponent } from './adminskill.component';

describe('AdminskillComponent', () => {
  let component: AdminskillComponent;
  let fixture: ComponentFixture<AdminskillComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminskillComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminskillComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
