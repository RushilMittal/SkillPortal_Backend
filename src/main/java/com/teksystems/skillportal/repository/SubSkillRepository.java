package com.teksystems.skillportal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teksystems.skillportal.model.*;
import org.springframework.stereotype.Repository;

@Repository
public interface SubSkillRepository extends MongoRepository<SubSkill,String> {
	 


	 SubSkill findById(String id);
	 SubSkill findBySubSkill(String subSkill);

}