import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from './page-not-found.component';
import { SkillnavigationComponent } from './modules/skills/skillnavigation/skillnavigation.component';
import { MyskillComponent } from './modules/skills/myskill/myskill.component';
import { CertificationNavigationComponent } from './modules/certifications/certification-navigation/certification-navigation.component';
import { DashboardLayoutComponent } from './modules/dashboard/dashboard-layout/dashboard-layout.component';


const ROUTES: Routes = [
  // will need this later
  // will create an route for dashboard and the app will be redirected to it
  {path: 'dashboard', component: DashboardLayoutComponent},
  {path: 'skills', component: SkillnavigationComponent},
  {path: 'certifications', component: CertificationNavigationComponent},
  {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(ROUTES)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
