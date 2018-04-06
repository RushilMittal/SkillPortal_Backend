package com.teksystems.skillportal.domain;

import java.util.Date;

import com.teksystems.skillportal.model.SubSkill;

public class EmployeeSkillDomainAtul {
	
 String employeeId;
 SubSkill subSkill;
 int rating;
 Date lastModifiedDate;
 
 public EmployeeSkillDomainAtul(String employeeId, SubSkill subSkill, int rating, Date lastModifiedDate)
  {
	 this.employeeId = employeeId;
	 this.subSkill=subSkill;
	 this.rating=rating;
	 this.lastModifiedDate=lastModifiedDate;
  }

 public String getEmployeeId(){
	 return this.employeeId;
	}

public SubSkill getSubSkill() {
 	return this.subSkill;
   }


public int getRating(){
	 return this.rating;	
	}

public Date getLastModifiedDate() {
	return this.lastModifiedDate;
    }


}
