import { Injectable } from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor,
    HttpClient
} from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { AuthHelper } from './services/authHelper.service';
import { fromPromise } from 'rxjs/observable/fromPromise';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {


    constructor(private authHelperService: AuthHelper) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let requestToForward = req;

        if (!this.authHelperService.isOnline()) {
            this.authHelperService.login();
        }
        if(req.headers.has('Authorization')){
            console.log("I have Auth " + req.url);
            return next.handle(req);
        }
        else if(!req.headers.has('Authorization')) {
            if (this.authHelperService.isOnline()) {
                return fromPromise(this.authHelperService.getMSGraphAccessToken())
              .switchMap(token => {
                   const headers = req.headers
                            .set('Authorization', 'Bearer ' + token);
                            
                   const reqClone = req.clone({
                     headers 
                    });
                  return next.handle(reqClone);
             });



            // //    this.authHelperService.getMSGraphAccessToken().then(token => {
            //         let token = 'eyJ0eXAiOiJKV1QiLCJub25jZSI6IkFRQUJBQUFBQUFEWDhHQ2k2SnM2U0s4MlRzRDJQYjdyelB6QkpGYWpzNW5kZGFhbHoySUZ2a0QyQTMycGo5OGoyRUdrTEVXNjU1R3FZU2hyM1ExS2VSQlZoNnlIZVQzZF92ZTVfWHY2ZWRJSC1LYUZ0X0xVQUNBQSIsImFsZyI6IlJTMjU2IiwieDV0IjoiaUJqTDFSY3F6aGl5NGZweEl4ZFpxb2hNMllrIiwia2lkIjoiaUJqTDFSY3F6aGl5NGZweEl4ZFpxb2hNMllrIn0.eyJhdWQiOiJodHRwczovL2dyYXBoLm1pY3Jvc29mdC5jb20iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8zNzFjYjkxNy1iMDk4LTQzMDMtYjg3OC1jMTgyZWM4NDAzYWMvIiwiaWF0IjoxNTI1ODcxMDMzLCJuYmYiOjE1MjU4NzEwMzMsImV4cCI6MTUyNTg3NDkzMywiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhIQUFBQURVQlRKcHRPRFRYbG8veTZwUEtNM0gyTDFmR1dkYnRIRXR4UjNJa2p6bjA9IiwiYW1yIjpbInB3ZCJdLCJhcHBfZGlzcGxheW5hbWUiOiJTa2lsbFBvcnRhbCIsImFwcGlkIjoiZWRiMzFjN2EtMTI3My00NGU4LWIwZDAtNTA4MzBhYWVkZTM1IiwiYXBwaWRhY3IiOiIwIiwiZV9leHAiOjI2MjgwMCwiZmFtaWx5X25hbWUiOiJTaW5naCIsImdpdmVuX25hbWUiOiJTYWhpYiIsImlwYWRkciI6IjExNS4xMTAuMTkyLjE0NiIsIm5hbWUiOiJTaW5naCwgU2FoaWIiLCJvaWQiOiJhOTUzZjNhZC0xMzFjLTRhOTYtYWU1Ni00MDUxMzljZTUwY2QiLCJvbnByZW1fc2lkIjoiUy0xLTUtMjEtMjA3NjU5NzQ5Ni0xNTYzMjYxOTQ0LTEyNTY0MTAwNjEtNDM4MDgyIiwicGxhdGYiOiIzIiwicHVpZCI6IjEwMDMwMDAwQTdERDY5M0IiLCJzY3AiOiJNYWlsLlNlbmQgVXNlci5SZWFkIiwic3ViIjoiakxHcFM2emJ2eV9aTndQNHJzZWtsaWJLVExfYlppSXQxY0NNaXppSGFtayIsInRpZCI6IjM3MWNiOTE3LWIwOTgtNDMwMy1iODc4LWMxODJlYzg0MDNhYyIsInVuaXF1ZV9uYW1lIjoic2FoaXNpbmdoQHRla3N5c3RlbXMuY29tIiwidXBuIjoic2FoaXNpbmdoQHRla3N5c3RlbXMuY29tIiwidXRpIjoidWVrUmtwY2NZVVdtaXBpY19ZNHVBQSIsInZlciI6IjEuMCJ9.hyJ5eMwoJHmH3_wQ0_x1LhZzwXvFBVjRZLpSCTXlbO7kOSM1zOmQQ9FjHxPfkTRWUJZEY32CROffd0e7bx8ZtOxjF9qUDlVG9i84T7YPHLu7cZtTNjM122Cst3bwmufI2Ys_wW6S2Snni0qIrr1UgHoc_pACBI5re54lWmEFeXUqiUOETNVYyyq8lFLPHMvZPHo85A7pts0l6YT_i61R4LCOIPH4WNJr0Mx_r_RgizK9zJF-JXubSFTG5U5g4apRQT5VzbvoqXl9WRbssr3pGnwarNR5wAKmEwF6KpXQyNg1lJNXyiVRb_ggG1emXAlqBUOlJzZAbP1r37i0Y6LEfg';
            //         const tokenValue = 'Bearer ' + token;
            //         console.log("inside the then" + tokenValue);
            //         requestToForward = req.clone({ setHeaders: { 'Authorization': tokenValue } });
            //         console.log("INside the if no auth present " + requestToForward.headers.get('Authorization'));
            //         return next.handle(requestToForward);
            // //    });

               
                // if (token !== '') {
                //     const tokenValue = 'Bearer ' + token;
                //     console.log('TOkenValue:' + tokenValue);
                //     requestToForward = req.clone({ setHeaders: { 'Authorization': tokenValue } });
                //     console.log(req.headers.get('Authorization'));

                // }
            } else {
                // console.debug('OidcSecurityService undefined: NO auth header!');
            }
        }
        

        
    }
}
