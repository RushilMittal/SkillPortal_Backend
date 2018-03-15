package com.teksystems.skillportal.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="subskill")
public class SubSkill {

@Id	
String id;
String name;
String skillId;

public SubSkill(String id, String name, String skillId) {
	this.id=id;
	this.name=name;
	this.skillId=skillId;
}

public SubSkill() {
	// TODO Auto-generated constructor stub
}

public String getId(){
 return this.id;	
	}

public void setId(String subSkillId) {
 this.id=subSkillId;
    }

public String getName(){
	 return this.name;	
	}

public void setName(String name) {
	 this.name=name;
   }

public String getSkillId(){
	 return this.skillId;	
	}

public void setSkillId(String skillId) {
	 this.skillId=skillId;
  }

}

