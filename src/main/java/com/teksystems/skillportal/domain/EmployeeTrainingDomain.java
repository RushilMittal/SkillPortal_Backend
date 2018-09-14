package com.teksystems.skillportal.domain;


import com.teksystems.skillportal.model.Training;

import java.util.Date;

public class EmployeeTrainingDomain {

    String id;
    String empId;
    Training training;
    Date lastModified;

    public EmployeeTrainingDomain(String empId, Training training, Date lastModified) {
        this.empId = empId;
        this.training = training;
        this.lastModified = lastModified;
    }


    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

}
