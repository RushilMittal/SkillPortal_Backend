//comments,logger and validation added ; sahib 14-04-2018
package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationPlaceholderDomain;
import com.teksystems.skillportal.model.Certification;
import com.teksystems.skillportal.model.EmployeeCertification;
import com.teksystems.skillportal.service.EmployeeCertificationService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.teksystems.skillportal.service.CertificationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/certifications",method= {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin()
public class EmployeeCertificationController {
    private static Logger logger = Logger.getLogger(EmployeeCertificationController.class);
    private TokenValidationService tokenValidator;
    @Autowired
    EmployeeCertificationService employeeCertificationService;
    
    @Autowired
    CertificationService certificationService;

    /*
     * Controller for Adding the Certificate to Employee Profile.
     * UI:- Called on when new Certificate is added by the Employee
     * param:- HttpServletRequest(for validation of the EmployeeId)
     * Validation and fetching of the EmployeeId done:- 14-04-2018
     */
    @GetMapping("/getcertifications")
    public List<EmployeeCertificationDomain> getById(HttpServletRequest request) {
        String employeeId = null;
        List<EmployeeCertificationDomain> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                logger.info("Trying to fetch employee's added Certificate");
                toReturn = employeeCertificationService.getEmployeeCertificationByEmployeeId(employeeId);
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }

        return toReturn;
    }

    /*
     * Controller for Adding the Certificate to Employee Profile.
     * UI:- Called on when new Certificate is added by the Employee
     * param:- HttpServletRequest(for validation of the EmployeeId)
     * param:- certificationId(UniqueId)
     * param:- certificationDate(Date of the certification issued)
     * param:- certificationValidityDate(certification Validity Date if any)(Optional i.e. can be null)
     * param:- certificationNumber(Number/id present on the Certification)
     * param:- certificationUrl(URL proof of the certification(Image / issuer record))
     * Validation and fetching of the EmployeeId done:- 14-04-2018
     */
    @PostMapping("/addCertification")
	 public void addCertification(HttpServletRequest request,@RequestParam String certificationId,
                                  @RequestParam String certificationDateString,
                                  @RequestParam  String certificationValidityDateString,
                                  @RequestParam int certificationNumber,@RequestParam  String certificationUrl
    ) {

        String employeeId =null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                logger.info("Trying to Save the Certificate");
                Calendar calendar = Calendar.getInstance();
                Date certificationDate = employeeCertificationService.dateExtractor(certificationDateString, calendar);

                Date certificationValidityDate =employeeCertificationService.dateExtractor(certificationValidityDateString, calendar);
                System.out.println(employeeId + ":" + certificationId + ":" + certificationDate + ":"+ certificationValidityDate
                        + certificationNumber + certificationUrl);
                employeeCertificationService.addNew(employeeId,certificationId, certificationDate, certificationValidityDate,
                        certificationNumber, certificationUrl);
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
	 }


    /*
	 * Controller for Adding the Certificate to Employee Profile.
	 * UI:- Called on when new Certificate is added by the Employee
	 * param:- HttpServletRequest(for validation of the EmployeeId)
	 * param:- EmployeeCertificationDomain(Contains info about the certificate to Add)
	 * Validation and fetching of the EmployeeId done:- 14-04-2018
	 * (TODO SOLVED)Function not in use as, giving error due to inteceptor as its passed with type:- "text/plain",
	 * and changing to "application/json" causing authentication fail of OPTIONS call and giving 415 ERROR
	 */
    @PostMapping("/addcertificate")
    public void addNewCertificate (HttpServletRequest request,
            @RequestBody EmployeeCertificationDomain employeeCertificationDomain
    ){
        logger.info("/addcertificate API called");
        System.out.println("Add certificate called");
        String employeeId =null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                employeeCertificationDomain.setEmpId(employeeId);
                logger.info("Trying to save the Certificate");
                employeeCertificationService.addNewCertificate(employeeCertificationDomain);
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Some Error Occurred: " + e.toString());
        }
    }
	
    /*
    * Controller used for fetching the details for Certification Placeholder
    * UI:- For the Certification placeholder on the Dashboard
    * params:- HttpServletRequest(for validation of EmployeeId)
    * EmplloyeeId fetching and validation done :- 14-04-2018
    */
    @GetMapping("/getcertificationplaceholder")
    public List<EmployeeCertificationPlaceholderDomain> getTopTwoEmployeeCertificationYearById(
            HttpServletRequest request) {
        logger.info("/getcertificationplaceholder API called");
        String employeeId = null;
        List<EmployeeCertificationPlaceholderDomain> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                logger.info("Trying to fetch Certification Placeholder");
                toReturn = employeeCertificationService.getEmployeeCertificationPlaceholderById(employeeId);
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
        return toReturn;
    }
}
	

