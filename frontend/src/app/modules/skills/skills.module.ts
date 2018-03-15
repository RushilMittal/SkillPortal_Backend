import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SkillnavigationComponent } from './skillnavigation/skillnavigation.component';
import { AllskillComponent } from './allskill/allskill.component';
import { MyskillComponent } from './myskill/myskill.component';
import { SkilldetailComponent } from './skilldetail/skilldetail.component';
import { RatingComponent } from './rating/rating.component';
import { RouterModule } from '@angular/router';
import { Component } from '@angular/core';
import { AllSkillService } from '../../services/allskillservice.service';
import { AllSubSkillService } from '../../services/allsubskillservice.service';
import { IdService } from '../../services/idservice.service';
import { MySkillService } from '../../services/myskillservice.service';
import { MySubSkillService } from '../../services/mysubskillservice.service';
import { HttpModule } from '@angular/http';
import { SearchService } from '../../services/search.service';
import { SearchListComponent } from './search/search-list/search-list.component';
import { SearchPageComponent } from './search/search-page/search-page.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    FormsModule,
    RouterModule.forChild([
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
            component : MyskillComponent
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
      }
    ])

  ],

  declarations: [
    SkillnavigationComponent,
    AllskillComponent,
    MyskillComponent,
    SkilldetailComponent,
    RatingComponent,
    SearchListComponent,
    SearchPageComponent

  ],
  exports: [
      RatingComponent,
      MyskillComponent
    ],
  providers: [
    AllSkillService,
    AllSubSkillService,
    IdService,
    MySkillService,
    MySubSkillService,
    SearchService
  ]
})
export class SkillsModule { }
