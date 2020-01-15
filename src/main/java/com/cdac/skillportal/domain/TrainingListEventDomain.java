package com.cdac.skillportal.domain;

import java.util.Date;

public class TrainingListEventDomain {
    String id;
    Date start;
    String startTime;
    String endTime;
    String name;
    String trainer;
    String location;

    public TrainingListEventDomain(String id, Date start, String startTime, String endTime, String name, String trainer, String location) {
        this.id = id;
        this.start = start;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.trainer = trainer;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
