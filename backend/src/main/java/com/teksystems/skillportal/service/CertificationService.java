package com.teksystems.skillportal.service;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.model.Certification;
import com.teksystems.skillportal.repository.CertificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    public CertificationService(CertificationRepository certificationRepository) {
        this.certificationRepository = certificationRepository;
    }

    //Read all certifications of an employee
    public List<CertificationDomain> getAllCertifications()
    {
        List<Certification> certifications = this.certificationRepository.findAll();
        List<CertificationDomain> certificationDomains = new LinkedList<>();

        //transforming certification to certification_domain type
        for(Certification iterable: certifications)
        {
            CertificationDomain temp = new CertificationDomain(iterable.getId(),iterable.getSkillId(), iterable.getCertificationName(),
                                        iterable.getInstitution());
            certificationDomains.add(temp);
        }
        return certificationDomains;
    }
    
    public CertificationDomain getCertificationByName(String certificationName)
    {	Certification certification=this.certificationRepository.findBycertificationName(certificationName);
    	CertificationDomain certificationDomains;

    //transforming certification to certification_domain type
        certificationDomains = new CertificationDomain(certification.getId(),certification.getSkillId(), certification.getCertificationName(),
        		certification.getInstitution());
        
    
    return certificationDomains;
    	
    }


    // Add new Certification, if not in list
    public void postNewCertification(CertificationDomain certification)
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

    public List<CertificationDomain> searchCertItems(String searchTerm){

        List<CertificationDomain> certDomain = getAllCertifications();
        List<CertificationDomain> certSearch = new LinkedList<>();

        for(CertificationDomain iterable: certDomain)
        {
            Pattern p = Pattern.compile(searchTerm);
            Matcher m1 = p.matcher((iterable.getCertificationName()).toLowerCase());
            Matcher m2 = p.matcher((iterable.getInstitution()).toLowerCase());
            if(m1.find()||m2.find())
            {
                certSearch.add(iterable);
            }
        }
        return certSearch;
    }
}
