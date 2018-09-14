package com.teksystems.skillportal.repository;

import com.teksystems.skillportal.model.Training;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrainingRepository extends MongoRepository<Training, String> {

    public Training findByid(String id);

    public Training findByName(String name);

    public List<Training> findByLocation(String location);

    public List<Training> findByType(String type);

    public List<Training> findByTrainer(String trainer);

}




