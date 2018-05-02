// Comments Added And Validation added on 14-04-2018- Sahib

package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.model.Certification;
import com.teksystems.skillportal.service.CertificationService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.http.HTTPBinding;
import java.util.List;

@RestController
@RequestMapping("/certifications")
@CrossOrigin()
public class CertificationController {
    private static Logger logger = Logger.getLogger(CertificationController.class);

    private CertificationService certificationService;
    private TokenValidationService tokenValidator;

    @Autowired
    public CertificationController(CertificationService certificationService) {

        this.certificationService = certificationService;
    }

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
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                logger.info("Trying to fetch all the Certificates");
                certifications = this.certificationService.getAllCertifications();
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
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
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                CertificationDomain certification=new CertificationDomain(id,skillId,certificationName,institution);
                certificationService.postNewCertification(certification);

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
    }

    /*
    * Controller for Adding New Certification(For adding in the "Available Certification")
    * UI:- For adding the certification in the list "Available Certification List".
    * Param:- HttpServletRequest(for verifying the Authorization token),certificationdomain (contains info about the new certification )
    * EmployeeID validation done :- 14-04-2018
    */
    @PostMapping("/add_new")
    void postNewUniqueEntry(HttpServletRequest request,@RequestBody CertificationDomain certification)
    {
        logger.info("/add_new API called");
        String employeeId =  null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                this.certificationService.postNewCertification(certification);

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
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
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                CertificationDomain certification = new CertificationDomain(skillId,certificationName,institution);
                this.certificationService.postNewCertification(certification);

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
    }

}
