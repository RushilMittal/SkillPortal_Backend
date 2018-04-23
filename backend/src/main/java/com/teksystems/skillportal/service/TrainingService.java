package com.teksystems.skillportal.service;

import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.model.TrainingSession;
import com.teksystems.skillportal.repository.TrainingRepository;
import com.teksystems.skillportal.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class TrainingService {

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    TrainingSessionRepository trainingSessionRepository;

    public void saveTraining(Training training, List<TrainingSession> trainingSessions) throws Exception
    {
        Training doesExist;
        String id;
        Random random = new Random();
        do
        {
            id = Integer.toString(random.nextInt(100000));
            doesExist = trainingRepository.findByid(id);
        } while(doesExist!=null);
        training.setId(id);
        trainingRepository.save(training);
        for(TrainingSession iterable : trainingSessions)
        {
            iterable.setTrainingId(id);
        }
        trainingSessionRepository.save(trainingSessions);
    }

}
