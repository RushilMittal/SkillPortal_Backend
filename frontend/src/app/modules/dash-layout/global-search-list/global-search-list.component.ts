import { Component, OnInit, Input } from '@angular/core';
import { SearchItem } from '../../../model/search-item';

import { SearchService } from '../../../services/search.service';
import { IdService } from '../../../services/idservice.service';
import {Router} from '@angular/router';
import { Certification } from '../../../model/certification';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { Subject } from 'rxjs/Subject';
import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';


@Component({
  selector: 'app-global-search-list',
  templateUrl: './global-search-list.component.html',
  styleUrls: ['./global-search-list.component.css']
})
export class GlobalSearchListComponent implements OnInit {

  @Input() filter: SearchItem;

  public skillitems: SearchItem[] = [];
  public certitems: Certification[] = [];

  skillitem: Observable<SearchItem[]>;
  private searchTerms = new Subject<string>();

  certitem: Observable<Certification[]>;

  constructor(private searchService: SearchService, private router: Router, private _idService: IdService) { }

  ngOnInit() {
    this.skillitem = this.searchTerms.pipe(

      debounceTime(500),

      distinctUntilChanged(),

      switchMap((term: string) => this.searchService.searchSkills(this.filter.name)),
    );

    this.certitem = this.searchTerms.pipe(
    debounceTime(500),

      distinctUntilChanged(),

      switchMap((term: string) => this.searchService.searchCert(this.filter.name)),
    );
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }


  keyeventfunc() {
    this.search(this.filter.name);
    this.skillitem.subscribe(skills => this.skillitems = skills);
    this.certitem.subscribe(skills => this.certitems = skills);
  }


}
