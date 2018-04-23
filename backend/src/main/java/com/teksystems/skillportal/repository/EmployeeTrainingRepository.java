package com.teksystems.skillportal.repository;

import com.teksystems.skillportal.model.EmployeeTraining;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeTrainingRepository extends MongoRepository<EmployeeTraining,String>{

    public EmployeeTraining findByid(String Id);
    public List<EmployeeTraining> findByempId(String employeeId);
    public EmployeeTraining findByEmpIdAndTrainingId(String employeeId,String trainingId);
    public List<EmployeeTraining> findBylastModified(String Type);
    public List<EmployeeTraining> findBytrainingId(String trainingId);


}
