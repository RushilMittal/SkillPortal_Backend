package com.cdac.skillportal.domain;

public class EmployeeSkillPlaceholderDomain {

    int numberOfSkillRated;
    String higestRatedSkill;
    int highestRating;
    int[] lastUpdatedPeriod;

    public EmployeeSkillPlaceholderDomain(int numberOfSkillRated, String higestRatedSkill, int highestRating, int[] lastUpdatedPeriod) {
        this.numberOfSkillRated = numberOfSkillRated;
        this.higestRatedSkill = higestRatedSkill;
        this.highestRating = highestRating;
        this.lastUpdatedPeriod = lastUpdatedPeriod;
    }

    public int getNumberOfSkillRated() {
        return numberOfSkillRated;
    }

    public String getHigestRatedSkill() {
        return higestRatedSkill;
    }

    public int getHighestRating() {
        return highestRating;
    }

    public int[] getLastUpdatedPeriod() {
        return lastUpdatedPeriod;
    }


}