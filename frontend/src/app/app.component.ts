import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Skill Portal';
  constructor(private _router: Router) {}
  ngOnInit() {
  // could use this code to reload the component on each click.
  //   this._router.routeReuseStrategy.shouldReuseRoute = function(){
  //     return false;
  // };

  // this._router.events.subscribe((evt) => {
  //     if (evt instanceof NavigationEnd) {
  //         this._router.navigated = false;
  //         window.scrollTo(0, 0);
  //     }
  // });

}
}
