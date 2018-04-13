import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { Location } from '@angular/common';

import { Certification } from '../../../model/Certification';
import { Skill } from '../../../model/Skill';
import { AllSkillService } from '../../../services/allskillservice.service';
import { NewCertificationService } from '../../../services/newcertification.service';


@Component({
  selector: 'app-new-certification',
  templateUrl: './new-certification.component.html',
  styleUrls: ['./new-certification.component.css']
})

export class NewCertificationComponent implements OnInit {

  newCertificationForm: FormGroup; // defines our form model (root)
  certification: Certification = new Certification('', '', '', ''); // defines our data model

  skills: Skill; // to populate selector
  skillSelected: Skill; // selected frm select option
  errorMessage: any;

  constructor(private allSkillService: AllSkillService,
              private newCertificationService: NewCertificationService) { }

  save() {
    // console.log(this.newCertificationForm);
    // console.log(JSON.stringify(this.newCertificationForm.value));


    this.allSkillService.getSkillByName(this.newCertificationForm.get('skillName').value)
        .subscribe(
          skill => this.skillSelected = skill,
          error => this.errorMessage = error,
          () => this.saveCertification(this.skillSelected) // forwarding the selected skill for further use
        );
  }

  // Helper method: uses forwarded skill to make post call
  saveCertification(skillForwarded: Skill) {

    // console.log('Printing Skill Forwarded: ' + skillForwarded.id + ' ' + skillForwarded.name + ' ' + skillForwarded.ratedUsers) ;

    // Providing value to data model Certification
    this.certification.skillId = skillForwarded.id;
    this.certification.certificationName = this.newCertificationForm.get('certificationName').value;
    this.certification.institution = this.newCertificationForm.get('institutionName').value;

    // Calling the new_certification_service method
    this.newCertificationService.saveNewCertification(this.certification)
        .subscribe(
              () => console.log('Certification Passed to Certification API'),
              (error: any) => this.errorMessage = <any>error
        );

    window.location.reload(); // Page Refresh : new
  }

  ngOnInit() {

    // form model, match wd html input elements
    this.newCertificationForm = new FormGroup({
      skillName: new FormControl(),
      certificationName: new FormControl(),
      institutionName: new FormControl()
    });

    this.allSkillService.getAllSkill()
      .subscribe(
        skill => this.skills = skill,
        error => this.errorMessage = error);
  }
}
