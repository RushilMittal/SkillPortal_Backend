package com.teksystems.skillportal.domain;

import java.util.Date;

public class EmployeeTrainingPlaceholderDomain {
	String trainingId;
	String name;
	Date trainingDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}

	public String getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(String trainingId) {
		this.trainingId = trainingId;
	}
	
	
}