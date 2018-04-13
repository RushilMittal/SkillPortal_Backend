package com.teksystems.skillportal.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
@Configuration
public class MongoConfigNew {
	public @Bean
	MongoTemplate mongoTemplate() throws Exception{
		
		MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("localhost"),"Skill_Portal");
		return mongoTemplate;
	}
	
}
