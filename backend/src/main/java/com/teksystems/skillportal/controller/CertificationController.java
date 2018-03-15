package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.model.Certification;
import com.teksystems.skillportal.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certifications")
@CrossOrigin("*")
public class CertificationController {

    private CertificationService certificationService;

    @Autowired
    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @GetMapping("/all")
    List<CertificationDomain> getAvailableCertifications()
    {
        List<CertificationDomain> certifications = this.certificationService.getAllCertifications();
        return certifications;
    }
    @PostMapping("/addnewCert")
    void postNewCert(@RequestParam String id,@RequestParam String certificationName,@RequestParam String institution,@RequestParam String skillId)
    {	CertificationDomain certification=new CertificationDomain(id,skillId,certificationName,institution);
        this.certificationService.postNewCertification(certification);
    }
    
    @PostMapping("/add_new")
    void postNewUniqueEntry(@RequestBody CertificationDomain certification)
    {
        this.certificationService.postNewCertification(certification);
    }
}
