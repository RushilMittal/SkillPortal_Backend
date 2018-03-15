import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-certification-placeholder',
  templateUrl: './certification-placeholder.component.html',
  styleUrls: ['./certification-placeholder.component.css']
})
export class CertificationPlaceholderComponent implements OnInit {
  subtitle1 = 'Certifications Received';
  subtitle2 = 'Certifications Required';
  constructor() { }

  ngOnInit() {
  }

}
