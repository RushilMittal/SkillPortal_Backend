import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found.component';
import { SkillsModule } from './modules/skills/skills.module';
import { ReportsModule } from './modules/reports/reports.module';
import { CertificationsModule } from './modules/certifications/certifications.module';
import { DashboardModule } from './modules/dashboard/dashboard.module';
import { TrainingsModule } from './modules/trainings/trainings.module';
import { ModulesModule } from './modules/modules.module';
import { HttpClientModule } from '@angular/common/http';
import { DashboardLayoutComponent } from './modules/dashboard/dashboard-layout/dashboard-layout.component';
import { DashLayoutComponent } from './modules/dash-layout/dash-layout/dash-layout.component';
import { AppRoutingModule } from './app-routing.module';
import { GlobalSearchListComponent } from './modules/dash-layout/global-search-list/global-search-list.component';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    AppComponent,
    DashLayoutComponent,
    GlobalSearchListComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ModulesModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
