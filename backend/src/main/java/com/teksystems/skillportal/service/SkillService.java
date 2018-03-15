package com.teksystems.skillportal.service;

import com.teksystems.skillportal.repository.SkillRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teksystems.skillportal.domain.SkillDomain;
import com.teksystems.skillportal.model.Skill;

import java.util.LinkedList;
import java.util.List;

@Service
public class SkillService {
	
	@Autowired
    SkillRepository skillRepository;

	/* Service to get all skills from skill database
	 *  
	*/	
  public List<Skill> getAll(){
    	
      return this.skillRepository.findAll();

    }
  
  
    /* Service to get a particular skill by skill id
	 *  
	 */
  public Skill getById(String skillId){

        return skillRepository.findById(skillId);
       

      
    }
  
  public Skill getBySkillName(String skillName)
  {
      return this.skillRepository.findBySkillName(skillName);
  }
  

}
