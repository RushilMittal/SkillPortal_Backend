package com.teksystems.skillportal.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.model.Certification;
import com.teksystems.skillportal.model.EmployeeCertification;
import com.teksystems.skillportal.model.EmployeeSkill;
import com.teksystems.skillportal.repository.CertificationRepository;
import com.teksystems.skillportal.repository.EmployeeCertificationRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

@Service
public class EmployeeCertificationService {

    @Autowired
    EmployeeCertificationRepository employeeCertificationRepository;

    @Autowired
    CertificationRepository certificationRepository;

    public List<EmployeeCertificationDomain> getEmployeeCertificationByEmployeeId(String empId) {


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
    
    
    public void addNew(String empId,String certificationId,Date certificationDate, Date certificationValidityDate,
    		int certificatioNumber, String certificationUrl)
    {
 	//created a new object to insert into database
 	EmployeeCertification newCertification = new EmployeeCertification(empId,certificationId, certificationDate, certificationValidityDate,
    		 certificatioNumber, certificationUrl);
 	//saving the object into employee skill database
 	employeeCertificationRepository.save(newCertification);
 	
    }
    public void addNewCertificate(EmployeeCertificationDomain empDom) {
    	EmployeeCertification newCert=new EmployeeCertification(empDom.getempId(),empDom.getCertificationIdId(),empDom.getCertificationDate(),empDom.getCertificationValidityDate(),empDom.getCertificationNumber(),empDom.getCertificationUrl());
    	employeeCertificationRepository.save(newCert);
    }
    
    public String[] getTopTwoEmployeeCertificationById(String employeeId){
        String[] names=new String[2];
        names[0] =names[1] =null;
        try {
            List<EmployeeCertificationDomain> employeeCertificationDomain = getEmployeeCertificationByEmployeeId(employeeId);

            System.out.println("Printing  Second Function " + employeeCertificationDomain.size());
            for (EmployeeCertificationDomain b : employeeCertificationDomain) {
                System.out.println(b.toString());

            }
            if (employeeCertificationDomain.size() >= 2) {
                String certification1 = employeeCertificationDomain.get(0).getCertificationId().getCertificationName();
                String certification2 = employeeCertificationDomain.get(1).getCertificationId().getCertificationName();
                names[0] = certification1;
                names[1] = certification2;

            } else if (employeeCertificationDomain.size() == 1) {
                String certification1 = employeeCertificationDomain.get(0).getCertificationId().getCertificationName();
                names[0] = certification1;
                names[1] = null;

            } else {
                System.out.println("No data Present");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(names[0] + " : " + names[1]);
        return names;
    }
    
    public String[] getTopTwoEmployeeCertificationYearById(String employeeId){
        String[] years=new String[2];
        years[0] =years[1] =null;
        Date[] dates = new Date[2];
        try {
            List<EmployeeCertificationDomain> employeeCertificationDomain = getEmployeeCertificationByEmployeeId(employeeId);

            if (employeeCertificationDomain.size() >= 2) {
                dates[0] = employeeCertificationDomain.get(0).getCertificationDate();
                dates[1] = employeeCertificationDomain.get(1).getCertificationDate();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(dates[0]);
                years[0] = String.valueOf(calendar.get(Calendar.YEAR));
                calendar.setTime(dates[1]);
                years[1] = String.valueOf(calendar.get(Calendar.YEAR));

            } else if (employeeCertificationDomain.size() == 1) {
                dates[0] = employeeCertificationDomain.get(0).getCertificationDate();
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(dates[0]);
                years[0] = String.valueOf(calendar.get(Calendar.YEAR));
                years[1] = null;

            } else {
                System.out.println("No data Present");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(dates[0] + " : " + dates[1]);
        return years;
    }


}
