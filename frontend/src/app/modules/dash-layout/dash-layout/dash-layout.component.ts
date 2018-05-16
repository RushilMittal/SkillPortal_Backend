import { Component, OnInit } from '@angular/core';
import { SearchItem } from '../../../model/search-item';
import { AuthHelper } from '../../../services/authHelper.service';
import { EmployeeService } from '../../../services/employee.service';


@Component({
  selector: 'app-dash-layout',
  templateUrl: './dash-layout.component.html',
  styleUrls: ['./dash-layout.component.css']
})
export class DashLayoutComponent {
  filter: string;
  toShow = false;
  imageToShow: any;
  constructor(private authHelperService: AuthHelper,
    private employeeDetailService: EmployeeService) {
    this.employeeDetailService.initializeEmployeeDetails();
    this.getUserImage();
  }
  called() {
    // console.log("I am called ");
    this.toShow = true;
  }
  decalled() {
    // console.log("gone");
    this.toShow = false;
  }
  logout() {
    this.authHelperService.logout();
  }


  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  getUserImage() {
    this.authHelperService.getMSGraphAccessToken().then(token => {
      this.employeeDetailService.getImage(token).
        subscribe(data => {
          this.createImageFromBlob(data);
          console.log("image" + data);
        },
          err => console.log(err),
      );
    }, error => {
      console.log(error);
    });
  }
}
