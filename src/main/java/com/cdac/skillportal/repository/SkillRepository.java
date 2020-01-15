package com.cdac.skillportal.repository;

import com.cdac.skillportal.model.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends MongoRepository<Skill, String> {

    public Skill findById(String id);

    public Skill findBySkillName(String skillName);
}
