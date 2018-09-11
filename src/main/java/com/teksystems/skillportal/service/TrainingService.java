package com.teksystems.skillportal.service;

import com.mongodb.MongoException;
import com.teksystems.skillportal.domain.TrainingDomain;
import com.teksystems.skillportal.init.MongoConfigNew;
import com.teksystems.skillportal.model.EmployeeTraining;
import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.model.TrainingSession;
import com.teksystems.skillportal.repository.EmployeeTrainingRepository;
import com.teksystems.skillportal.repository.TrainingRepository;
import com.teksystems.skillportal.repository.TrainingSessionRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

        try {

            Training t = trainingRepository.save(training);

            for (TrainingSession iterable : trainingSessions) {
                iterable.setTrainingId(t.getId());
            }
            trainingSessionRepository.save(trainingSessions);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    public void updateTraining(Training training, List<TrainingSession> trainingSessionNew) throws MongoException {
        List<TrainingSession> trainingSession = this.trainingSessionRepository.findBytrainingId(training.getId());
        trainingRepository.save(training);
        trainingSessionRepository.delete(trainingSession);
        trainingSessionRepository.save(trainingSessionNew);

    }

    public List<TrainingDomain> getAllTrainings(String employeeId) throws MongoException {

        boolean flag;
        Query query = new Query(Criteria.where("seats").gte(0));
        List<Training> trainings = mongoOperation.find(query, Training.class);
        List<EmployeeTraining> employeeTrainings = employeeTrainingRepository.findByempId(employeeId);
        List<TrainingDomain> trainingDomains = new LinkedList<>();

        try {
            for (Training training : trainings) {
                flag = true;
                for (EmployeeTraining employeeTraining : employeeTrainings) {
                    if (training.getId().equals(employeeTraining.getTrainingId()))
                        flag = false;
                }
                if (flag) {
                    TrainingDomain trainingDomain = new TrainingDomain();
                    trainingDomain.setTraining(training);
                    Query queryToSort = new Query(Criteria.where("trainingId").is(training.getId()));
                    queryToSort.with(new Sort(Sort.Direction.DESC, "trainingDate"));
                    List<TrainingSession> trainingSessions = mongoOperation.find(queryToSort, TrainingSession.class);
                    trainingDomain.setTrainingSessions(trainingSessions);
                    trainingDomains.add(trainingDomain);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
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
        Query query = new Query(Criteria.where("name").regex(search, "i"));
        return mongoOperation.find(query, Training.class);
    }
}