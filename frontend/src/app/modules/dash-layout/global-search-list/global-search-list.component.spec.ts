import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalSearchListComponent } from './global-search-list.component';

describe('GlobalSearchListComponent', () => {
  let component: GlobalSearchListComponent;
  let fixture: ComponentFixture<GlobalSearchListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GlobalSearchListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GlobalSearchListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
