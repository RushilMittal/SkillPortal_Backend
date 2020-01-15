package com.cdac.skillportal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "trainingSession")
public class TrainingSession implements Comparable<TrainingSession> {
    @Id
    String id;
    String trainingId;
    Date trainingDate;
    String startTime;
    String endTime;

    public TrainingSession() {

    }


    public TrainingSession(String trainingId, Date trainingDate, String startTime, String endTime) {

        this.trainingId = trainingId;
        this.trainingDate = trainingDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public int compareTo(TrainingSession o) {

        return this.trainingDate.compareTo(new Date());
    }


}
