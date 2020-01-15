package com.cdac.skillportal.repository;

import com.cdac.skillportal.model.Training;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrainingRepository extends MongoRepository<Training, String> {

    public Training findByid(String id);
    public List<Training> findBySeatsGreaterThan(int seats);

    public List<Training> findByNameRegex(String name);

    public List<Training> findByLocation(String location);

    public List<Training> findByType(String type);

    public List<Training> findByTrainer(String trainer);

}




