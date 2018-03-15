package com.teksystems.skillportal.domain;

public class SubSkillDomain {
	
String id;
String name;
String skillId;
private int ratedUsers;

public SubSkillDomain(String id,String name,String skillId,int ratedUsers)
{
	this.id=id;
	this.name=name;
	this.skillId=skillId;
	this.ratedUsers=ratedUsers;
}

	public SubSkillDomain(String id, String name, String skillId) {
		this.id = id;
		this.name = name;
		this.skillId = skillId;
	}

	public String getId()
   {
 return this.id;	
	}


public String getName()
   {
	 return this.name;	
	}



public String getSkillId()
   {
	 return this.skillId;	
	}

	public int getRatedUsers() {
		return ratedUsers;
	}



}
