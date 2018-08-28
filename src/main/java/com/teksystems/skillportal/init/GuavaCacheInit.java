package com.teksystems.skillportal.init;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.teksystems.skillportal.model.SubSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mongodb.BasicDBObject;
import org.springframework.stereotype.Component;

@Component
public class GuavaCacheInit {
	
    ApplicationContext ctx =
            new AnnotationConfigApplicationContext(MongoConfigNew.class);
    MongoOperations mongoOperation =
            (MongoOperations) ctx.getBean("mongoTemplate");
	
    public LoadingCache<String, List<String>> skillGroupCache;
    public LoadingCache<String, List<SubSkill>> skillCache;

    @Autowired
	public GuavaCacheInit()
	  {
	    	skillGroupCache = CacheBuilder.newBuilder()
		       .build(
		           new CacheLoader<String, List<String>>() 
		           {
	        		  @Override
				      public List<String> load(String key) throws Exception 
	        		   {
					     return getSkillGroup(key);
				       }
		           });
	    	
	    	skillCache = CacheBuilder.newBuilder()
	 		       .build(
	 		           new CacheLoader<String, List<SubSkill>>() 
	 		           {
	 	        		  @Override
	 				      public List<SubSkill> load(String key) throws Exception 
	 	        		   {
	 					     return getSkill(key);
	 				       }
	 		           });
	   }
	
	 
	public LoadingCache<String, List<String>> getLoadingCache()
	   {
			return skillGroupCache;
	   }
	 
	public LoadingCache<String, List<SubSkill>> getSkillLoadingCache()
	   {
			return skillCache;
	   }
	
	
	public  List<String> getSkillGroup(String key) {
		 System.out.println("Method Executes");


		 return mongoOperation.getCollection("subskill").distinct("skill",new BasicDBObject("skillGroup",key));
	 }
	
	
	public List<SubSkill> getSkill(String key) {
		 System.out.println("Method Executes");
		 String[] splitKey = key.split("_");
		 Criteria criteria = new Criteria();
	     criteria.andOperator(Criteria.where("skillGroup").is(splitKey[0]),Criteria.where("skill").is(splitKey[1]));
	     Query query = new Query(criteria);
		 return mongoOperation.find(query, SubSkill.class);
	 }
	 
	
	
	public  Map<String,List<String>> loadSkillGroup()
		{
			System.out.println("Executing SkillGroup Method");
				
			Map<String,List<String>> skillGroupMap = new HashMap<>();
	     
			List<String> skillGroups =  mongoOperation.getCollection("subskill").distinct("skillGroup");
			
			for(String iterable : skillGroups)
			{
				List<String> skills =  mongoOperation.getCollection("subskill").distinct("skill",new BasicDBObject("skillGroup",iterable));
				skillGroupMap.put(iterable,skills);
			}
			  System.out.println("Function Exits");   
			return skillGroupMap;
		}
	
	
	public  Map<String,List<SubSkill>> loadSkill()
	{
		
		Map<String,List<String>> skillGroupMap = loadSkillGroup();
		
		Map<String,List<SubSkill>> skillMap = new HashMap<>();
		
		for(String key : skillGroupMap.keySet())
		{
			List<String> skillNames = skillGroupMap.get(key);
			
			for(String iterable : skillNames)
			{
			  Criteria criteria = new Criteria();
			  criteria.andOperator(Criteria.where("skillGroup").is(key),Criteria.where("skill").is(iterable));
			  Query query = new Query(criteria);
			  List<SubSkill> subSkills = mongoOperation.find(query, SubSkill.class);			
			  skillMap.put(key+"_"+iterable,subSkills);
			}
		}
		return skillMap;
	}

}
