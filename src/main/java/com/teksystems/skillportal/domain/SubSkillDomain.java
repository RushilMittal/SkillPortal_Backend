package com.teksystems.skillportal.domain;

public class SubSkillDomain {
    String id;
    String subSkill;
    String subSkillDesc;
    String skill;
    String skillGroup;
    String practice;
    int totalNumberofRatedUsers;

    public SubSkillDomain(String id, String subSkill, String subSkillDesc, String skill, String skillGroup,
                          String practice, int totalNumberofRatedUsers) {
        this.id = id;
        this.subSkill = subSkill;
        this.subSkillDesc = subSkillDesc;
        this.skill = skill;
        this.skillGroup = skillGroup;
        this.practice = practice;
        this.totalNumberofRatedUsers = totalNumberofRatedUsers;
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
        return "SubSkillDomain{" +
                "id='" + id + '\'' +
                ", subSkill='" + subSkill + '\'' +
                ", subSkillDesc='" + subSkillDesc + '\'' +
                ", skill='" + skill + '\'' +
                ", skillGroup='" + skillGroup + '\'' +
                ", practice='" + practice + '\'' +
                ", totalNumberofRatedUsers=" + totalNumberofRatedUsers +
                '}';
    }
}
