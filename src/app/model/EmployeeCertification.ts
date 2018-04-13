import { Certification } from './Certification';
export class EmployeeCertificate {
    empId: string;
    certificationId: Certification = new Certification(null, null, null, null);
    certificationDate: Date;
    certificationValidityDate: Date;
    certificationUrl: string;
    certificationNumber: number;
    constructor() { }

}
