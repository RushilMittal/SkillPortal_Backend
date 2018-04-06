package com.teksystems.skillportal.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
@Configuration
public class MongoConfigNew {
	public @Bean
	MongoTemplate mongoTemplate() throws Exception{
		
		MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("10.188.27.105"),"Skill_Portal");
		return mongoTemplate;
	}
	
}
