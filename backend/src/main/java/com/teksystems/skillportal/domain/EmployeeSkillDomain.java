package com.teksystems.skillportal.domain;

import java.util.Date;

public class EmployeeSkillDomain {
	
 String employeeId;
 SubSkillDomain subSkill;
 int rating;
 Date lastModifiedDate;
 
 public EmployeeSkillDomain(String employeeId, SubSkillDomain subSkill, int rating, Date lastModifiedDate)
  {
	 this.employeeId = employeeId;
	 this.subSkill=subSkill;
	 this.rating=rating;
	 this.lastModifiedDate=lastModifiedDate;
  }

 public String getEmployeeId(){
	 return this.employeeId;
	}

public SubSkillDomain getSubSkill() {
 	return this.subSkill;
   }


public int getRating(){
	 return this.rating;	
	}

public Date getLastModifiedDate() {
	return this.lastModifiedDate;
    }


}
