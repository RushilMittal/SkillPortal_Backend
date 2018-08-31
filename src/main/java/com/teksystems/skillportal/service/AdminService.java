package com.teksystems.skillportal.service;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.init.GuavaCacheInit;
import com.teksystems.skillportal.model.AdminRoles;
import com.teksystems.skillportal.model.Certification;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.AdminRoleRepository;
import com.teksystems.skillportal.repository.CertificationRepository;
import com.teksystems.skillportal.repository.SubSkillRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.LinkedList;
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

    @Autowired
    GuavaCacheInit guavaCacheInit;
    private static Logger logger = Logger.getLogger(AdminService.class);

    //method to validate whether the role is present or not in the list
    public boolean IsAdmin(String role) {
        AdminRoles adminRoles = adminRoleRepository.findByUserRole(role);
        if (!((adminRoles == null) || (adminRoles.getUserRole().isEmpty())))
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

        if (inCollection != null) {
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

    public void reloadCache() {
        guavaCacheInit.getLoadingCache().invalidateAll();
        Map<String, List<String>> skillGroupMap = new GuavaCacheInit().loadSkillGroup();
        guavaCacheInit.skillGroupCache.putAll(skillGroupMap);
    }

    public void reloadSkillCache() {
        guavaCacheInit.getSkillLoadingCache().invalidateAll();
        Map<String, List<SubSkill>> skillMap = new GuavaCacheInit().loadSkill();
        guavaCacheInit.skillCache.putAll(skillMap);

    }

    /*
     * Admin Method
     * method for updating the current certification in the collection
     */

    public void updateCertificate(CertificationDomain certificate) {
        Certification inCollectionCertification = certificationRepository.findById(certificate.getId());
        if (inCollectionCertification != null) {
            inCollectionCertification.setSkillId(certificate.getSkillId());
            inCollectionCertification.setCertificationName(certificate.getCertificationName());
            inCollectionCertification.setInstitution(certificate.getInstitution());
            certificationRepository.save(inCollectionCertification);
        }

    }


    // Add new Certification, if not in list
    public void postNewCertification(CertificationDomain certification) throws Exception {
        Certification certification1 = new Certification();
        certification1.setSkillId(certification.getSkillId());
        certification1.setCertificationName(certification.getCertificationName());
        certification1.setInstitution(certification.getInstitution());
        int flag = 0;
        List<Certification> certifications = this.certificationRepository.findAll();

        for (Certification iterable : certifications) {
            if (iterable.getInstitution().equalsIgnoreCase(certification1.getInstitution())
                    && iterable.getCertificationName().equalsIgnoreCase(certification1.getCertificationName())) {
                flag = 1;
            }
        }

        if (flag == 0) {
            //Generating random Id for Certification Model
            Certification temp = new Certification();
            String certification_id = "";
            Random rand = new Random();

            //Exception Handling: NullPointerException
            try {
                do {
                    certification_id = Integer.toString(rand.nextInt(1000000));
                    temp = this.certificationRepository.findOne(certification_id);
                }
                while (temp.getId() == certification_id);
            } catch (NullPointerException e) {
                System.out.println(e);
            }

            certification1.setId(certification_id);

            //To save a new entry
            System.out.println("saved");
            this.certificationRepository.save(certification1);
        }
    }

    /**
     * Method used to create the objects of ConfigurationStrings.SUBSKILL type and save it in the collection
     *
     * @param br is of buffered reader type received from the multipart form
     */
    public boolean skilluploadcsv(BufferedReader br) {
        boolean toReturn = true;
        int i = (int) subSkillRepository.count() + 1;
        System.out.println("inside the admin service count is" + i);
        String line = "";
        SubSkill checker = null;
        List<SubSkill> subskills = new LinkedList<>();
        try {
            while ((line = br.readLine()) != null) {

                String[] skillstring = line.split(",");
                checker = null;
                checker = subSkillRepository.findBySubSkill(skillstring[3]);

                if (checker == null) {
                    SubSkill subskill = new SubSkill();
                    subskill.setId("" + i);
                    subskill.setPractice(skillstring[0]);
                    subskill.setSkillGroup(skillstring[1]);
                    subskill.setSkill(skillstring[2]);
                    subskill.setSubSkill(skillstring[3]);
                    StringBuilder temp = new StringBuilder("");
                    for (int j = 4; j < skillstring.length; j++) {
                        if (skillstring[j].charAt(0) == '"')
                            skillstring[j] = skillstring[j].substring(1);
                        if (skillstring[j].charAt((skillstring[j].length()) - 1) == '"')
                            skillstring[j] = skillstring[j].substring(0, (skillstring[j].length()) - 1);
                        temp = temp.append(new StringBuilder(skillstring[j]));
                    }
                    subskill.setSubSkillDesc(temp.toString());

                    subskills.add(subskill);
                    i++;
                }
            }

        } catch (Exception e) {
            toReturn = false;
            logger.error(e.getMessage());

        }

        subSkillRepository.save(subskills);
        this.reloadCache();
        this.reloadSkillCache();

        return toReturn;

    }

    /**
     * method used to save the certificates from the mulitpart received.
     *
     * @param br
     * @return
     */
    public boolean certificateuploadcsv(BufferedReader br) {
        boolean toReturn = true;
        int i = (int) certificationRepository.count() + 1;
        //System.out.println("inside the admin service count is" + i);
        String line = "";
        Certification checker = null;
        List<Certification> certifications = new LinkedList<>();
        try {
            while ((line = br.readLine()) != null) {

                String[] certificateString = line.split(",");
                checker = null;
                checker = certificationRepository.findBycertificationName(certificateString[1]);

                if (checker == null) {
                    Certification certification = new Certification();
                    certification.setId("" + i);
                    certification.setSkillId(certificateString[0]);
                    certification.setCertificationName(certificateString[1]);
                    certification.setInstitution(certificateString[2]);
                    certifications.add(certification);
                    i++;
                }
            }

        } catch (Exception e) {
            toReturn = false;
            logger.error(e.getMessage());

        }

        certificationRepository.save(certifications);


        return toReturn;

    }
}

