package com.teksystems.skillportal.repository;


import com.teksystems.skillportal.model.SubSkill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubSkillRepository extends MongoRepository<SubSkill, String> {


    SubSkill findById(String id);

    SubSkill findBySubSkill(String subSkill);

}