import { Component, OnInit, ViewChild } from '@angular/core';
import { SubSkill } from '../../model/SubSkill';
import { EmployeeSkill } from '../../model/EmployeeSkill';
import { EmployeeCertificate } from '../../model/EmployeeCertification';
import { SkillReport } from '../../model/skillreport';
import { ReportService } from '../../services/report.service';
import { BaseChartDirective } from 'ng2-charts';
import { Ng2SmartTableComponent } from 'ng2-smart-table/ng2-smart-table.component';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import { Angular2Csv } from 'angular2-csv/Angular2-csv';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {
  from;
  to;
  @ViewChild(BaseChartDirective) chart: BaseChartDirective;
  @ViewChild(Ng2SmartTableComponent) table: Ng2SmartTableComponent;

  reportType : boolean[]=[];
  numberOfSkills : number;
  numberOfMonths : number;
  showGraph : boolean;
  showTable : boolean;
  topSubSkills: SubSkill[];
  employeeSkills: EmployeeSkill[];
  employeeCerts: EmployeeCertificate[];
  skillUpdated: SkillReport[];
  employeeIds: String[];
  employeeId:string="101";

  public chartColors: any[] = [
    { 
      backgroundColor:"#FF7360",
      borderColor: 'rgba(148,159,177,1)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }];

  chartOptions = {
    responsive: true,
    scales: {
      yAxes: [{
        ticks: {
          beginAtZero: true,
          autoSkip: false
        }
      }],
      xAxes: [{
        ticks: {
          autoSkip: false
        }
      }]
    }
  };
  
  chartData2 = [
    { data: [350, 600], label: 'Whatever subskill' },
  ];

  chartLabels = ['1', '2',];

  settings : any;
  
  rows:any[];

  constructor(private reportService:ReportService) { }

  ngOnInit() {
    this.reportSetter(0);
    this.showGraph=false;
    this.showTable=false;

    this.rows = [{
      vin: '1',
      year: 'title test1'
    }];
  
    this.settings= { actions: false,
      columns:{
      vin: {title: 'ID' },
      year: {title: 'Full Name' }}
     };

     this.reportService.getEmployeesWithASkill().subscribe(employeeIds => this.employeeIds=employeeIds);
  }



  onChangeSetEmployee(employeeId) {
    this.employeeId=employeeId;
  }

  onChange(deviceValue) {
    this.reportSetter(deviceValue);
  }

  reportSetter(n:number)
  {
    let i;  
    for(i=0;i<5;i++)
    {
      this.reportType[i]=false;
    }
    this.reportType[n]=true;
  }

  generateReport(skillReportType:number)
  {
    if(skillReportType==2)
    {
        this.showGraph=true;
        this.showTable=true;
        let a: Date = new Date(this.from.year,this.from.month-1,this.from.day);
        console.log("date is"+a.getTime()+ " skills " + this.numberOfSkills);
        this.reportService.getSkillTrend(this.numberOfSkills,a.getTime())
        .map(res => res)
        .subscribe(
        subskills => this.topSubSkills=subskills,
        error=>console.log("error"),
        () =>  {
          this.chartLabels= this.topSubSkills.map(a=>a.subSkill);
          let arr = this.topSubSkills.map(a=>a.totalNumberofRatedUsers);
          this.chartData2 = [{ data: arr ,label: "Number of Users who have Rated or Updated Skills"}];
          this.chart.chart.config.data.labels=this.chartLabels;
          this.chart.chart.config.data.datasets=this.chartData2;
          this.chart.colors = this.chartColors;
          this.chart.chart.update();

          this.settings ={actions: false, 
                                 columns:  { subSkillName: {title: 'SubSkill'},
                                    noOfRatedUsers: {title: 'Number of Rated Users'},
                                  },
                                  pager : {
                                    display : true,
                                    perPage:10
                                  }  };               
                 this.rows=[];
                 for(let topSubSkill of this.topSubSkills)
                 {
                   this.rows.push({subSkillName:topSubSkill.subSkill,
                                   noOfRatedUsers:topSubSkill.totalNumberofRatedUsers});
                 }
         }
        );
    }

    if(skillReportType==1)
    {
        this.showGraph=true;
        this.showTable=true;
        this.reportService.getTopSkill(this.numberOfSkills)
        .map(res => res)
        .subscribe(
        subskills => this.topSubSkills=subskills,
        error=>console.log("error"),
        () =>  {
                 this.chartLabels= this.topSubSkills.map(a=>a.subSkill);
                 let arr = this.topSubSkills.map(a=>a.totalNumberofRatedUsers);
                 this.chartData2 = [{ data: arr ,label: "Number of Users who have Rated or Updated Skills"}];
                 this.chart.chart.config.data.labels=this.chartLabels;
                 this.chart.chart.config.data.datasets=this.chartData2;
                 this.chart.chart.update();

                 this.settings ={actions: false, 
                                 columns:  { subSkillName: {title: 'SubSkill'},
                                    noOfRatedUsers: {title: 'Number of Rated Users'},
                                  },
                                  pager : {
                                    display : true,
                                    perPage:10
                                  }  };               
                 this.rows=[];
                 for(let topSubSkill of this.topSubSkills)
                 {
                   this.rows.push({subSkillName:topSubSkill.subSkill,
                                   noOfRatedUsers:topSubSkill.totalNumberofRatedUsers});
                 }
              }
             );
    }

    if(skillReportType==4)
    {
        this.showGraph=false;
        this.showTable=true;
        this.reportService.getSkillsEmployee(this.employeeId)
        .map(res => res)
        .subscribe(
        empskills => this.employeeSkills=empskills,
        error=>console.log("error"),
        () =>  {
                 this.settings ={actions: false, 
                                 columns:  { subSkillName: {title: 'SubSkill'},
                                    rating: {title: 'Rating'},
                                    lastModified: {title: 'Date'}
                                  },
                                  pager : {
                                    display : true,
                                    perPage:10
                                  }  };               
                 console.log(this.employeeSkills);
                 this.rows=[];
                 for(let employeeSkill of this.employeeSkills)
                 {
                   this.rows.push({subSkillName:employeeSkill.subSkill.subSkill,
                                   rating:employeeSkill.rating,
                                   lastModified:new Date(employeeSkill.lastModifiedDate)});
                    console.log(employeeSkill.lastModifiedDate)
                    console.log(employeeSkill.rating)
                 }
               }
            );
        
    }

    if(skillReportType==5)
    {
        this.showGraph=false;
        this.showTable=true;
        let fromDate: Date = new Date(this.from.year,this.from.month-1,this.from.day);
        let toDate: Date = new Date(this.to.year,this.to.month-1,this.to.day);
        this.reportService.getExpiringCertificates(fromDate.getTime(),toDate.getTime())
        .map(res => res)
        .subscribe(
        empcerts => this.employeeCerts=empcerts,
        error=>console.log("error"),
        () =>  {
                this.settings ={actions: false, 
                                 columns:  { empId: {title: 'Employee ID'},
                                    certificate: {title: 'Certificate'},
                                    expiringOn: {title: 'Expiring On'}
                                  },
                                  pager : {
                                    display : true,
                                    perPage:10
                                } };
                 this.rows=[];
                 for(let employeeCert of this.employeeCerts)
                 {
                   this.rows.push({empId:employeeCert.empId,
                                   certificate:employeeCert.certificationId.certificationName+" by "+employeeCert.certificationId.institution,
                                   expiringOn:new Date(employeeCert.certificationValidityDate)});
                 }
               }
            );
    }
    if(skillReportType==3)
    {
        this.showGraph=false;
        this.showTable=true;
        let fromDate: Date = new Date(this.from.year,this.from.month-1,this.from.day);
        let toDate: Date = new Date(this.to.year,this.to.month-1,this.to.day);
        this.reportService.getUpdatedSkills(fromDate.getTime(),toDate.getTime())
        .map(res => res)
        .subscribe(
        skillupdated => this.skillUpdated=skillupdated,
        error=>console.log("error"),
        () =>  {
                this.settings ={actions: false, 
                                 columns:  { empId: {title: 'Employee ID'},
                                    subSkillId: {title: 'SubSkill'},
                                    minDate: {title: 'Rated First On'},
                                    firstRated: {title:'Previous Rating'},
                                    maxDate:{title: 'Rated Last On'},
                                    lastRated: {title:'Last Rating'}
                                  },
                                  pager : {
                                    display : true,
                                    perPage:10
                                  } };
                 this.rows=[];
                 for(let skillRepo of this.skillUpdated)
                 {
                   this.rows.push({empId:skillRepo.empId,
                                   subSkillId:skillRepo.subSkillId,
                                   minDate:new Date(skillRepo.minDate),
                                   firstRated:skillRepo.firstRating,
                                   maxDate:new Date(skillRepo.maxDate),
                                   lastRated:skillRepo.lastRating});
                 }
               }
            );
     }
  }

  downloadCSV() {
    let anarray:any[]=[];
    let sumthing=this.table.grid.getDataSet().getRows();
    let headers=this.table.grid.getColumns().map(a=>a.title);
    for(let onething of this.table.source.filteredAndSorted)
    {
      console.log(onething);
      anarray.push(onething);
    }
  console.log(anarray);
   new Angular2Csv(anarray,"Report",{headers: (headers)});
   
  }

  downloadCanvas(event) {
    var anchor = event.target;
    // get the canvas
    anchor.href = document.getElementsByTagName('canvas')[0].toDataURL();
    anchor.download = "test.png";
  }


}