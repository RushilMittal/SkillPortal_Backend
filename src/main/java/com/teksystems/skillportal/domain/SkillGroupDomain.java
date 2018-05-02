package com.teksystems.skillportal.domain;

import java.util.List;

public class SkillGroupDomain {
    String skillGroup;
    List<String> skills;


    public SkillGroupDomain(String skillGroup, List<String> skills) {
        this.skillGroup = skillGroup;
        this.skills = skills;
    }

    public String getSkillGroup() {
        return skillGroup;
    }

    public void setSkillGroup(String skillGroup) {
        this.skillGroup = skillGroup;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
