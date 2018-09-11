package com.teksystems.skillportal.domain;

import java.util.Date;

public class SkillReport {
	
	String empId;
	String subSkillId;
	int lastRating;
	int firstRating;
	Date maxDate;
	Date minDate;


	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public SkillReport() {

	}

    public SkillReport(String empId, String subSkillId, int lastRating, int firstRating, Date maxDate, Date minDate) {
        this.empId = empId;
        this.subSkillId = subSkillId;
        this.lastRating = lastRating;
        this.firstRating = firstRating;
        this.maxDate = maxDate;
        this.minDate = minDate;
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

	public int getLastRating() {
		return lastRating;
	}

	public void setLastRating(int lastRating) {
		this.lastRating = lastRating;
	}

	public int getFirstRating() {
		return firstRating;
	}

	public void setFirstRating(int firstRating) {
		this.firstRating = firstRating;
	}
	
   	
 
}
