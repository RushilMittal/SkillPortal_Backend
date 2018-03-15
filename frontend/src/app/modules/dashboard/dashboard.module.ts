import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationMarqueeComponent } from './notification-marquee/notification-marquee.component';
import { PlaceholderbadgeComponent } from './placeholderbadge/placeholderbadge.component';
import { SkillsPlaceholderComponent } from './skills-placeholder/skills-placeholder.component';
import { CertificationPlaceholderComponent } from './certification-placeholder/certification-placeholder.component';
import { DashboardLayoutComponent } from './dashboard-layout/dashboard-layout.component';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { TrainingPlaceholderComponent } from './training-placeholder/training-placeholder.component';

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    RouterModule.forChild([
      {
        path: 'dashboard',
        component: DashboardLayoutComponent
      }
    ])
  ],
  declarations: [
    NotificationMarqueeComponent,
     PlaceholderbadgeComponent,
     SkillsPlaceholderComponent,
     CertificationPlaceholderComponent,
      DashboardLayoutComponent,
     TrainingPlaceholderComponent
  ]
})
export class DashboardModule { }
