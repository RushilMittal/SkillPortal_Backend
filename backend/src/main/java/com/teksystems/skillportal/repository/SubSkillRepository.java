package com.teksystems.skillportal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teksystems.skillportal.model.*;


public interface SubSkillRepository extends MongoRepository<SubSkill,String> {
	 
	 /**
	    * This method will find an Booking instance in the database by its departure.
	    * Note that this method is not implemented and its working code will be
	    * automatically generated from its signature by Spring Data JPA.
	    */

	 SubSkill findById(String id);
	 SubSkill findOneBySkillGroup(String skillGroup);
	List<SubSkill> findBySkillGroupAndSkill(String skillGroup,String skill);
	SubSkill findBySkillAndSubSkill(String skill, String subSkill);
	List<SubSkill> findBySkillGroup(String skillGroup);
	List<SubSkill> findBySkill(String skill);


}