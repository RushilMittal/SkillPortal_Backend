import { Component, OnInit, Input } from '@angular/core';
import { SearchItem } from '../../../../model/search-item';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { SearchService } from '../../../../services/search.service';
import { Subject } from 'rxjs/Subject';
import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';
import { SubSkill } from '../../../../model/SubSkill';


@Component({
  selector: 'app-search-list',
  templateUrl: './search-list.component.html',
  styleUrls: ['./search-list.component.css']
})
export class SearchListComponent implements OnInit {
  @Input() filter: string;
  skills: string[];
  skillitem: Observable<string[]>;
  private searchTerms = new Subject<string>();
  showSpinner = false;


  public items: SearchItem[] = [];
  public retvalues: SearchItem[] = [];

  constructor(private searchService: SearchService) { }

  ngOnInit() {

    this.skillitem = this.searchTerms.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((term: string) => this.searchService.searchSkills(this.filter))
    );
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  keyeventfunc() {
    this.search(this.filter);
    this.showSpinner = true;
    this.skillitem.subscribe(skills => {
      this.skills = skills;
      console.log(this.skills);
      this.showSpinner = false;
    });
  }


}
