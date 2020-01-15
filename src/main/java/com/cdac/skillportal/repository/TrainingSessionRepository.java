package com.cdac.skillportal.repository;

import com.cdac.skillportal.model.TrainingSession;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface TrainingSessionRepository extends MongoRepository<TrainingSession, String> {

    public List<TrainingSession> findBytrainingId(String trainingId);

    public List<TrainingSession> findBytrainingDate(Date trainingDate);

}
