import { Component, OnInit } from '@angular/core';
import { AdminServices } from '../../../services/adminService';
import { Ng2SmartTableModule, LocalDataSource } from 'ng2-smart-table';
import { SubSkill } from '../../../model/SubSkill';

@Component({
  selector: 'app-adminskill',
  templateUrl: './adminskill.component.html',
  styleUrls: ['./adminskill.component.css']
})
export class AdminskillComponent implements OnInit {
  source: SubSkill;
  settings = {
    actions: {
      delete: false
    },
    add: {
      confirmCreate: true,
    },
    edit: {
      confirmSave: true,
    },
    columns: {
      id: {
        title: 'ID',
        editable: false
      },
      subSkill: {
        title: 'Sub Skill'
      },
      skill: {
        title: 'Skill'
      },
      skillGroup: {
        title: 'Skill Group'
      },
      subSkillDesc: {
        title: 'Sub Skill Description'
      }

    },
    pager: {
      display: true,
      perPage: 10
    }
  };

  constructor(private adminService: AdminServices) { }

  ngOnInit() {

    this.adminService.getAllAdminSkill().subscribe(
      data => {
        this.source = data;

      }, error => {
        console.log("error error" + error);
      }
    );
  }

  onSaveConfirm(event) {
    console.log(event.data);
    // event.newData['name'] += ' + added in code';
    // event.confirm.resolve(event.newData);

    // event.confirm.reject();

  }
  //method called on the addition of new row.
  onCreateConfirm(event) {
    if (window.confirm('Are you sure you want to create?')) {
      event.newData['name'] += ' + added in code';
      event.confirm.resolve(event.newData);
    } else {
      event.confirm.reject();
    }
  }
  //method used to validate the data received on save or edit
  validateDate(event): boolean {
    return false;
  }
}
