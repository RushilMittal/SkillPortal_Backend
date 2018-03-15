import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationMarqueeComponent } from './notification-marquee.component';

describe('NotificationMarqueeComponent', () => {
  let component: NotificationMarqueeComponent;
  let fixture: ComponentFixture<NotificationMarqueeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NotificationMarqueeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationMarqueeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
