package com.teksystems.skillportal.domain;

public class CertificationDomain {
    
	String id;
    String skillId;
    String certificationName;
    String institution;
    
    public CertificationDomain(String id, String skillId, String certificationName, String institution) {
    	this.id=id;
        this.skillId = skillId;
        this.certificationName = certificationName;
        this.institution = institution;
    }

    public CertificationDomain(String skillId, String certificationName, String institution) {
        this.skillId = skillId;
        this.certificationName = certificationName;
        this.institution = institution;
    }

    public CertificationDomain(){}
    
    public String getId() {
    	return id;
    }
    
    public String getSkillId() {
        return skillId;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public String getInstitution() {
        return institution;
    }
}
