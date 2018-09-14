package com.teksystems.skillportal.model;

import com.teksystems.skillportal.helper.ConfigurationStrings;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = ConfigurationStrings.SUBSKILL)
public class SubSkill {

    @Id
    String id;
    String modelSubSkill;
    String modelSubSkillDesc;
    String modelSkill;
    String modelSkillGroup;
    String modelPractice;

    public SubSkill() {
    }

    public SubSkill(String id, String subSkill, String modelSubSkillDesc, String modelSkill, String modelSkillGroup, String modelPractice) {
        this.id = id;
        this.modelSubSkill = subSkill;
        this.modelSubSkillDesc = modelSubSkillDesc;
        this.modelSkill = modelSkill;
        this.modelSkillGroup = modelSkillGroup;
        this.modelPractice = modelPractice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubSkill() {
        return modelSubSkill;
    }

    public void setSubSkill(String subSkill) {
        this.modelSubSkill = subSkill;
    }

    public String getModelSubSkillDesc() {
        return modelSubSkillDesc;
    }

    public void setModelSubSkillDesc(String modelSubSkillDesc) {
        this.modelSubSkillDesc = modelSubSkillDesc;
    }

    public String getModelSkill() {
        return modelSkill;
    }

    public void setModelSkill(String modelSkill) {
        this.modelSkill = modelSkill;
    }

    public String getModelSkillGroup() {
        return modelSkillGroup;
    }

    public void setModelSkillGroup(String modelSkillGroup) {
        this.modelSkillGroup = modelSkillGroup;
    }

    public String getModelPractice() {
        return modelPractice;
    }

    public void setModelPractice(String modelPractice) {
        this.modelPractice = modelPractice;
    }

    @Override
    public String toString() {
        return "SubSkill{" +
                "id='" + id + '\'' +
                ", subSkill='" + modelSubSkill + '\'' +
                ", modelSubSkillDesc='" + modelSubSkillDesc + '\'' +
                ", modelSkill='" + modelSkill + '\'' +
                ", modelSkillGroup='" + modelSkillGroup + '\'' +
                ", modelPractice='" + modelPractice + '\'' +
                '}';
    }
}
