import { async, TestBed, ComponentFixture } from "@angular/core/testing";
import { DebugElement } from "@angular/core/src/debug/debug_node";
import { HttpClient,HttpHandler } from "@angular/common/http";
import { RouterTestingModule } from "@angular/router/testing";
import { AvailableTrainingPipe } from "../modules/trainings/available-trainings/training-list-pipe.pipe";
import { AvailableTrainingsComponent } from "../modules/trainings/available-trainings/available-trainings.component";
import { AvailableTrainingService } from "../services/availabletraining.service";

describe('To Test Training List',()=>{
  beforeEach(async(()=>{
    TestBed
    .configureTestingModule({
       declarations:[AvailableTrainingPipe,AvailableTrainingsComponent],
       providers:[AvailableTrainingService,HttpClient,HttpHandler],
       imports:[RouterTestingModule]
    })
    .compileComponents();
  }));
  let fixture:ComponentFixture<AvailableTrainingsComponent>;
  let debugElement:DebugElement;
  let component:AvailableTrainingsComponent;
  let button,buttonRight,div,table:HTMLElement,enrollButton,editButton,session;

  beforeEach(()=>{
    fixture=TestBed.createComponent(AvailableTrainingsComponent);
    debugElement=fixture.debugElement;
    component=fixture.componentInstance;
  });
  it('should create an instance',()=>{
    expect(fixture).toBeTruthy();
  })

  it('should check left button',()=>{
    button= fixture.nativeElement.querySelector('#left');
    expect(button).toBeDefined();
  })

  it('should check right button',()=>{
    buttonRight= fixture.nativeElement.querySelector('#right');
    expect(buttonRight).toBeDefined();
  })

  it('should check div',()=>{
    div= fixture.nativeElement.querySelector('#test');
    expect(div).toBeTruthy();
  })

  it('should check table',()=>{
    table= fixture.nativeElement.querySelector('.table');
    expect(table).toBeDefined();
  })

  it('should check enroll button',()=>{
    enrollButton= fixture.nativeElement.querySelector('#enrollment');
    expect(enrollButton).toBeDefined();
  })

  it('should check session button',()=>{
    session= fixture.nativeElement.querySelector('#session');
    expect(session).toBeDefined();
  })

  it('should check edit button',()=>{
    editButton= fixture.nativeElement.querySelector('#new-edit');
    expect(editButton).toBeDefined();
  })

});