package com.cdac.skillportal.domain;

public class SkillDomain {

    private String id;
    private int ratedUsers;

    public SkillDomain() {
    }

    public SkillDomain(String id, int ratedUsers) {
        this.id = id;
        this.ratedUsers = ratedUsers;
    }

    public String getId() {
        return id;
    }

    public int getRatedUsers() {
        return ratedUsers;
    }

}
