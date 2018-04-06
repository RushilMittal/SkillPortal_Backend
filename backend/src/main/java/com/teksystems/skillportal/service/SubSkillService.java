package com.teksystems.skillportal.service;


import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.init.GuavaCacheInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;

import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.init.MongoConfigNew;
import com.teksystems.skillportal.model.Skill;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.SkillRepository;
import com.teksystems.skillportal.repository.SubSkillRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class SubSkillService {

    @Autowired
    SkillRepository skillRepository;
    
    @Autowired
    SubSkillRepository subSkillRepository;
    
    public Map<String,List<SubSkill>> getAllSubSkillsOfEmployee(String skillName) throws ExecutionException{

        LoadingCache<String, List<SubSkill>> skillCache = GuavaCacheInit.getSkillLoadingCache();
        System.out.println("Cache Size:" + skillCache.size());
        return skillCache.asMap();

    }
    
    /* Service to get all subskills from subskill database
	 *  
	*/	
//    public List<SubSkillDomain> getAll(){
//        List<SubSkill> subSkills=this.subSkillRepository.findAll();
//        List<SubSkillDomain> subSkillDomains=  new LinkedList<SubSkillDomain>();
//
//      //transforming subskills to subskill domain type
//        for(SubSkill iterable: subSkills){
//
//            SubSkillDomain temp = new SubSkillDomain(iterable.getId(),iterable.getName(),iterable.getSkillId());
//
//            subSkillDomains.add(temp);
//        }
//
//        return subSkillDomains;
//    }
//
//    public List<SubSkill> searchSkill(String searchTerm)
//    {
//  	  ApplicationContext ctx =
//                new AnnotationConfigApplicationContext(MongoConfigNew.class);
//  	  MongoOperations mongoOperation =
//                (MongoOperations) ctx.getBean("mongoTemplate");
//
//  		BasicQuery query = new BasicQuery("{'name': {$regex : '"+searchTerm+"', '$options': 'i'}}");
//  		return mongoOperation.find(query, SubSkill.class);
//
//    }
//
//
//    public void insert(SubSkill subSkill)
//    {
//        this.subSkillRepository.insert(subSkill);
//    }
//
//
//    /* Service to get a subskill by subskill id
//	 *
//	*/
//
//   public SubSkillDomain getBySubSkillId(String subSkillId){
//
//        SubSkill subSkill = subSkillRepository.findById(subSkillId);
//
//      //transforming subskill to subskill domain type
//
//        return new SubSkillDomain(subSkill.getId(),subSkill.getName(),subSkill.getSkillId());
//
//    }
//
//
//   /* Service to get all subskills by skill id,
//    *  to get all subskills that belong to a particular skill
//	*/
//
//    public List<SubSkillDomain> getAllBySkillId(String skillId){
//
//        List<SubSkill> subSkills = subSkillRepository.findBySkillId(skillId);
//
//        List<SubSkillDomain> subSkillDomains=  new LinkedList<>();
//
//      //transforming subskills to subskill domain type
//
//        for(SubSkill iterable: subSkills){
//
//            SubSkillDomain temp = new SubSkillDomain(iterable.getId(),iterable.getName(),iterable.getSkillId());
//
//            subSkillDomains.add(temp);
//        }
//
//        return subSkillDomains;
//    }

    
 
}