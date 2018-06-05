import { Component, OnInit } from '@angular/core';
import { AdminServices } from '../../../services/adminService';
import { ToastService } from '../../../services/toast.service';
import { Role } from '../../../model/Role';

@Component({
  selector: 'app-adminmanager',
  templateUrl: './adminmanager.component.html',
  styleUrls: ['./adminmanager.component.css']
})
export class AdminmanagerComponent implements OnInit {
  source: Role[];
  settings = {
    actions: {
      edit:false
    },
    add: {
      confirmCreate: true,
    },
    delete:{
      confirmDelete: true,
    },
    columns: {
      userRole: {
        title: 'Allowed Users/ Roles/ Job Title'
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
    this.source = null;
    this.adminService.getAllAdminRoles().subscribe(
      data => {
        this.source = data;

      }, error => {
        console.log("error error" + error);
      }
    );
  }

  
  //method called on the addition of new row.
  onCreateConfirm(event) {
    if (this.validateData(event)) {
      let a = this.source.length + 1;
      console.log("on create" + a.toString());

      this.adminService.SaveNewRole(this.convertToRoleObject(event.newData)).subscribe(
        () => console.log('Product Passed to savefunction'),
        (error: any) => {
          this.toastService.showErrorToast("Unable to Save Some Error Occured");
          event.confirm.reject();
        },
        () => {
          this.toastService.showSuccessToast("New Admin Added to the List");
          event.confirm.resolve(event.newData);
          
          this.loadSource();
        });
    }
  }

  //method called on the addition of new row.
  onDeleteConfirm(event) {
    
    if (this.validateDeleteData(event)) {
      let a = this.source.length + 1;
      this.adminService.deleteRole(this.convertToRoleObject(event.data)).subscribe(
        () => console.log('Role Passed to Delete Function'),
        (error: any) => {
          this.toastService.showErrorToast("Unable to Delete Role,Some Error Occured");
          event.confirm.reject();
        },
        () => {
          this.toastService.showSuccessToast("User Role/ email id deleted");
          event.confirm.resolve(event.newData);
          this.loadSource();
        });
    }
  }

  //method used to validate the data received on save or edit
  validateData(event): boolean {
    if (event.newData['userRole']) {
      return true;
    }
    return false;
  }
  
  validateDeleteData(event): boolean{
    if (event.data['userRole']) {
      return true;
    }
    return false;
  }
  convertToRoleObject(event): Role {
    let toReturn = new Role();
    toReturn.id = event['id'];
    toReturn.userRole = event['userRole']; 
    return toReturn;
  }
}
