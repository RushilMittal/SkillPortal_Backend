import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { SkillGroupComponent } from './skillgroup.component';
import { SkillGroupService } from '../../../services/SkillGroupService.service';
describe('SkillGroupComponent', () => {
  let component: SkillGroupComponent;
  let fixture: ComponentFixture<SkillGroupComponent>;
  let h5, span: HTMLElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillGroupComponent ],
      providers: [
        SkillGroupService,
        HttpClient,
        HttpHandler],
      schemas: [NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  }));
  beforeEach(() => {
    fixture = TestBed.createComponent(SkillGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('to check heading text', () => {
    h5 = document.querySelector('h5');
    expect(h5).toBeTruthy();
});
it('to check spinner exists', () => {
  span = fixture.nativeElement.querySelector('#spinner');
      expect(span).toBeDefined();
  });

});
