import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Renderer2 } from '@angular/core';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { DashboardLayoutComponent } from './modules/dashboard/dashboard-layout/dashboard-layout.component';
import { DashLayoutComponent } from './modules/dash-layout/dash-layout/dash-layout.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GlobalSearchListComponent } from './modules/dash-layout/global-search-list/global-search-list.component';
import { AddNewCertificateService } from './services/addnewcertificate.service';
import { NewCertificationService } from './services/newcertification.service';
import { AllCertificationService } from './services/allcertification.service';
import { MyCertificationService } from './services/mycertification.service';
import { CertificationNavigationComponent } from './modules/certifications/certification-navigation/certification-navigation.component';
import { MyCertificationComponent } from './modules/certifications/my-certification/my-certification.component';
import { AvailableCertificationsComponent } from './modules/certifications/available-certifications/available-certifications.component';
import { AddCertificationComponent } from './modules/certifications/add-certification/add-certification.component';
import { NewCertificationComponent } from './modules/certifications/new-certification/new-certification.component';
import { NotificationMarqueeComponent } from './modules/dashboard/notification-marquee/notification-marquee.component';
import { PlaceholderbadgeComponent } from './modules/dashboard/placeholderbadge/placeholderbadge.component';
import { SkillsPlaceholderComponent } from './modules/dashboard/skills-placeholder/skills-placeholder.component';
import { CertificationPlaceholderComponent } from './modules/dashboard/certification-placeholder/certification-placeholder.component';
import { TrainingPlaceholderComponent } from './modules/dashboard/training-placeholder/training-placeholder.component';
import { DashboardCertificationPlaceholderService } from './services/dashboardcertificationplaceholder.service';
import { DashboardTrainingPlaceholder } from './services/dashboardtrainingplaceholder.service';
import { DashBoardSkillPlaceHolderService } from './services/dashboardskillplaceholder.service';
import { SkillnavigationComponent } from './modules/skills/skillnavigation/skillnavigation.component';
import { AllskillComponent } from './modules/skills/allskill/allskill.component';
import { MyskillComponent } from './modules/skills/myskill/myskill.component';
import { SkilldetailComponent } from './modules/skills/skilldetail/skilldetail.component';
import { RatingComponent } from './modules/skills/rating/rating.component';
import { SearchListComponent } from './modules/skills/search/search-list/search-list.component';
import { SearchPageComponent } from './modules/skills/search/search-page/search-page.component';
import { EmployeeSkill } from './model/EmployeeSkill';
import { AllSkillService } from './services/allskillservice.service';
import { AllSubSkillService } from './services/allsubskillservice.service';
import { IdService } from './services/idservice.service';
import { MySkillService } from './services/myskillservice.service';
import { SearchService } from './services/search.service';
import { MySubSkillService } from './services/mysubskillservice.service';
import { LoginComponent } from './modules/login/login.component';
import { AuthorizationGuard } from './services/authorization-guard.service';
import { TrainingsNavigationComponent } from './modules/trainings/trainings-navigation/trainings-navigation.component';
import { MyEnrolledTrainingsComponent } from './modules/trainings/my-enrolled-trainings/my-enrolled-trainings.component';
import { AvailableTrainingsComponent } from './modules/trainings/available-trainings/available-trainings.component';
import { SkillGroupComponent } from './modules/skills/skillgroup/skillgroup.component';
import { SkillGroupService } from './services/SkillGroupService.service';
import { SearchTransformPipe } from './modules/search-transform.pipe';

import { LocationStrategy, HashLocationStrategy } from '@angular/common';

import { TokenInterceptor } from './Token.interceptor';
import { RedirectComponent } from './redirect/redirect.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { TrainingListPipe } from './modules/trainings/training-list/training-list-pipe.pipe';
import { AddTrainingComponent } from './modules/trainings/add-training/add-training.component';
import { CalendarModule } from 'angular-calendar';
import {CalendarComponent} from "ap-angular2-fullcalendar";
import { EventService } from './services/event.service';
import { AddNewTrainingService } from './services/addnewtraining.service';
import { TrainingListComponent } from './modules/trainings/training-list/training-list.component';
import { TrainingCalenderComponent } from './modules/trainings/training-calender/training-calender.component';
import {MatIconModule} from '@angular/material/icon';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AvailableTrainingService } from './services/availabletraining.service';
import { Ng2IziToastModule } from 'ng2-izitoast';
import { ToastService } from './services/toast.service';
import { query } from '@angular/animations';
import { ErrorHandler } from './services/handleerror.service';
import { AvailableTrainingPipe } from './modules/trainings/available-trainings/available-trainings-pipe.pipe';
import { AuthHelper } from './services/authHelper.service';
import { EmployeeService } from './services/employee.service';
import { EncrptionService } from './services/encryption.service';







@NgModule({
  declarations: [
    AppComponent,
    DashLayoutComponent,
    GlobalSearchListComponent,
    PageNotFoundComponent,
    SearchTransformPipe,
    // for certifications feature
    CertificationNavigationComponent,
    MyCertificationComponent,
    AvailableCertificationsComponent,
    AddCertificationComponent,
    NewCertificationComponent,
    RedirectComponent,

    // for DashBoard feature
    NotificationMarqueeComponent,
    PlaceholderbadgeComponent,
    SkillsPlaceholderComponent,
    CertificationPlaceholderComponent,
    DashboardLayoutComponent,
    TrainingPlaceholderComponent,

    // for Skills feature
    SkillnavigationComponent,
    SkillGroupComponent,
    AllskillComponent,
    MyskillComponent,
    SkilldetailComponent,
    RatingComponent,
    SearchListComponent,
    SearchPageComponent,
    LoginComponent,
    // for training module
    TrainingsNavigationComponent,
    MyEnrolledTrainingsComponent,
    AvailableTrainingsComponent,
    
    CalendarComponent,
    TrainingListComponent,
    TrainingCalenderComponent,
    TrainingListPipe,
    AddTrainingComponent,
    AvailableTrainingPipe
    
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    NgbModule.forRoot(),
    AppRoutingModule,
    ReactiveFormsModule,
    CalendarModule,
    MatIconModule,
    HttpModule,
    Ng2IziToastModule
   
    

  ],
  exports: [
    RatingComponent,
    MyskillComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    
    AuthHelper,
    EmployeeService,
    EncrptionService,
    // Providers for certification Services
    MyCertificationService,
    AllCertificationService,
    NewCertificationService,
    AddNewCertificateService,
    DashBoardSkillPlaceHolderService,
    DashboardTrainingPlaceholder,
    DashboardCertificationPlaceholderService,
    // Providers for Skills Services
   // AuthService,
    AllSkillService,
    AllSubSkillService,
    IdService,
    MySkillService,
    MySubSkillService,
    SearchService,
    EmployeeSkill,
    EventService,
    AddNewTrainingService,
    AvailableTrainingService,
    // for Authorization and login guard
    AuthorizationGuard,
   // EmployeeAuthorizationService,
    SkillGroupService,
    // For displaying the toast
    ToastService,
    // testing purpose
    ErrorHandler

  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor() {}
}