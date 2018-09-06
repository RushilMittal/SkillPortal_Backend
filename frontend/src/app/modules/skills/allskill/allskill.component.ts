import { Component, OnInit, Input } from '@angular/core';
import { AllSkillService } from '../../../services/allskillservice.service';
import { Skill } from '../../../model/Skill';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-allskill',
  templateUrl: './allskill.component.html',
  styleUrls: ['./allskill.component.css']
})
export class AllskillComponent implements OnInit {
  @Input() skills: string[];
  @Input() modelSkillGroup: string;
  errorMessage: any;
  constructor(private allSkillService: AllSkillService) { }
  ngOnInit() {
 
  }


}
