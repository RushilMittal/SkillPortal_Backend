import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { MyskillComponent } from './myskill.component';
import { MySkillService } from '../../../services/myskillservice.service';



describe('MyskillComponent', () => {
  let component: MyskillComponent;
  let fixture: ComponentFixture<MyskillComponent>;
  let table, button: HTMLElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyskillComponent ],
      providers: [
        MySkillService,
        HttpClient,
        HttpHandler],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyskillComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('to check whether table exists', () => {
    table = fixture.nativeElement.querySelector('table');
    expect(table).toBeTruthy();
});


it('to check explore add new modelSkill button exists', () => {
  button = fixture.nativeElement.querySelector('#back-to-modelSkill');
      expect(button).toBeDefined();
  });

it('to check explore update rating button exists', () => {
button = fixture.nativeElement.querySelector('#update-button');
    expect(button).toBeDefined();
});
});
