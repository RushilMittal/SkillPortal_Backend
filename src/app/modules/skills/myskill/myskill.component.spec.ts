import {async,
    getTestBed,
    inject,
    TestBed,
    ComponentFixture} from '@angular/core/testing';
  import {BaseRequestOptions,
    Http,
    Response,
    ResponseOptions,
    XHRBackend} from '@angular/http';
  import {
     MockBackend,
     MockConnection
   }from '@angular/http/testing';
  import { MySkillService } from '../../../services/myskillservice.service';
  import { MyskillComponent } from './myskill.component';
  import { NO_ERRORS_SCHEMA, DebugElement } from '@angular/core';
  import { By } from '@angular/platform-browser';



  describe('MySkillComponent', () => {
    //for component testing
     let component: MyskillComponent;
     let fixture: ComponentFixture<MyskillComponent>;
     let h5, button, table, input: HTMLElement;
     beforeEach(async(() => {
     TestBed.configureTestingModule({
          providers: [
      BaseRequestOptions,
      MockBackend,
      Http,
      MySkillService,
      { deps: [MockBackend, BaseRequestOptions],
        provide: Http,
        useFactory: (mockBackend: XHRBackend,
                     defaultOptions: BaseRequestOptions) => {
          return new Http(mockBackend, defaultOptions);
        }
      }],
      declarations: [ MyskillComponent ],
      schemas: [NO_ERRORS_SCHEMA]
   }).compileComponents();

  //for component testing
  fixture = TestBed.createComponent(MyskillComponent);
  component = fixture.componentInstance;
  fixture.detectChanges();
  }
  ));

  it('should create', () => {
    expect(component).toBeDefined();
  });

  it('to check your skill list text exist', () => {
      h5 = fixture.nativeElement.querySelector('h5');
      expect(h5.textContent.trim()).toBe('Your Skill List');
  });
  it('to check explore add new skill button exists', () => {
      input = fixture.nativeElement.querySelector('input');
      expect(input.className).toBe('btn right');

   });

   it('to check whether table exists', () => {
    table = fixture.nativeElement.querySelector('table');
    expect(table).toBeTruthy();
  });

  it('to check explore update button exists', () => {
     button = fixture.nativeElement.querySelector('.btn');
     expect(button).toBeTruthy();
  });
  });

