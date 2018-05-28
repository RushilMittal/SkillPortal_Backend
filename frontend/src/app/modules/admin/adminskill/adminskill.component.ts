import { Component, OnInit } from '@angular/core';
import { AdminServices } from '../../../services/adminService';
import { Ng2SmartTableModule, LocalDataSource } from 'ng2-smart-table';
import { SubSkill } from '../../../model/SubSkill';
import { AllSkillService } from '../../../services/allskillservice.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-adminskill',
  templateUrl: './adminskill.component.html',
  styleUrls: ['./adminskill.component.css']
})
export class AdminskillComponent implements OnInit {
  source: SubSkill[];
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

  constructor(private adminService: AdminServices,
    private toastService: ToastService) { }

  ngOnInit() {
    this.loadSource();

  }
  loadSource() {
    this.adminService.getAllAdminSkill().subscribe(
      data => {
        this.source = data;

      }, error => {
        console.log("error error" + error);
      }
    );
  }

  // method called while updating the new row.
  onSaveConfirm(event) {
    console.log(event.newData);

    if (this.validateData(event)) {
      console.log(event.newData['id']);


      this.adminService.UpdateNewSkill(this.convertToSubskillObject(event.newData['id'], event.newData)).subscribe(
        () => console.log('Product Passed to savefunction'),
        (error: any) => {
          this.toastService.showErrorToast("Unable to Save Some Error Occured");
          event.confirm.reject();
        },
        () => {
          this.toastService.showSuccessToast("Skill Updated");
          event.confirm.resolve(event.newData);
          this.loadSource();
        });
    }
  }

  //method called on the addition of new row.
  onCreateConfirm(event) {
    if (this.validateData(event)) {
      let a = this.source.length + 1;
      console.log("on create" + a.toString());

      this.adminService.SaveNewSkill(this.convertToSubskillObject(a.toString(), event.newData)).subscribe(
        () => console.log('Product Passed to savefunction'),
        (error: any) => {
          this.toastService.showErrorToast("Unable to Save Some Error Occured");
          event.confirm.reject();
        },
        () => {
          this.toastService.showSuccessToast("New Skill Added to the List");
          event.confirm.resolve(event.newData);
          
          this.loadSource();
        });
    }
  }

  //method used to validate the data received on save or edit
  validateData(event): boolean {
    if ((event.newData['subSkill']) || (event.newData['skill']) || (event.newData['skillGroup']) || (event.newData['subSkillDesc'])) {
      return true;
    }
    return false;
  }

  convertToSubskillObject(id: string, event): SubSkill {
    let toReturn = new SubSkill();
    toReturn.id = id;
    toReturn.skill = event['skill'];
    toReturn.skillGroup = event['skillGroup'];
    toReturn.subSkill = event['subSkill'];
    toReturn.subSkillDesc = event['subSkillDesc'];
    toReturn.practice = event['practice'];
    return toReturn;
  }


}
