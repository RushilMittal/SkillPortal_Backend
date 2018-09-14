package com.teksystems.skillportal.repository;

import com.teksystems.skillportal.model.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends MongoRepository<Skill, String> {

    public Skill findById(String id);

    public Skill findBySkillName(String skillName);
}
