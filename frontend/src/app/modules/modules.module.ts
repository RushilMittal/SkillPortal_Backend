import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CertificationsModule } from './certifications/certifications.module';
import { SkillsModule } from './skills/skills.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { TrainingsModule } from './trainings/trainings.module';
import { ReportsModule } from './reports/reports.module';



@NgModule({
  imports: [
    CommonModule,
    DashboardModule,
    SkillsModule,
    CertificationsModule,
    TrainingsModule,
    ReportsModule,
  ],
  declarations: [],
  exports: [
    // CertificationsModule,
    // SkillsModule,
    // DashboardModule,
    // TrainingsModule,
    // ReportsModule

  ]

})
export class ModulesModule { }
