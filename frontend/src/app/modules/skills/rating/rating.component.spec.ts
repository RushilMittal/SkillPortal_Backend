import { async, ComponentFixture, TestBed, getTestBed } from '@angular/core/testing';
import { RatingComponent } from './rating.component';
import { SubSkill } from '../../../model/SubSkill';
import { EmployeeSkill } from '../../../model/EmployeeSkill';
import { Router } from '@angular/router';

describe('RatingComponent', () => {
  let component: RatingComponent;
  // tslint:disable-next-line:prefer-const
  let fixture: ComponentFixture<RatingComponent>;
  let employeeSkill: EmployeeSkill;
  let h5, list, button: Element;
  // tslint:disable-next-line:prefer-const
  let router: Router;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RatingComponent ],
      })
    .compileComponents();

     employeeSkill = new EmployeeSkill();
     component = new RatingComponent(router, employeeSkill);
}));

  it('should create', () => {
    expect(component).toBeDefined();
  });



  it('to check update your rating text', () => {
      h5 = document.querySelector('h5');
      expect(h5).toBeDefined();
  });

  it('to check list is present', () => {
    list = document.querySelector('.pagination');
    expect(list).toBeDefined();
  });

  it('to check update button', () => {
    button = document.querySelector('.update-rating');
     expect(button).toBeDefined();
  });

  it('to check cancel button', () => {
    button = document.querySelector('button[cancel]');
     expect(button).toBeDefined();
  });
});
