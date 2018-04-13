import { Component, OnInit, Input } from '@angular/core';
import { SearchItem } from '../../../../model/search-item';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { SearchService } from '../../../../services/search.service';
import { Subject } from 'rxjs/Subject';
import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';


@Component({
  selector: 'app-search-list',
  templateUrl: './search-list.component.html',
  styleUrls: ['./search-list.component.css']
})
export class SearchListComponent implements OnInit {
  @Input() filter: SearchItem;
  skills: SearchItem[];
  skillitem: Observable<SearchItem[]>;
  private searchTerms = new Subject<string>();



public items: SearchItem[] = [];
public retvalues: SearchItem[] = [];

  constructor(private searchService: SearchService ) { }

  ngOnInit() {
    this.skillitem = this.searchTerms.pipe(

      debounceTime(500),

      distinctUntilChanged(),

      switchMap((term: string) => this.searchService.searchSkills(this.filter.name)),
    );
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

   keyeventfunc() {
    this.search(this.filter.name);
    this.skillitem.subscribe(skills => {this.skills = skills;
                                        console.log(this.skills); });
    // console.log(this.skills);
   }


}
