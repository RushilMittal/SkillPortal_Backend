package com.teksystems.skillportal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="subskill")
public class SubSkill {
	
	@Id
	String id;
	String subSkill;
	String subSkillDesc;
	String skill;
	String skillGroup;
	String practice;
	
	
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
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
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
				", subSkillDesc='" + subSkillDesc + '\'' +
				", skill='" + skill + '\'' +
				", skillGroup='" + skillGroup + '\'' +
				", practice='" + practice + '\'' +
				'}';
	}
}
