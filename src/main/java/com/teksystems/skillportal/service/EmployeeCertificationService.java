package com.teksystems.skillportal.service;

import com.mongodb.MongoException;
import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationPlaceholderDomain;
import com.teksystems.skillportal.model.Certification;
import com.teksystems.skillportal.model.EmployeeCertification;
import com.teksystems.skillportal.repository.CertificationRepository;
import com.teksystems.skillportal.repository.EmployeeCertificationRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeCertificationService {

    @Autowired
    EmployeeCertificationRepository employeeCertificationRepository;
    private static Logger logger = Logger.getLogger(EmployeeCertificationService.class);

    @Autowired
    CertificationRepository certificationRepository;

    public List<EmployeeCertificationDomain> getEmployeeCertificationByEmployeeId(String empId) throws MongoException {


        List<EmployeeCertification> employeeCertification = employeeCertificationRepository.findByempId(empId);

        List<EmployeeCertificationDomain> employeeCertificationDomains = new LinkedList<>();


        for (EmployeeCertification iterable : employeeCertification) {

            Certification certification = certificationRepository.findById(iterable.getCertificationId());
            CertificationDomain certificationDomain = new CertificationDomain(certification.getId(), certification.getSkillId(), certification.getCertificationName(), certification.getInstitution());
            EmployeeCertificationDomain temp = new EmployeeCertificationDomain(iterable.getEmployeeId(), certificationDomain, iterable.getCertificationDate(), iterable.getCertificationValidityDate(), iterable.getCertificationNumber(), iterable.getCertificationUrl());
            employeeCertificationDomains.add(temp);
        }
        return employeeCertificationDomains;
    }


    public void addNew(String empId, String certificationId, Date certificationDate, Date certificationValidityDate,
                       int certificatioNumber, String certificationUrl) throws MongoException {
        //created a new object to insert into database
        EmployeeCertification newCertification = new EmployeeCertification(empId, certificationId, certificationDate, certificationValidityDate,
                certificatioNumber, certificationUrl);
        //saving the object into employee skill database
        employeeCertificationRepository.save(newCertification);

    }

    public void addNewCertificate(EmployeeCertificationDomain empDom) {
        EmployeeCertification newCert = new EmployeeCertification(empDom.getempId(), empDom.getCertificationId().getId(), empDom.getCertificationDate(), empDom.getCertificationValidityDate(), empDom.getCertificationNumber(), empDom.getCertificationUrl());
        employeeCertificationRepository.save(newCert);
    }

    public List<EmployeeCertificationPlaceholderDomain> getEmployeeCertificationPlaceholderById(String employeeId) {

        String[] names = new String[2];
        names[0] = names[1] = null;
        String[] years = new String[2];
        years[0] = years[1] = null;

        List<EmployeeCertificationPlaceholderDomain> employeeCertificationPlaceholderDomainsList = new ArrayList<>();

            List<EmployeeCertificationDomain> employeeCertificationDomain = getEmployeeCertificationByEmployeeId(employeeId);


            if (employeeCertificationDomain.size() >= 2) {

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(employeeCertificationDomain.get(0).getCertificationDate());
                EmployeeCertificationPlaceholderDomain temp1 = new EmployeeCertificationPlaceholderDomain(employeeCertificationDomain.get(0).getCertificationId().getCertificationName(), String.valueOf(calendar.get(Calendar.YEAR)));
                calendar.setTime(employeeCertificationDomain.get(1).getCertificationDate());
                EmployeeCertificationPlaceholderDomain temp2 = new EmployeeCertificationPlaceholderDomain(employeeCertificationDomain.get(1).getCertificationId().getCertificationName(), String.valueOf(calendar.get(Calendar.YEAR)));
                employeeCertificationPlaceholderDomainsList.add(temp1);
                employeeCertificationPlaceholderDomainsList.add(temp2);
            } else if (employeeCertificationDomain.size() == 1) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(employeeCertificationDomain.get(0).getCertificationDate());
                EmployeeCertificationPlaceholderDomain temp1 = new EmployeeCertificationPlaceholderDomain(employeeCertificationDomain.get(0).getCertificationId().getCertificationName(), String.valueOf(calendar.get(Calendar.YEAR)));
                employeeCertificationPlaceholderDomainsList.add(temp1);


            }


        return employeeCertificationPlaceholderDomainsList;
    }

    public Date dateExtractor(String dateReceived, Calendar calendar) {
        Date toReturn = null;

        if (!dateReceived.equals("null")) {
            calendar.set(Calendar.YEAR, Integer.parseInt(dateReceived.split("-")[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(dateReceived.split("-")[1]));
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateReceived.split("-")[2]));

            int month = calendar.get(Calendar.MONTH) - 1;
            calendar.set(Calendar.MONTH, month);
            toReturn = calendar.getTime();

        }

        return toReturn;
    }

}
