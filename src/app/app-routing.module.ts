import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found.component';
import { SkillnavigationComponent } from './modules/skills/skillnavigation/skillnavigation.component';
import { MyskillComponent } from './modules/skills/myskill/myskill.component';
import { CertificationNavigationComponent } from './modules/certifications/certification-navigation/certification-navigation.component';
import { DashboardLayoutComponent } from './modules/dashboard/dashboard-layout/dashboard-layout.component';
import { MyCertificationComponent } from './modules/certifications/my-certification/my-certification.component';
import { AvailableCertificationsComponent } from './modules/certifications/available-certifications/available-certifications.component';
import { AddCertificationComponent } from './modules/certifications/add-certification/add-certification.component';
import { AllskillComponent } from './modules/skills/allskill/allskill.component';
import { SearchPageComponent } from './modules/skills/search/search-page/search-page.component';
import { SkilldetailComponent } from './modules/skills/skilldetail/skilldetail.component';
import { TrainingsNavigationComponent } from './modules/trainings/trainings-navigation/trainings-navigation.component';
import { MyEnrolledTrainingsComponent } from './modules/trainings/my-enrolled-trainings/my-enrolled-trainings.component';
import { AvailableTrainingsComponent } from './modules/trainings/available-trainings/available-trainings.component';
import { TrainingListComponent } from './modules/trainings/training-list/training-list.component';
import { TrainingCalenderComponent } from './modules/trainings/training-calender/training-calender.component';
const ROUTES: Routes = [
  {
    path: 'dashboard',
    component: DashboardLayoutComponent
  },
  {
    path: 'skills',
    component: SkillnavigationComponent,
    children: [
      {
        path: '',
        redirectTo: 'myskill',
        pathMatch: 'full'
      },
      {
        path: 'myskill',
        component: MyskillComponent
      },
      {
        path: 'allskill',
        component: AllskillComponent
      },
      {
        path: 'searchskill',
        component: SearchPageComponent
      },
      {
        path: 'allskill/explore/:id',
        component: SkilldetailComponent
      }
    ]
  },
  {
    path: 'certifications',
    component: CertificationNavigationComponent,
    children: [
      {
        path: '',
        redirectTo: 'mycertifications',
        pathMatch: 'full'
      },
      {
        path: 'mycertifications',
        component: MyCertificationComponent
      },
      {
        path: 'availablecertifications',
        component: AvailableCertificationsComponent
      },
      {
        path: 'availablecertifications/add',
        component: AddCertificationComponent
      }

    ]
  },
  {
    path: 'trainings',
    component: TrainingsNavigationComponent,
    children: [
      {
        path: '',
        redirectTo: 'myenrolledtrainings',
        pathMatch: 'full'
      },
      {
        path: 'myenrolledtrainings',
        component: MyEnrolledTrainingsComponent,
        children: [
          {
            path: '',
            redirectTo: 'traininglist',
            pathMatch: 'full'

          },
          {
            path: 'traininglist',
            component: TrainingListComponent

          },
          {
            path: 'trainingcalender',
            component: TrainingCalenderComponent
          }
        ]
      },
      {
        path: 'availabletrainings',
        component: AvailableTrainingsComponent
      }

    ]
  },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(ROUTES)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
