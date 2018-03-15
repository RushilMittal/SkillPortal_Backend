package com.teksystems.skillportal.domain;
import com.teksystems.skillportal.model.Certification;
import java.util.Date;

public class EmployeeCertificationDomain {

    String empId;
    CertificationDomain certificationId;
    Date certificationDate;
    Date certificationValidityDate;
    int certificationNumber;
    String certificationUrl;
    public EmployeeCertificationDomain() {}
    public EmployeeCertificationDomain(String empId, CertificationDomain certificationId, Date certificationDate, 
    		Date certificationValidityDate, int certificationNumber, String certificationUrl) {
        this.empId = empId;
        this.certificationId = certificationId;
        this.certificationDate = certificationDate;
        this.certificationValidityDate = certificationValidityDate;
        this.certificationNumber = certificationNumber;
        this.certificationUrl = certificationUrl;
    }


    
	public String getempId() {
        return this.empId;
    }

    public CertificationDomain getCertificationId() {
        return this.certificationId;
    }
    
    public String getCertificationIdId() {
    	return this.certificationId.id;
    }

    public Date getCertificationDate() {
        return this.certificationDate;
    }

    public Date getCertificationValidityDate() {
        return this.certificationValidityDate;
    }

    public int getCertificationNumber() {
        return this.certificationNumber;
    }

    public String getCertificationUrl() {
        return this.certificationUrl;
    }
}



