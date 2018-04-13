package com.teksystems.skillportal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "EmployeeCertification")
public class EmployeeCertification {

    @Id
    String id;
    String empId;
    String certificationId;
    Date certificationDate;
    Date certificationValidityDate;
    int certificationNumber;
    String certificationUrl;
    public EmployeeCertification() {}
    public EmployeeCertification(String employeeId, String certificationId, Date certificationDate, Date certificationValidityDate, int certificationNumber, String certificationUrl) {
        this.empId = employeeId;
        this.certificationId = certificationId;
        this.certificationDate = certificationDate;
        this.certificationValidityDate = certificationValidityDate;
        this.certificationNumber = certificationNumber;
        this.certificationUrl = certificationUrl;
    }

    public String getEmployeeId() {
        return empId;
    }

    public String getCertificationId() {
        return certificationId;
    }

    public Date getCertificationDate() {
        return certificationDate;
    }

    public Date getCertificationValidityDate() {
        return certificationValidityDate;
    }

    public int getCertificationNumber() {
        return certificationNumber;
    }

    public String getCertificationUrl() {
        return certificationUrl;
    }
}
