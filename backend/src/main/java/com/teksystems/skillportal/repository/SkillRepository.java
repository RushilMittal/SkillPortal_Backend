package com.teksystems.skillportal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.teksystems.skillportal.model.Skill;

@Repository
public interface SkillRepository extends MongoRepository<Skill,String>{

   public Skill findById(String Id);
   public Skill findBySkillName(String skillName);
}
