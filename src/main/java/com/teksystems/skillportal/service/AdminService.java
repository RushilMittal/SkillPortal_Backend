package com.teksystems.skillportal.service;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.init.GuavaCacheInit;
import com.teksystems.skillportal.model.AdminRoles;
import com.teksystems.skillportal.model.Certification;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.AdminRoleRepository;
import com.teksystems.skillportal.repository.CertificationRepository;
import com.teksystems.skillportal.repository.SubSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class AdminService {
    @Autowired
    AdminRoleRepository adminRoleRepository;
    @Autowired
    SubSkillRepository subSkillRepository;
    @Autowired
    private CertificationRepository certificationRepository;

    //method to validate whether the role is present or not in the list
    public boolean IsAdmin(String role){
        AdminRoles adminRoles = adminRoleRepository.findByUserRole(role);
        if(!((adminRoles == null) || (adminRoles.getUserRole().isEmpty())))
            return true;
        return false;
    }


    public List<SubSkill> getAllAdminSkills() throws ExecutionException {
        List<SubSkill> toReturn = subSkillRepository.findAll();
        System.out.println(toReturn);
        return toReturn;

    }


    public void updateNewSkill(SubSkill subSkillReceived) {
        SubSkill inCollection = subSkillRepository.findById(subSkillReceived.getId());

        if(inCollection!=null){
            inCollection.setSubSkill(subSkillReceived.getSubSkill());
            inCollection.setSkill(subSkillReceived.getSkill());
            inCollection.setSkillGroup(subSkillReceived.getSkillGroup());
            inCollection.setSubSkillDesc(subSkillReceived.getSubSkillDesc());
            subSkillRepository.save(inCollection);
            this.reloadCache();
            this.reloadSkillCache();
        }

    }

    public void addNewSkill(SubSkill subSkillReceived) {
        subSkillRepository.save(subSkillReceived);
        this.reloadCache();
        this.reloadSkillCache();
    }
    public void reloadCache(){
        GuavaCacheInit.getLoadingCache().invalidateAll();
        Map<String,List<String>> skillGroupMap = GuavaCacheInit.loadSkillGroup();
        GuavaCacheInit.skillGroupCache.putAll(skillGroupMap);
    }
    public void reloadSkillCache(){
        GuavaCacheInit.getSkillLoadingCache().invalidateAll();
        Map<String,List<SubSkill>> skillMap = GuavaCacheInit.loadSkill();
        GuavaCacheInit.skillCache.putAll(skillMap);

    }

    /*
    * Admin Method
    * method for updating the current certification in the collection
    */

    public void updateCertificate(CertificationDomain certificate){
        Certification inCollectionCertification = certificationRepository.findById(certificate.getId());
        if(inCollectionCertification!=null){
            inCollectionCertification.setSkillId(certificate.getSkillId());
            inCollectionCertification.setCertificationName(certificate.getCertificationName());
            inCollectionCertification.setInstitution(certificate.getInstitution());
            certificationRepository.save(inCollectionCertification);
        }

    }


    // Add new Certification, if not in list
    public void postNewCertification(CertificationDomain certification) throws Exception
    {
        Certification certification1 = new Certification();
        certification1.setSkillId(certification.getSkillId());
        certification1.setCertificationName(certification.getCertificationName());
        certification1.setInstitution(certification.getInstitution());
        int flag=0;
        List<Certification> certifications = this.certificationRepository.findAll();

        for(Certification iterable: certifications)
        {
            if(iterable.getInstitution().equalsIgnoreCase(certification1.getInstitution())
                    && iterable.getCertificationName().equalsIgnoreCase(certification1.getCertificationName()))
            {
                flag = 1;
            }
        }

        if(flag == 0)         {
            //Generating random Id for Certification Model
            Certification temp = new Certification();
            String certification_id="";
            Random rand = new Random();

            //Exception Handling: NullPointerException
            try
            {
                do
                {
                    certification_id = Integer.toString(rand.nextInt(1000000));
                    temp = this.certificationRepository.findOne(certification_id);
                }
                while(temp.getId() == certification_id);
            }
            catch (NullPointerException e)
            {
                System.out.println(e);
            }

            certification1.setId(certification_id);

            //To save a new entry
            System.out.println("saved");
            this.certificationRepository.save(certification1);
        }
    }

}

