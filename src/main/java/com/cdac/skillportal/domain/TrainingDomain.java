package com.cdac.skillportal.domain;

import com.cdac.skillportal.model.Training;
import com.cdac.skillportal.model.TrainingSession;

import java.util.List;

public class TrainingDomain {

    Training training;
    List<TrainingSession> trainingSessions;


    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public List<TrainingSession> getTrainingSessions() {
        return trainingSessions;
    }

    public void setTrainingSessions(List<TrainingSession> trainingSessions) {
        this.trainingSessions = trainingSessions;
    }

}
