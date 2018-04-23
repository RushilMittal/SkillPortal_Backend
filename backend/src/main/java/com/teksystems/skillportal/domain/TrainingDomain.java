package com.teksystems.skillportal.domain;
import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.model.TrainingSession;

import java.util.List;

public class TrainingDomain {

    Training training;
    List<TrainingSession> trainingSessions;


    public TrainingDomain() {
        super();
    }

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
