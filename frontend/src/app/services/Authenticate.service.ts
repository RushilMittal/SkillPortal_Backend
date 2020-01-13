import { Injectable } from "@angular/core";
import { baseIP } from "../baseUrl";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { catchError } from "rxjs/operators";
import { ErrorHandler } from "./handleerror.service";
import { httpOptions } from "../httpheaders";
import { JwtRequest } from "../model/JwtRequest";
import { Observable } from "rxjs";
import { EmployeeDetails } from "../model/EmployeeDetail";

@Injectable()
export class AuthenticateService {
  url = baseIP + "/authenticate";
  registerURL = baseIP + "/register";
  employeeDetailsURL = baseIP + "/getEmployeeDetail";
  constructor(private http: HttpClient, private handler: ErrorHandler) {}

  callAuthenticate(jwtRequest: JwtRequest): Observable<any> {
    return this.http
      .post<any>(this.url, jwtRequest, httpOptions)
      .pipe(catchError(this.handler.handleError));
  }

  register(employeeDetails: EmployeeDetails): Observable<EmployeeDetails> {
    return this.http
      .post<EmployeeDetails>(this.registerURL, employeeDetails, httpOptions)
      .pipe(catchError(this.handler.handleError));
  }
  getEmployeeDetails(): Observable<EmployeeDetails> {
    return this.http
      .get<EmployeeDetails>(this.employeeDetailsURL)
      .pipe(catchError(this.handler.handleError));
  }
}
