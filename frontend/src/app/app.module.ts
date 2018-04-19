import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
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
// import { EmployeeAuthorizationService } from './services/employee-authorization.service';
import { AuthorizationGuard } from './services/authorization-guard.service';
import { TrainingsNavigationComponent } from './modules/trainings/trainings-navigation/trainings-navigation.component';
import { MyEnrolledTrainingsComponent } from './modules/trainings/my-enrolled-trainings/my-enrolled-trainings.component';
import { AvailableTrainingsComponent } from './modules/trainings/available-trainings/available-trainings.component';
import { SkillGroupComponent } from './modules/skills/skillgroup/skillgroup.component';
import { SkillGroupService } from './services/SkillGroupService.service';
import { SearchTransformPipe } from './modules/search-transform.pipe';
import { SpinnerComponent } from './modules/shared/spinner/spinner.component';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { AuthModule, OidcSecurityService, OpenIDImplicitFlowConfiguration, AuthWellKnownEndpoints } from 'angular-auth-oidc-client';
import { TokenInterceptor } from './Token.interceptor';
import { RedirectComponent } from './redirect/redirect.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
// import { AuthService } from './services/AuthToken.service';



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
    SpinnerComponent

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AuthModule.forRoot(),
    NgbModule.forRoot(),
    AppRoutingModule,
    HttpModule,
    ReactiveFormsModule


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
    OidcSecurityService,
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

    // for Authorization and login guard
    AuthorizationGuard,
   // EmployeeAuthorizationService,
    SkillGroupService,

    // testing purpose

  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor(public oidcSecurityService: OidcSecurityService) {

    const openIDImplicitFlowConfiguration = new OpenIDImplicitFlowConfiguration();
    openIDImplicitFlowConfiguration.response_type = 'query';
    openIDImplicitFlowConfiguration.stsServer =
      'https://login.microsoftonline.com/371cb917-b098-4303-b878-c182ec8403ac/oauth2/authorize';
    openIDImplicitFlowConfiguration.redirect_url =
      'http://localhost:4200/redirect';
    openIDImplicitFlowConfiguration.client_id =
      'edb31c7a-1273-44e8-b0d0-50830aaede35';
    openIDImplicitFlowConfiguration.response_type = 'id_token';
    openIDImplicitFlowConfiguration.storage = localStorage;
    openIDImplicitFlowConfiguration.resource =
      '00000002-0000-0000-c000-000000000000';
    openIDImplicitFlowConfiguration.scope = 'openid';
    openIDImplicitFlowConfiguration.post_logout_redirect_uri =
      'http://localhost:4200';
    openIDImplicitFlowConfiguration.post_login_route = '/dashboard';
    openIDImplicitFlowConfiguration.forbidden_route = '/dashboard';
    openIDImplicitFlowConfiguration.unauthorized_route = '/dashboard';
    openIDImplicitFlowConfiguration.auto_userinfo = false;
    openIDImplicitFlowConfiguration.log_console_warning_active = true;
    openIDImplicitFlowConfiguration.log_console_debug_active = true;
    openIDImplicitFlowConfiguration.max_id_token_iat_offset_allowed_in_seconds = 1200;

    const authWellKnownEndpoints = new AuthWellKnownEndpoints();
    authWellKnownEndpoints.issuer =
      'https://sts.windows.net/371cb917-b098-4303-b878-c182ec8403ac/';
    authWellKnownEndpoints.jwks_uri = 'http://localhost:4200/assets/jwks_uri.json';
    authWellKnownEndpoints.authorization_endpoint =
      'https://login.microsoftonline.com/371cb917-b098-4303-b878-c182ec8403ac/oauth2/authorize';
    authWellKnownEndpoints.token_endpoint =
      'https://login.microsoftonline.com/371cb917-b098-4303-b878-c182ec8403ac/oauth2/token';
    authWellKnownEndpoints.userinfo_endpoint =
      'https://login.microsoftonline.com/371cb917-b098-4303-b878-c182ec8403ac/openid/userinfo';
    authWellKnownEndpoints.end_session_endpoint =
      'https://login.microsoftonline.com/371cb917-b098-4303-b878-c182ec8403ac/oauth2/logout';
    authWellKnownEndpoints.check_session_iframe =
      'https://login.microsoftonline.com/371cb917-b098-4303-b878-c182ec8403ac/oauth2/checksession';

    this.oidcSecurityService.setupModule(
      openIDImplicitFlowConfiguration,
      authWellKnownEndpoints
    );
    console.log('APP STARTING');
  }
}
