import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CertificationNavigationComponent } from './certification-navigation/certification-navigation.component';
import { MyCertificationComponent } from './my-certification/my-certification.component';
import { AvailableCertificationsComponent } from './available-certifications/available-certifications.component';
import { AddCertificationComponent } from './add-certification/add-certification.component';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { AllskillComponent } from '../skills/allskill/allskill.component';
import {MyCertificationService} from '../../services/mycertification.service';

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    RouterModule.forChild([
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
      }
    ])
  ],
  declarations: [CertificationNavigationComponent, MyCertificationComponent, AvailableCertificationsComponent, AddCertificationComponent],
  providers: [MyCertificationService]
})
export class CertificationsModule { }
