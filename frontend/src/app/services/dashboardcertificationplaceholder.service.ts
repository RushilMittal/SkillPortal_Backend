import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams} from '@angular/http';
import { Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { baseUrlCertification } from '../baseUrl';
import { EmployeeCertificatePlaceholderModel } from '../model/EmployeeCertificatePlaceholderModel';


@Injectable()
export class DashboardCertificationPlaceholderService {
   apiRoot = baseUrlCertification;
  constructor(private http: Http) { }

  getCertificatePlaceholder(empId: string): Observable<EmployeeCertificatePlaceholderModel> {
    // console.log('inside GetCertificatePlaceholder');
    const url = `${this.apiRoot}/getcertificationplaceholder?employeeId=${empId}`;
    // console.log(url);
    return this.http.get(url)
            .map((response: Response) => <EmployeeCertificatePlaceholderModel>response.json())
            .do(data => console.log(JSON.stringify(data )))
            .catch(this.handleError);

  }
  private handleError(error: Response): Observable<any> {
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }
}
