import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { EmployeeService } from '../../../services/employee.service';
import { AuthHelper } from '../../../services/authHelper.service';

@Component({
  selector: 'app-send-email',
  templateUrl: './send-email.component.html',
  styleUrls: ['./send-email.component.css']
})
export class SendEmailComponent implements OnInit {
  @Output() saveClicked: EventEmitter<void> = new EventEmitter<void>();
  emailContent;
  @Input() placeholder: string;
  @Input() subject : string;
  constructor(private employeeService:EmployeeService,private authHelperService:AuthHelper) { 
    this.emailContent = "Thanks, " + this.employeeService.employeeDetails.displayName;
  }
  
  ngOnInit() {
  }

  sendEmail(){
    console.log("Sending Email");
    this.sendAdminEmail();
    this.saveClicked.emit();
    
  }

  sendAdminEmail() {
    let data = this.emailContent;
    this.authHelperService.getMSGraphAccessToken().then(token => {
      this.employeeService.sendEmail(token,data,this.subject).
        subscribe(data => {
          console.log("image" + data);
        },
          err => console.log(err),
      );
    }, error => {
      console.log(error);
    });
  }
  
}
