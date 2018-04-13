import { Component, OnInit, Input } from '@angular/core';
import { SearchItem } from '../../../model/search-item';

import { SearchService } from '../../../services/search.service';
import { IdService } from '../../../services/idservice.service';
import {Router} from '@angular/router';


import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { Subject } from 'rxjs/Subject';
import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';
import { Certification } from '../../../model/Certification';
import { OnChanges } from '@angular/core/src/metadata/lifecycle_hooks';


@Component({
  selector: 'app-global-search-list',
  templateUrl: './global-search-list.component.html',
  styleUrls: ['./global-search-list.component.css']
})
export class GlobalSearchListComponent implements OnInit {

  @Input() filter: string;
  // @Input() toshow: boolean;
  showSpinnerSkills = false;
  showSpinnerCertificate = false;

  public skillitems: string[] = [];
  public certitems: Certification[] = [];

  skillitem: Observable<string[]>;
  private searchTerms = new Subject<string>();

  certitem: Observable<Certification[]>;

  constructor(private searchService: SearchService, private router: Router, private _idService: IdService) { }

  // ngOnChanges(){
  //   // remove the show from the dropwown
  //   console.log(this.toshow);
  // }

  ngOnInit() {
    this.skillitem = this.searchTerms.pipe(

      debounceTime(500),

      distinctUntilChanged(),

      switchMap((term: string) => this.searchService.searchSkills(this.filter)),
    );

    this.certitem = this.searchTerms.pipe(
    debounceTime(500),

      distinctUntilChanged(),

      switchMap((term: string) => this.searchService.searchCert(this.filter)),
    );

  }

  search(term: string): void {

    this.searchTerms.next(term);
  }


  keyeventfunc() {
    this.showSpinnerCertificate = true;
    this.showSpinnerSkills = true;
    this.search(this.filter);

    this.skillitem.subscribe(skills => {
          this.skillitems = skills,
          this.showSpinnerSkills = false;

        }

    );
    this.certitem.subscribe(skills => {

        this.certitems = skills,
        this.showSpinnerCertificate = false;
      }

      );
  }


}
