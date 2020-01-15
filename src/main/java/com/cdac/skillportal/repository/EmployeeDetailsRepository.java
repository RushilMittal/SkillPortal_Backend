package com.cdac.skillportal.repository;

import com.cdac.skillportal.model.EmployeeDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeDetailsRepository extends MongoRepository<EmployeeDetails, String> {
    EmployeeDetails findByusername(String username);
}
