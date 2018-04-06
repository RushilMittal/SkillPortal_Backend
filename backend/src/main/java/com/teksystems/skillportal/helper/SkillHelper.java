package com.teksystems.skillportal.helper;

import com.teksystems.skillportal.service.EmployeeSkillService;
import com.teksystems.skillportal.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillHelper {

    @Autowired
    SkillService skillService;

    @Autowired
    EmployeeSkillService employeeSkillService;

//    public List<SkillDomain> getAll(){
//
//        List<Skill> skills=this.skillService.getAll();
//        List<SkillDomain> skillDomains=  new LinkedList<SkillDomain>();
//
//        //transforming skills to skill domain type
//        for(Skill iterable: skills){
//
//            SkillDomain temp = new SkillDomain(iterable.getId(),iterable.getName(),employeeSkillService.getSkillCount(iterable.getId()));
//
//            skillDomains.add(temp);
//        }
//
//        return skillDomains;
//    }
//
//    public List<Skill> searchSkill(String searchTerm){
//
//        return this.skillService.searchSkill(searchTerm);
//    }
//
//    public SkillDomain getById(String skillId){
//
//        Skill skill = skillService.getById(skillId);
//
//        //transforming skill to skilldomain
//
//        return new SkillDomain(skill.getId(),skill.getName(),employeeSkillService.getSkillCount(skill.getId()));
//
//    }
//
//    public SkillDomain getBySkillName(String skillName) {
//
//        Skill skill = skillService.getBySkillName(skillName);
//        return new SkillDomain(skill.getId(),skill.getName(),employeeSkillService.getSkillCount(skill.getId()));
//
//    }
}