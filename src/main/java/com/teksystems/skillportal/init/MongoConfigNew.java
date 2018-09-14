package com.teksystems.skillportal.init;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfigNew {
    public @Bean
    MongoTemplate mongoTemplate() throws MongoClientException {

        return new MongoTemplate(new MongoClient("localhost"), "Skill_Portal");

    }

}
