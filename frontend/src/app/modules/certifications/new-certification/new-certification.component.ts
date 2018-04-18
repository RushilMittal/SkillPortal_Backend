import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { Location } from '@angular/common';

import { Certification } from '../../../model/Certification';
import { Skill } from '../../../model/Skill';
import { AllSkillService } from '../../../services/allskillservice.service';
import { NewCertificationService } from '../../../services/newcertification.service';
import { Router } from '@angular/router';
import { SubSkill } from '../../../model/SubSkill';


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
    private route: Router) { }

  ngOnInit() {

    // form model, match wd html input elements
    this.newCertificationForm = new FormGroup({
      skillName: new FormControl(),
      certificationName: new FormControl(),
      institutionName: new FormControl()
    });

    this.allSkillService.getAllSkillsData()
      .subscribe(
        skillGroup => {
          this.skills = skillGroup;
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
    // console.log(this.newCertificationForm);
    // console.log(JSON.stringify(this.newCertificationForm.value));

    this.saveCertification((this.newCertificationForm.get('skillName').value));
    this.saveClicked.emit();
  }

  // Helper method: uses forwarded skill to make post call
  saveCertification(skillForwarded: string) {

    // console.log('Printing Skill Forwarded: ' + skillForwarded.id + ' ' + skillForwarded.name + ' ' + skillForwarded.ratedUsers) ;

    // Providing value to data model Certification
    this.certification.skillId = skillForwarded;
    this.certification.certificationName = this.newCertificationForm.get('certificationName').value;
    this.certification.institution = this.newCertificationForm.get('institutionName').value;

    // Calling the new_certification_service method

    this.newCertificationService.saveNewCertification(this.certification)
      .subscribe(
        () => console.log('Certification Passed to Certification API'),
        (error: any) => this.errorMessage = <any>error
      );

    // window.location.reload(); // Page Refresh : new
  }


  gettingKeys() {

    this.keys = this.temp.keys();
    console.log(this.keys);
  }
}
