package com.teksystems.skillportal.domain;

public class EmployeeCertificationPlaceholderDomain {
    String certificationName;
    String year;

    public EmployeeCertificationPlaceholderDomain(String certificationName, String year) {
        this.certificationName = certificationName;
        this.year = year;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "EmployeeCertificationPlaceholderDomain{" +
                "certificationName='" + certificationName + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
