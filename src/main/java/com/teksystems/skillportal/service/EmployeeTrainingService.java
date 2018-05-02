package com.teksystems.skillportal.service;

import com.teksystems.skillportal.domain.EmployeeTrainingDomain;
import com.teksystems.skillportal.domain.EmployeeTrainingPlaceholderDomain;
import com.teksystems.skillportal.domain.TrainingEventDomain;
import com.teksystems.skillportal.domain.TrainingListEventDomain;
import com.teksystems.skillportal.model.EmployeeTraining;
import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.model.TrainingSession;
import com.teksystems.skillportal.repository.EmployeeTrainingRepository;
import com.teksystems.skillportal.repository.TrainingRepository;
import com.teksystems.skillportal.repository.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EmployeeTrainingService {

    @Autowired
    EmployeeTrainingRepository employeeTrainingRepository;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    TrainingSessionRepository trainingSessionRepository;


    public List<EmployeeTrainingDomain> getEmployeeTrainingByEmployeeId(String empId) throws Exception {

        List<EmployeeTrainingDomain> employeeTrainingDomains = new LinkedList<>();
        try {
            List<EmployeeTraining> employeeTraining = employeeTrainingRepository.findByempId(empId);

            for (EmployeeTraining iterable : employeeTraining) {

                Training training = trainingRepository.findByid(iterable.getTrainingId());
                EmployeeTrainingDomain temp = new EmployeeTrainingDomain(iterable.getEmpId(), training, iterable.getLastModified());
                employeeTrainingDomains.add(temp);
            }
        }
        catch(Exception e){
         e.printStackTrace();
        }


        return employeeTrainingDomains;
    }


    public List<TrainingEventDomain> getTrainingEventByEmployeeId(String empId) throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<TrainingEventDomain> trainingEventDomains = new LinkedList<>();
        try {
            List<EmployeeTraining> employeeTrainings = employeeTrainingRepository.findByempId(empId);

            for (EmployeeTraining iterable : employeeTrainings) {
                List<TrainingSession> trainingSessions = trainingSessionRepository.findBytrainingId(iterable.getTrainingId());
                Training training = trainingRepository.findByid(iterable.getTrainingId());

                for (TrainingSession inner_iterable : trainingSessions) {
                    String strDate = dateFormat.format(inner_iterable.getTrainingDate());
                    TrainingEventDomain trainingEventDomain = new TrainingEventDomain(iterable.getTrainingId(), training.getName(), strDate + "T" + inner_iterable.getStartTime(), strDate + "T" + inner_iterable.getEndTime());
                    trainingEventDomains.add(trainingEventDomain);

                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return trainingEventDomains;
    }

    public void cancelEnrollment(String empId,String trainingId) throws  Exception{
        Training training = trainingRepository.findByid(trainingId);
        EmployeeTraining employeeTraining = this.employeeTrainingRepository.findByEmpIdAndTrainingId(empId,trainingId);
        this.employeeTrainingRepository.delete(employeeTraining);
        System.out.println(training.getSeats());
        training.setSeats(training.getSeats()+1);
        trainingRepository.save(training);

        System.out.println(training.getSeats());

    }

    public List<TrainingListEventDomain> getTrainingListEventByEmployeeId(String empId) throws Exception{
        List<TrainingListEventDomain> trainingListEventDomains=new LinkedList<>();
        try {
            List<EmployeeTraining> employeeTrainings = employeeTrainingRepository.findByempId(empId);

            for (EmployeeTraining iterable : employeeTrainings){
                List<TrainingSession> trainingSessions = trainingSessionRepository.findBytrainingId(iterable.getTrainingId());
                Training training = trainingRepository.findByid(iterable.getTrainingId());
                for (TrainingSession inner_iterable : trainingSessions){
                    TrainingListEventDomain trainingListEventDomain=new TrainingListEventDomain(
                            inner_iterable.getTrainingId(),
                            inner_iterable.getTrainingDate(),
                            inner_iterable.getStartTime(),
                            inner_iterable.getEndTime(),
                            training.getName(),
                            training.getTrainer(),
                            training.getLocation());
                    trainingListEventDomains.add(trainingListEventDomain);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return trainingListEventDomains;
    }

    public List<EmployeeTrainingPlaceholderDomain> getUpcomingTraining() throws Exception {
        List<Training> training = trainingRepository.findAll();
        List<EmployeeTrainingPlaceholderDomain> empTrainList = new LinkedList<>();


        try {
            for (Training iterable : training) {
                List<TrainingSession> trainingSession = trainingSessionRepository.findBytrainingId(iterable.getId());
                Collections.sort(trainingSession);
                System.out.println(trainingSession);
                EmployeeTrainingPlaceholderDomain empTrainingPlaceholderDomain = new EmployeeTrainingPlaceholderDomain();
                empTrainingPlaceholderDomain.setTrainingId(iterable.getId());
                empTrainingPlaceholderDomain.setName(iterable.getName());
                empTrainingPlaceholderDomain.setTrainingDate(trainingSession.get(0).getTrainingDate());
                empTrainList.add(empTrainingPlaceholderDomain);

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return empTrainList;
    }

    public List<EmployeeTrainingPlaceholderDomain> getEnrolledTraining(String empId) throws  Exception{
        List<EmployeeTraining> empTraining= employeeTrainingRepository.findByempId(empId);
        List<EmployeeTrainingPlaceholderDomain> empTrainList = new LinkedList<>();
        try {
            for (EmployeeTraining iterable : empTraining) {
                Training training = trainingRepository.findByid(iterable.getTrainingId());
                List<TrainingSession> trainingSession = trainingSessionRepository.findBytrainingId(iterable.getTrainingId());
                Collections.sort(trainingSession);
                EmployeeTrainingPlaceholderDomain empTrainingPlaceholderDomain = new EmployeeTrainingPlaceholderDomain();
                empTrainingPlaceholderDomain.setTrainingId(iterable.getTrainingId());
                empTrainingPlaceholderDomain.setName(training.getName());
                empTrainingPlaceholderDomain.setTrainingDate(trainingSession.get(0).getTrainingDate());
                empTrainList.add(empTrainingPlaceholderDomain);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return empTrainList;
    }
}



