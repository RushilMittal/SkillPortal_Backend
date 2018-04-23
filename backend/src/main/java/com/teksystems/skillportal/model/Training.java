package com.teksystems.skillportal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "training")
public class Training {
	
	@Id
	String id;
	String name;
	String location;
	int seats;
	String type;
	String description;
	String trainer;

	public Training()
    {

    }

    public Training(String id, String name, String location, int seats, String type, String description, String trainer) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.seats = seats;
        this.type = type;
        this.description = description;
        this.trainer = trainer;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}
}
