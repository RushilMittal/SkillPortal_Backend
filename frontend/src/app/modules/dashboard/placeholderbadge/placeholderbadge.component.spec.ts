import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaceholderbadgeComponent } from './placeholderbadge.component';

describe('PlaceholderbadgeComponent', () => {
  let component: PlaceholderbadgeComponent;
  let fixture: ComponentFixture<PlaceholderbadgeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlaceholderbadgeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaceholderbadgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
