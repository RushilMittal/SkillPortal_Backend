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
    


    
 
}