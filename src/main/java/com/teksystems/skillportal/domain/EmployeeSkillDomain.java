package com.teksystems.skillportal.domain;


import java.util.Date;

public class EmployeeSkillDomain {

    String employeeId;
    SubSkillDomain subSkill; //changed from SubSkill to SubSkillDomain
    int rating;
    Date lastModifiedDate;

    public EmployeeSkillDomain(String employeeId, SubSkillDomain subSkill, int rating, Date lastModifiedDate) {
        this.employeeId = employeeId;
        this.subSkill = subSkill;
        this.rating = rating;
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public SubSkillDomain getSubSkill() {
        return subSkill;
    }

    public int getRating() {
        return rating;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public String toString() {
        return "EmployeeSkillDomain{" +
                "employeeId='" + employeeId + '\'' +
                ", subSkill=" + subSkill +
                ", rating=" + rating +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
