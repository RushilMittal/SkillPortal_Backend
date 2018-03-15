package com.teksystems.skillportal.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.SkillRepository;
import com.teksystems.skillportal.repository.SubSkillRepository;

import java.util.LinkedList;
import java.util.List;

@Service
public class SubSkillService {

    @Autowired
    SkillRepository skillRepository;
    
    @Autowired
    SubSkillRepository subSkillRepository;
    

    
    /* Service to get all subskills from subskill database
	 *  
	*/	
    public List<SubSkillDomain> getAll(){
        List<SubSkill> subSkills=this.subSkillRepository.findAll();
        List<SubSkillDomain> subSkillDomains=  new LinkedList<SubSkillDomain>();
        
      //transforming subskills to subskill domain type
        for(SubSkill iterable: subSkills){
            
            SubSkillDomain temp = new SubSkillDomain(iterable.getId(),iterable.getName(),iterable.getSkillId());

            subSkillDomains.add(temp);
        }
        
        return subSkillDomains;
    }
   
    
    
    public void insert(SubSkill subSkill)
    {
        this.subSkillRepository.insert(subSkill);
    }
    
    
    /* Service to get a subskill by subskill id
	 *  
	*/	
    
   public SubSkillDomain getBySubSkillId(String subSkillId){

        SubSkill subSkill = subSkillRepository.findById(subSkillId);
        
      //transforming subskill to subskill domain type
        
        return new SubSkillDomain(subSkill.getId(),subSkill.getName(),subSkill.getSkillId());

    }
   
   
   /* Service to get all subskills by skill id,
    *  to get all subskills that belong to a particular skill  
	*/	
    
    public List<SubSkillDomain> getAllBySkillId(String skillId){

        List<SubSkill> subSkills = subSkillRepository.findBySkillId(skillId);
        
        List<SubSkillDomain> subSkillDomains=  new LinkedList<>();
        
      //transforming subskills to subskill domain type
        
        for(SubSkill iterable: subSkills){
            
            SubSkillDomain temp = new SubSkillDomain(iterable.getId(),iterable.getName(),iterable.getSkillId());

            subSkillDomains.add(temp);
        }
        
        return subSkillDomains;
    }

    
 
}
