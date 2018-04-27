package com.teksystems.skillportal.repository;

import com.teksystems.skillportal.model.Training;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrainingRepository extends MongoRepository<Training,String> {

   public Training findByid(String id);
   public Training findByName(String Name);
   public List<Training> findByLocation(String Location);
   public List<Training> findByType(String Type);
   public List<Training> findByTrainer(String Trainer);

}




