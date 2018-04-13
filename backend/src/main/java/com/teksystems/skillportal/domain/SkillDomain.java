package com.teksystems.skillportal.domain;

import com.teksystems.skillportal.model.SubSkill;

import java.util.List;

public class SkillDomain {

    String skill;
    List<SubSkill> subSkills;

    public SkillDomain(String skill, List<SubSkill> subSkills) {
        this.skill = skill;
        this.subSkills = subSkills;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public List<SubSkill> getSubSkills() {
        return subSkills;
    }

    public void setSubSkills(List<SubSkill> subSkills) {
        this.subSkills = subSkills;
    }
}
