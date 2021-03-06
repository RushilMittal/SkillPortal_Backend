package com.cdac.skillportal.model;

import com.cdac.skillportal.helper.ConfigurationStrings;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = ConfigurationStrings.SKILL)
public class Skill {

    @Id
    String id;
    String skillName;


    public Skill(String id, String skillName) {
        this.id = id;
        this.skillName = skillName;
    }


    public Skill() {
        //  Auto-generated constructor stub
    }


    public String getId() {
        return id;
    }


    public String getName() {
        return skillName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String skillName) {
        this.skillName = skillName;
    }
}
