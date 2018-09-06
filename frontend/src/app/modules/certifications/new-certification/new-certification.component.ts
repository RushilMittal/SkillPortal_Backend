import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { Location } from '@angular/common';

import { Certification } from '../../../model/Certification';
import { Skill } from '../../../model/Skill';
import { AllSkillService } from '../../../services/allskillservice.service';
import { NewCertificationService } from '../../../services/newcertification.service';
import { Router } from '@angular/router';
import { SubSkill } from '../../../model/SubSkill';
import { ToastService } from '../../../services/toast.service';


@Component({
  selector: 'app-new-certification',
  templateUrl: './new-certification.component.html',
  styleUrls: ['./new-certification.component.css']
})

export class NewCertificationComponent implements OnInit {

  newCertificationForm: FormGroup; // defines our form model (root)
  certification: Certification = new Certification('', '', '', ''); // defines our data model

  @Output() saveClicked: EventEmitter<void> = new EventEmitter<void>();

  skills: SubSkill; // to populate selector
  skillSelected: Skill; // selected frm select option
  errorMessage: any;
  temp;
  keys;
  constructor(private allSkillService: AllSkillService,
    private newCertificationService: NewCertificationService,
    private route: Router,
    private toastService: ToastService) { }

  ngOnInit() {

    // form model, match wd html input elements
    this.newCertificationForm = new FormGroup({
      skillName: new FormControl(),
      certificationName: new FormControl(),
      institutionName: new FormControl()
    });

    this.allSkillService.getAllSkillsData()
      .subscribe(modelSkillGroup => {
        this.skills = modelSkillGroup;
        this.temp = new Map();
        for (const key in this.skills) {
          this.temp.set(key, this.skills[key]);
        }
      }
        , (error: any) => this.errorMessage = <any>error,
        () => this.gettingKeys()

      );
  }

  save() {
    this.saveCertification((this.newCertificationForm.get('skillName').value));
    this.saveClicked.emit();
  }

  // Helper method: uses forwarded modelSkill to make post call
  saveCertification(skillForwarded: string) {

    this.certification.skillId = skillForwarded;
    this.certification.certificationName = this.newCertificationForm.get('certificationName').value;
    this.certification.institution = this.newCertificationForm.get('institutionName').value;

    this.newCertificationService.saveNewCertification(this.certification)
      .subscribe(
        () => console.log('Product Passed to savefunction'),
        (error: any) => {
          this.errorMessage = <any>error;
          this.toastService.showErrorToast("Unable to Save Some Error Occured");
        },
        () => {
          this.toastService.showSuccessToast("New Certification Added to the List");
        }
      );
  }


  gettingKeys() {
    this.keys = this.temp.keys();
  }
}
