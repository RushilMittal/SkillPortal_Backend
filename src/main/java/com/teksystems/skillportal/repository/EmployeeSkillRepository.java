package com.teksystems.skillportal.repository;

import com.teksystems.skillportal.model.EmployeeSkill;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface EmployeeSkillRepository extends MongoRepository<EmployeeSkill, String> {

    /**
     * This method will find an Booking instance in the database by its departure.
     * Note that this method is not implemented and its working code will be
     * automatically generated from its signature by Spring Data JPA.
     */

    public List<EmployeeSkill> findByEmpId(String empId);

    public EmployeeSkill findById(String id);

    List<EmployeeSkill> findByEmpIdAndSubSkillId(String empId, String subSkillId);

    List<EmployeeSkill> findDistinctEmployeeSkillBySubSkillId(String subSkillId);

    List<EmployeeSkill> findBySubSkillId(String subSkillId);

    EmployeeSkill findTopByEmpIdOrderByLastModifiedDateDesc(String empId);
}
