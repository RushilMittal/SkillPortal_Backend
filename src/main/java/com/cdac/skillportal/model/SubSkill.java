package com.cdac.skillportal.model;

import com.cdac.skillportal.helper.ConfigurationStrings;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = ConfigurationStrings.SUBSKILL)
public class SubSkill {

    @Id
    String id;
    String subSkill;
    String subSkillDesc;
    String skill;
    String skillGroup;
    String practice;

    public SubSkill() {
    }

    public SubSkill(String id, String subSkill, String subSkillDesc, String modelSkill, String skillGroup, String practice) {
        this.id = id;
        this.subSkill = subSkill;
        this.subSkillDesc = subSkillDesc;
        this.skill = modelSkill;
        this.skillGroup = skillGroup;
        this.practice = practice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubSkill() {
        return subSkill;
    }

    public void setSubSkill(String subSkill) {
        this.subSkill = subSkill;
    }

    public String getSubSkillDesc() {
        return subSkillDesc;
    }

    public void setSubSkillDesc(String subSkillDesc) {
        this.subSkillDesc = subSkillDesc;
    }

    public String getModelSkill() {
        return skill;
    }

    public void setModelSkill(String modelSkill) {
        this.skill = modelSkill;
    }

    public String getSkillGroup() {
        return skillGroup;
    }

    public void setSkillGroup(String skillGroup) {
        this.skillGroup = skillGroup;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    @Override
    public String toString() {
        return "SubSkill{" +
                "id='" + id + '\'' +
                ", subSkill='" + subSkill + '\'' +
                ", modelSubSkillDesc='" + subSkillDesc + '\'' +
                ", modelSkill='" + skill + '\'' +
                ", modelSkillGroup='" + skillGroup + '\'' +
                ", modelPractice='" + practice + '\'' +
                '}';
    }
}
