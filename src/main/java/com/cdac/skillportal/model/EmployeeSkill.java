package com.cdac.skillportal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "employeeskill")
public class EmployeeSkill {

    @Id
    String id;
    String empId;
    String subSkillId;
    int rating;
    Date lastModifiedDate;

    public EmployeeSkill(String empId, String subSkillId, int rating, Date lastModifiedDate) {
        this.empId = empId;
        this.subSkillId = subSkillId;
        this.rating = rating;
        this.lastModifiedDate = lastModifiedDate;
    }

    public EmployeeSkill() {
        //  Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getSubSkillId() {
        return subSkillId;
    }

    public void setSubSkillId(String subSkillId) {
        this.subSkillId = subSkillId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "EmployeeSkill{" +
                "id='" + id + '\'' +
                ", empId='" + empId + '\'' +
                ", subSkillId='" + subSkillId + '\'' +
                ", rating=" + rating +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }

    @Override
    public int hashCode() {

       return empId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("inside the equals");
        boolean isEqual = false;
        if(obj instanceof EmployeeSkill) {
            EmployeeSkill temp = (EmployeeSkill) obj;
            isEqual = temp.getEmpId().equals(this.empId);
        }

        return isEqual;
    }
}