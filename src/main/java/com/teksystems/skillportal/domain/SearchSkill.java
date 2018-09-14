package com.teksystems.skillportal.domain;

public class SearchSkill {

    String skillId;
    String name;
    boolean isChild;
    String subSkillId;

    public String getSkillId() {
        return this.skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubSkillId() {
        return this.subSkillId;
    }

    public void setSubSkillId(String subSkillId) {
        this.subSkillId = subSkillId;
    }

    public void setIsChild(boolean isChild) {
        this.isChild = isChild;
    }

    public boolean getIsChild() {
        return this.isChild;
    }

}
