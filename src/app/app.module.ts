import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found.component';
import { HttpClientModule } from '@angular/common/http';
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
import { TrainingsNavigationComponent } from './modules/trainings/trainings-navigation/trainings-navigation.component';
import { AvailableTrainingsComponent } from './modules/trainings/available-trainings/available-trainings.component';
import { MyEnrolledTrainingsComponent } from './modules/trainings/my-enrolled-trainings/my-enrolled-trainings.component';
import {CalendarComponent} from 'ap-angular2-fullcalendar/src/calendar/calendar';
import { CommonModule } from '@angular/common';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule } from 'angular-calendar';
import { EventService } from './services/event.service';
import { TrainingListComponent } from './modules/trainings/training-list/training-list.component';
import { TrainingCalenderComponent } from './modules/trainings/training-calender/training-calender.component';
import {MatIconModule} from '@angular/material/icon';
import { TrainingListPipe } from './modules/trainings/training-list/training-list-pipe.pipe';
import { AddNewTrainingService } from './services/addnewtraining.service';
import { AddTrainingComponent } from './modules/trainings/add-training/add-training.component';

@NgModule({
  declarations: [
    AppComponent,
    DashLayoutComponent,
    GlobalSearchListComponent,
    PageNotFoundComponent,
    // for certifications feature
    CertificationNavigationComponent,
    MyCertificationComponent,
    AvailableCertificationsComponent,
    AddCertificationComponent,
    NewCertificationComponent,
    // for DashBoard feature
    NotificationMarqueeComponent,
    PlaceholderbadgeComponent,
    SkillsPlaceholderComponent,
    CertificationPlaceholderComponent,
    DashboardLayoutComponent,
    TrainingPlaceholderComponent,
    // for Skills feature
    SkillnavigationComponent,
    AllskillComponent,
    MyskillComponent,
    SkilldetailComponent,
    RatingComponent,
    SearchListComponent,
    SearchPageComponent,
     //for training feature
    TrainingsNavigationComponent,
    MyEnrolledTrainingsComponent,
    AvailableTrainingsComponent,
    MyEnrolledTrainingsComponent,
    CalendarComponent,
    TrainingListComponent,
    TrainingCalenderComponent,
    TrainingListPipe,
    AddTrainingComponent


  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    HttpModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    CalendarModule,
    HttpClientModule,
    MatIconModule
  ],
  exports: [
    RatingComponent,
    MyskillComponent
  ],
  providers: [
    // Providers for certification Services
    MyCertificationService,
    AllCertificationService,
    NewCertificationService,
    AddNewCertificateService,
    DashBoardSkillPlaceHolderService,
    DashboardTrainingPlaceholder,
    DashboardCertificationPlaceholderService,
    // Providers for Skills Services
    AllSkillService,
    AllSubSkillService,
    IdService,
    MySkillService,
    MySubSkillService,
    SearchService,
    EmployeeSkill,
     //Training
    EventService,
    AddNewTrainingService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
