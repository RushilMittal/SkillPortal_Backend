import { Injectable, Injector } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
// import { AuthService } from './services/AuthToken.service';
import { Observable } from 'rxjs/Observable';
import { OidcSecurityService } from 'angular-auth-oidc-client';
@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    private oidcSecurityService: OidcSecurityService;

    constructor(private injector: Injector) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let requestToForward = req;
       console.log('Inside Interceptor');
        if (this.oidcSecurityService === undefined) {
            this.oidcSecurityService = this.injector.get(OidcSecurityService);
        }
        if (this.oidcSecurityService !== undefined) {
            const token = this.oidcSecurityService.getIdToken();
            console.log('Inside Interceptor : ' + token);
            if (token !== '') {
                const tokenValue = 'Bearer ' + token;
                console.log('TOkenValue:' + tokenValue);
                requestToForward = req.clone({ setHeaders: { 'Authorization': tokenValue } });
                console.log('Header Added' + requestToForward.headers.get('Authorization'));
            }
        } else {
            // tslint:disable-next-line:no-console
            console.debug('OidcSecurityService undefined: NO auth header!');
        }
        console.log('Checking Header Authorization: ' + requestToForward.headers.get('Authorization'));
        return next.handle(requestToForward);
    }
}
