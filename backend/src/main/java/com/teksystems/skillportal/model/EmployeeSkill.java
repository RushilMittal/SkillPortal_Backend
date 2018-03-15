package com.teksystems.skillportal.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employeeskill")
public class EmployeeSkill {

	@Id
	String id;
	String empId;
	String subSkillId;
	int rating;
	Date lastModifiedDate;

	public EmployeeSkill(String empId, String subSkillId, int rating) {
		this.empId=empId;
		this.subSkillId=subSkillId;
		this.rating=rating;
		this.lastModifiedDate=new Date();
	}

	public EmployeeSkill() {
		// TODO Auto-generated constructor stub
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

}