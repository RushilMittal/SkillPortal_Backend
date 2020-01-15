package com.cdac.skillportal.service;

import com.cdac.skillportal.domain.TrainingDomain;
import com.cdac.skillportal.init.MongoConfigNew;
import com.cdac.skillportal.model.EmployeeTraining;
import com.cdac.skillportal.model.Training;
import com.cdac.skillportal.model.TrainingSession;
import com.mongodb.MongoException;
import com.cdac.skillportal.repository.EmployeeTrainingRepository;
import com.cdac.skillportal.repository.TrainingRepository;
import com.cdac.skillportal.repository.TrainingSessionRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrainingService {
    private static Logger logger = Logger.getLogger(TrainingService.class);

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    TrainingSessionRepository trainingSessionRepository;

    @Autowired
    EmployeeTrainingRepository employeeTrainingRepository;

    ApplicationContext ctx =
            new AnnotationConfigApplicationContext(MongoConfigNew.class);
    MongoOperations mongoOperation =
            (MongoOperations) ctx.getBean("mongoTemplate");

    public void saveTraining(Training training, List<TrainingSession> trainingSessions) throws MongoException {


        trainingRepository.save(training);

        for (TrainingSession iterable : trainingSessions)
            iterable.setTrainingId(training.getId());

        trainingSessionRepository.save(trainingSessions);


    }

    public void updateTraining(Training training, List<TrainingSession> trainingSessionNew) throws MongoException {
        List<TrainingSession> trainingSession = this.trainingSessionRepository.findBytrainingId(training.getId());
        trainingRepository.save(training);
        trainingSessionRepository.delete(trainingSession);
        trainingSessionRepository.save(trainingSessionNew);

    }

    public List<TrainingDomain> getAllTrainings(String employeeId) throws MongoException {

        boolean flag;

        List<Training> trainings = trainingRepository.findBySeatsGreaterThan(0);
        List<EmployeeTraining> employeeTrainings = employeeTrainingRepository.findByempId(employeeId);
        List<TrainingDomain> trainingDomains = new LinkedList<>();


        for (Training training : trainings) {
            flag = true;
            for (EmployeeTraining employeeTraining : employeeTrainings) {
                if (training.getId().equals(employeeTraining.getTrainingId()))
                    flag = false;
            }
            if (flag) {
                TrainingDomain trainingDomain = new TrainingDomain();
                trainingDomain.setTraining(training);

                //find all the trainingsession by id and sort it using the trainingDate.
                //finding all the trainingsession by id
                List<TrainingSession> trainingSessions = trainingSessionRepository.findBytrainingId(training.getId());
                if(trainingSessions == null) {
                    Collections.sort(trainingSessions, new Comparator<TrainingSession>() {
                        @Override
                        public int compare(TrainingSession o1, TrainingSession o2) {
                            return o1.getTrainingDate().compareTo(o2.getTrainingDate());
                        }
                    });
                }

                trainingDomain.setTrainingSessions(trainingSessions);
                trainingDomains.add(trainingDomain);
            }
        }

        return trainingDomains;
    }

    public void enrollTraining(String empId, String trainingId) throws MongoException {
        EmployeeTraining empTraining = new EmployeeTraining(empId, trainingId, new Date());
        employeeTrainingRepository.save(empTraining);
        Training training = trainingRepository.findByid(trainingId);
        training.setSeats(training.getSeats() - 1);
        trainingRepository.save(training);
    }

    public List<Training> searchTraining(String search) throws MongoException {

        return trainingRepository.findByNameRegex(search);
    }
}