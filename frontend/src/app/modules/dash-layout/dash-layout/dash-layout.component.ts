import { Component, OnInit } from "@angular/core";
import { SearchItem } from "../../../model/search-item";
import { AuthHelper } from "../../../services/authHelper.service";
import { EmployeeService } from "../../../services/employee.service";

@Component({
  selector: "app-dash-layout",
  templateUrl: "./dash-layout.component.html",
  styleUrls: ["./dash-layout.component.css"]
})
export class DashLayoutComponent implements OnInit {
  filter: string;
  toShow: boolean = false;
  show: boolean = false;
  admin: boolean = false;
  imageToShow: any;
  constructor(
    private authHelperService: AuthHelper,
    private employeeDetailService: EmployeeService
  ) {
    this.employeeDetailService.initializeEmployeeDetails();
    this.isadmin();
    // this.getUserImage();
  }

  ngOnInit() {}

  logout() {
    this.authHelperService.logout();
  }

  toggle() {
    this.toShow = !this.toShow;
    console.log("Show " + this.toShow);
  }

  isadmin(): boolean {
    console.log("isadmin called");
    if (this.employeeDetailService.checkRoleAdmin()) {
      console.log("INside the isadmin true");
      this.admin = true;
      return true;
    } else {
      console.log("not an admin");
      this.admin = false;
      return false;
    }
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener(
      "load",
      () => {
        this.imageToShow = reader.result;
      },
      false
    );

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  getUserImage() {
    //   this.authHelperService.getMSGraphAccessToken().then(
    //     token => {
    //       this.employeeDetailService.getImage(token).subscribe(
    //         data => {
    //           this.createImageFromBlob(data);
    //           console.log("image" + data);
    //         },
    //         err => console.log(err)
    //       );
    //     },
    //     error => {
    //       console.log(error);
    //     }
    //   );
  }
}
