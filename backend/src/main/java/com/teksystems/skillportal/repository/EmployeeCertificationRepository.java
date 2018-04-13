package com.teksystems.skillportal.repository;

import com.teksystems.skillportal.model.EmployeeCertification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmployeeCertificationRepository extends MongoRepository<EmployeeCertification,String> {
	public List<EmployeeCertification> findByempId(String employeeId);
	public List<EmployeeCertification> findBycertificationNumber(int certificationNumber);

}
