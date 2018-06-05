package com.teksystems.skillportal.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teksystems.skillportal.model.*;


public interface SubSkillRepository extends MongoRepository<SubSkill,String> {
	 


	 SubSkill findById(String id);
	 SubSkill findBySubSkill(String subSkill);
	 SubSkill findOneBySkillGroup(String skillGroup);
	List<SubSkill> findBySkillGroupAndSkill(String skillGroup,String skill);
	SubSkill findBySkillAndSubSkill(String skill, String subSkill);
	List<SubSkill> findBySkillGroup(String skillGroup);
	List<SubSkill> findBySkill(String skill);


}