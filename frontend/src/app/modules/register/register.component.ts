import { Component, OnInit } from "@angular/core";
import { EmployeeDetails } from "../../model/EmployeeDetail";
import { AuthenticateService } from "../../services/Authenticate.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.css"]
})
export class RegisterComponent implements OnInit {
  employeeDetails: EmployeeDetails = new EmployeeDetails();

  constructor(
    private authenticateService: AuthenticateService,
    private router: Router
  ) {}

  ngOnInit() {}

  submit() {
    console.log(this.employeeDetails);
    this.authenticateService.register(this.employeeDetails).subscribe(
      data => {
        this.employeeDetails = data;
      },
      (error: any) => {
        console.log(error);
      },
      () => {
        console.log("registration successfull");
        this.router.navigate(["/login"]);
      }
    );
  }
}
