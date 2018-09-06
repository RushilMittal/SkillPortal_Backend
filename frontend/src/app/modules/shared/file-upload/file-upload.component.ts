import { Component, OnInit, EventEmitter, Output, NgZone, Input } from '@angular/core';
import { FileUploader, FileItem, ParsedResponseHeaders, FileLikeObject } from 'ng2-file-upload';
import { baseUrlAdmin } from '../../../baseUrl';
import { AuthHelper } from '../../../services/authHelper.service';
import { ToastService } from '../../../services/toast.service';



@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})

export class FileUploadComponent implements OnInit {

  @Input() idvalue: string;
  @Output() upload: EventEmitter<void> = new EventEmitter<void>();
  URL;
  // uploader is for modelSkill
  public uploader: FileUploader = new FileUploader({
    
    authToken: 'Bearer ' + this.authHelper.getUser(),
    headers: [{
      name: 'Token',
      value: this.authHelper.getAccessToken()
    }],
    allowedMimeType: ['text/csv','application/vnd.ms-excel']
  });

 
  constructor(private authHelper: AuthHelper,private toast:ToastService) {
 
      
    this.uploader.onErrorItem = ((item: FileItem, response: string, status: number, headers: ParsedResponseHeaders): any => {
      alert("Some error Occured");
    });

    this.uploader.onWhenAddingFileFailed = function (item: FileLikeObject, filter: any, options: any) {
      // console.log(item);
      // console.log(filter);
      // console.log(options);
      alert("unknown file type, only csv format supported");
    };
    
    this.uploader.onCompleteItem = function (item: FileItem, response: string, status: number, headers: ParsedResponseHeaders){
      alert('Uploaded Successfully');
    
    }
    
    
    
  }

  ngOnInit() {
    console.log("type recieved" +this.idvalue);
    if(this.idvalue==='1'){
      this.uploader.options.url = `${baseUrlAdmin}/uploadskillcsv`;
    }else if(this.idvalue==='2'){
      this.uploader.options.url = `${baseUrlAdmin}/uploadcertificatecsv`;
    }
  }

  submit() {
    this.toast.showSuccessToast("Skills Uploaded Successfully");
    this.upload.emit();

  }
}
