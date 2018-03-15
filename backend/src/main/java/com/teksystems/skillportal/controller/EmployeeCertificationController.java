package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.model.Certification;
import com.teksystems.skillportal.model.EmployeeCertification;
import com.teksystems.skillportal.service.EmployeeCertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.teksystems.skillportal.service.CertificationService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/certifications",method=RequestMethod.GET)
@CrossOrigin("*")
public class EmployeeCertificationController {

    @Autowired
    EmployeeCertificationService employeeCertificationService;
    
    @Autowired
    CertificationService certificationService;

    @GetMapping("/getcertifications")
    public List<EmployeeCertificationDomain> getById(@RequestParam String empId) {
        return employeeCertificationService.getEmployeeCertificationByEmployeeId(empId);
    }
    
    @PostMapping("/addCertification")
	 public void addCertification(@RequestParam String empId,@RequestParam String certificationId,@RequestParam Date certificationDate,@RequestParam  Date certificationValidityDate,
			 @RequestParam 	int certificatioNumber,@RequestParam  String certificationUrl) {
	     employeeCertificationService.addNew(empId,certificationId, certificationDate, certificationValidityDate,
    		 certificatioNumber, certificationUrl);
	 }
    @PostMapping("/addcertificate")
    public void addNewCertificate (@RequestBody EmployeeCertificationDomain employeeCertificationDomain){
        employeeCertificationService.addNewCertificate(employeeCertificationDomain);
    }
	
    @GetMapping("/gettoptwocertifications")
    public String[] getTopTwoEmployeeCertificationById(@RequestParam String employeeId) {
        return employeeCertificationService.getTopTwoEmployeeCertificationById(employeeId);
    }
    
    @GetMapping("/gettoptwocertificationsyear")
    public String[] getTopTwoEmployeeCertificationYearById(@RequestParam String employeeId) {
        return employeeCertificationService.getTopTwoEmployeeCertificationYearById(employeeId);
    }
}
	

