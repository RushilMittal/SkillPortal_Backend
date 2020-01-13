import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { PageNotFoundComponent } from "./page-not-found.component";
import { SkillnavigationComponent } from "./modules/skills/skillnavigation/skillnavigation.component";
import { MyskillComponent } from "./modules/skills/myskill/myskill.component";
import { CertificationNavigationComponent } from "./modules/certifications/certification-navigation/certification-navigation.component";
import { DashboardLayoutComponent } from "./modules/dashboard/dashboard-layout/dashboard-layout.component";
import { MyCertificationComponent } from "./modules/certifications/my-certification/my-certification.component";
import { AvailableCertificationsComponent } from "./modules/certifications/available-certifications/available-certifications.component";
import { AddCertificationComponent } from "./modules/certifications/add-certification/add-certification.component";
import { AllskillComponent } from "./modules/skills/allskill/allskill.component";
import { SearchPageComponent } from "./modules/skills/search/search-page/search-page.component";
import { SkilldetailComponent } from "./modules/skills/skilldetail/skilldetail.component";
import { DashLayoutComponent } from "./modules/dash-layout/dash-layout/dash-layout.component";
import { LoginComponent } from "./modules/login/login.component";
import { AuthorizationGuard } from "./services/authorization-guard.service";
import { TrainingsNavigationComponent } from "./modules/trainings/trainings-navigation/trainings-navigation.component";
import { MyEnrolledTrainingsComponent } from "./modules/trainings/my-enrolled-trainings/my-enrolled-trainings.component";
import { AvailableTrainingsComponent } from "./modules/trainings/available-trainings/available-trainings.component";
import { SkillGroupComponent } from "./modules/skills/skillgroup/skillgroup.component";
import { RedirectComponent } from "./redirect/redirect.component";
import { TrainingListComponent } from "./modules/trainings/training-list/training-list.component";
import { TrainingCalenderComponent } from "./modules/trainings/training-calender/training-calender.component";
import { ReportsComponent } from "./modules/reports/reports.component";
import { AdminskillComponent } from "./modules/admin/adminskill/adminskill.component";
import { IsAdminGuard } from "./services/isadminguard";
import { AdmincertificateComponent } from "./modules/admin/admincertificate/admincertificate.component";
import { AdminmanagerComponent } from "./modules/admin/adminmanager/adminmanager.component";
import { RegisterComponent } from "./modules/register/register.component";

const ROUTES: Routes = [
  // will need this later
  // will create an route for dashboard and the app will be redirected to it

  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  { path: "redirect", component: RedirectComponent },
  {
    path: "",
    component: DashLayoutComponent,
    canActivate: [AuthorizationGuard],
    children: [
      {
        path: "dashboard",
        component: DashboardLayoutComponent
      },
      {
        path: "skills",
        component: SkillnavigationComponent,
        children: [
          {
            path: "",
            redirectTo: "myskill",
            pathMatch: "full"
          },
          {
            path: "skillgroup",
            component: SkillGroupComponent
          },
          {
            path: "myskill",
            component: MyskillComponent
          },

          {
            path: "allskill",
            component: AllskillComponent
          },
          {
            path: "searchskill",
            component: SearchPageComponent
          },
          {
            path: "skillgroup/explore/:id",
            component: SkilldetailComponent
          }
        ]
      },
      {
        path: "certifications",
        component: CertificationNavigationComponent,
        children: [
          {
            path: "",
            redirectTo: "mycertifications",
            pathMatch: "full"
          },
          {
            path: "mycertifications",
            component: MyCertificationComponent
          },
          {
            path: "availablecertifications",
            component: AvailableCertificationsComponent
          },
          {
            path: "availablecertifications/add",
            component: AddCertificationComponent
          }
        ]
      },
      {
        path: "trainings",
        component: TrainingsNavigationComponent,
        children: [
          {
            path: "",
            redirectTo: "myenrolledtrainings",
            pathMatch: "full"
          },
          {
            path: "myenrolledtrainings",
            component: MyEnrolledTrainingsComponent,
            children: [
              {
                path: "",
                redirectTo: "traininglist",
                pathMatch: "full"
              },
              {
                path: "traininglist",
                component: TrainingListComponent
              },
              {
                path: "trainingcalender",
                component: TrainingCalenderComponent
              }
            ]
          },
          {
            path: "availabletrainings",
            component: AvailableTrainingsComponent
          }
        ]
      },
      {
        path: "reports",
        component: ReportsComponent
      },
      {
        path: "adminskill",
        component: AdminskillComponent,
        canActivate: [IsAdminGuard]
      },
      {
        path: "admincertificate",
        component: AdmincertificateComponent,
        canActivate: [IsAdminGuard]
      },
      {
        path: "adminmanager",
        component: AdminmanagerComponent,
        canActivate: [IsAdminGuard]
      },
      { path: "", redirectTo: "dashboard", pathMatch: "full" },
      { path: "**", component: PageNotFoundComponent }
    ]
  },

  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "**", component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(ROUTES)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
