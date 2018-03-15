package com.teksystems.skillportal.domain;

public class SkillDomain {

    private String id;
    private String name;
    private int ratedUsers;
    
    public SkillDomain (String id,String name,int ratedUsers)
    {
    	 this.id = id;
    	 this.name = name;
    	 this.ratedUsers = ratedUsers;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRatedUsers() {
        return ratedUsers;
    }

}
