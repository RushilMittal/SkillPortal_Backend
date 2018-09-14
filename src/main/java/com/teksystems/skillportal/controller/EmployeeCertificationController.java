//comments,logger and validation added ; sahib 14-04-2018
package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationPlaceholderDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.service.CertificationService;
import com.teksystems.skillportal.service.EmployeeCertificationService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/certifications", method = {RequestMethod.GET, RequestMethod.POST})
@CrossOrigin()
public class EmployeeCertificationController {
    private static Logger logger = Logger.getLogger(EmployeeCertificationController.class);
    @Autowired
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
    public List<EmployeeCertificationDomain> getById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String employeeId;
        List<EmployeeCertificationDomain> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (request.getHeader(ConfigurationStrings.AUTHORIZATION) != null) {
                employeeId = tokenValidator.extractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                logger.info("Trying to fetch employee's added Certificate");
                toReturn = employeeCertificationService.getEmployeeCertificationByEmployeeId(employeeId);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
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
    public void addCertification(HttpServletRequest request, @RequestParam String certificationId,
                                 @RequestParam String certificationDateString,
                                 @RequestParam String certificationValidityDateString,
                                 @RequestParam int certificationNumber, @RequestParam String certificationUrl,
                                 HttpServletResponse response
    ) throws IOException {

        String employeeId;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (request.getHeader(ConfigurationStrings.AUTHORIZATION) != null) {
                employeeId = tokenValidator.extractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                logger.info("Trying to Save the Certificate");
                Calendar calendar = Calendar.getInstance();
                Date certificationDate = employeeCertificationService.dateExtractor(certificationDateString, calendar);

                Date certificationValidityDate = employeeCertificationService.dateExtractor(certificationValidityDateString, calendar);

                employeeCertificationService.addNew(employeeId, certificationId, certificationDate, certificationValidityDate,
                        certificationNumber, certificationUrl);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }
    }


    /*
     * Controller for Adding the Certificate to Employee Profile.
     * UI:- Called on when new Certificate is added by the Employee
     * param:- HttpServletRequest(for validation of the EmployeeId)
     * param:- EmployeeCertificationDomain(Contains info about the certificate to Add)
     * Validation and fetching of the EmployeeId done:- 14-04-2018
     * ( SOLVED)Function not in use as, giving error due to inteceptor as its passed with type:- "text/plain",
     * and changing to "application/json" causing authentication fail of OPTIONS call and giving 415 ERROR
     */
    @PostMapping("/addcertificate")
    public void addNewCertificate(HttpServletRequest request,
                                  @RequestBody EmployeeCertificationDomain employeeCertificationDomain, HttpServletResponse response
    ) throws IOException {
        logger.info("/addcertificate API called");

        String employeeId;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if ((request.getHeader(ConfigurationStrings.AUTHORIZATION) != null)) {
                employeeId = tokenValidator.extractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                employeeCertificationDomain.setEmpId(employeeId);
                logger.info("Trying to save the Certificate");
                employeeCertificationService.addNewCertificate(employeeCertificationDomain);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
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
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("/getcertificationplaceholder API called");
        String employeeId;
        List<EmployeeCertificationPlaceholderDomain> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (request.getHeader(ConfigurationStrings.AUTHORIZATION) != null) {
                employeeId = tokenValidator.extractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                logger.info("Trying to fetch Certification Placeholder");
                toReturn = employeeCertificationService.getEmployeeCertificationPlaceholderById(employeeId);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }
        return toReturn;
    }
}
	

