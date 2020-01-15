package com.cdac.skillportal.service;

import com.cdac.skillportal.domain.CertificationDomain;
import com.cdac.skillportal.model.Certification;
import com.mongodb.MongoException;
import com.cdac.skillportal.repository.CertificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CertificationService {

    @Autowired
    private CertificationRepository certificationRepository;


    //Read all certifications of an employee
    public List<CertificationDomain> getAllCertifications() throws MongoException {
        List<Certification> certifications = this.certificationRepository.findAll();
        List<CertificationDomain> certificationDomains = new LinkedList<>();

        //transforming certification to certification_domain type
        for (Certification iterable : certifications) {
            CertificationDomain temp = new CertificationDomain(iterable.getId(), iterable.getSkillId(), iterable.getCertificationName(),
                    iterable.getInstitution());
            certificationDomains.add(temp);
        }
        return certificationDomains;
    }

    public CertificationDomain getCertificationByName(String certificationName) throws MongoException {
        Certification certification = this.certificationRepository.findBycertificationName(certificationName);
        CertificationDomain certificationDomains;

        //transforming certification to certification_domain type
        certificationDomains = new CertificationDomain(certification.getId(), certification.getSkillId(), certification.getCertificationName(),
                certification.getInstitution());


        return certificationDomains;

    }


    public List<CertificationDomain> searchCertItems(String searchTerm) {

        List<CertificationDomain> certDomain = getAllCertifications();
        List<CertificationDomain> certSearch = new LinkedList<>();

        for (CertificationDomain iterable : certDomain) {
            Pattern p = Pattern.compile(searchTerm.toLowerCase());
            Matcher m1 = p.matcher((iterable.getCertificationName()).toLowerCase());
            Matcher m2 = p.matcher((iterable.getInstitution()).toLowerCase());
            if (m1.find() || m2.find()) {
                certSearch.add(iterable);
            }
        }
        return certSearch;
    }
}
