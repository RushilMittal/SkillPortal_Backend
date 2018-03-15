package com.teksystems.skillportal.repository;

import com.teksystems.skillportal.model.Certification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificationRepository extends MongoRepository<Certification, String> {

    List<Certification> findBySkillId(String skillId);
    Certification findById(String id);
    Certification findBycertificationName(String certificationName);
    //List<Certification> findByEmpIdAndSkillId(String empId, String skillId);

}
