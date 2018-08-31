// Comments Added And Validation added on 14-04-2018- Sahib

package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.service.AdminService;
import com.teksystems.skillportal.service.CertificationService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/certifications")
@CrossOrigin()
public class CertificationController {
    private static Logger logger = Logger.getLogger(CertificationController.class);
    @Autowired
    private CertificationService certificationService;
    @Autowired
    private TokenValidationService tokenValidator;
    @Autowired
    private AdminService adminService;


    /*
    * Controller for fetching all the Certificate Available
    * UI:- Used to dispslay list under "Available Certificates"
    * Param:- HttpServletrequest (For validation of the Authorization token)
    * EmployeeId Validation done :- 14-04-2018
     */
    @GetMapping("/all")
    List<CertificationDomain> getAvailableCertifications(HttpServletRequest request)
    {
        logger.info("/all  API called,under the Certification Controller");
        String employeeId = null;
        List<CertificationDomain> certifications =null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(((HttpServletRequest) request).getHeader(ConfigurationStrings.AUTHORIZATION).toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                logger.info("Trying to fetch all the Certificates");
                certifications = this.certificationService.getAllCertifications();
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
        }
        System.out.println(certifications);
        return certifications;
    }

    /*
    * Controller for adding the New Certificate to the Available Certificates List.
    * param :- HttpSservletRequest (for verifying the Authorization token),
    * param :- id (unique id) , certificationName(Name of the certificate),
    * param :- institution(provider institution of the certificate), skillId(skillid of the skill with which the
    * param :- is associated)
    * EmployeeId validation donee:- 14-04-2018
    */
    @PostMapping("/addnewCert")
    void postNewCert(HttpServletRequest request,@RequestParam String id,@RequestParam String certificationName,
                     @RequestParam String institution,@RequestParam String skillId)
    {
        logger.info("/addnewCert API called");
        String employeeId = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(((HttpServletRequest) request).getHeader(ConfigurationStrings.AUTHORIZATION).toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                CertificationDomain certification=new CertificationDomain(id,skillId,certificationName,institution);
                adminService.postNewCertification(certification);

            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
        }
    }



    @PostMapping("/addnewemployeecertificate")
    void addNewEmployeeCertificate(HttpServletRequest request,
                                   @RequestParam String skillId,
                                   @RequestParam String certificationName,
                                   @RequestParam String institution
                                   ){
        logger.info("/add_new API called");
        String employeeId =  null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(((HttpServletRequest) request).getHeader(ConfigurationStrings.AUTHORIZATION).toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                CertificationDomain certification = new CertificationDomain(skillId,certificationName,institution);
                adminService.postNewCertification(certification);

            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
        }
    }

}
