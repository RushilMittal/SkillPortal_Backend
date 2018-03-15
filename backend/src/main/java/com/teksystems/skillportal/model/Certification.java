package com.teksystems.skillportal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Certification")
public class Certification {

    @Id
    private String id;
    private String skillId;
    private String certificationName;
    private String institution;

    public Certification(String id,String skillId, String certificationName,
                         String institution) {
    	this.id=id;
        this.skillId = skillId;
        this.certificationName = certificationName;
        this.institution = institution;
    }

    public Certification(){}
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


    public void setId(String id) {
        this.id = id;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
