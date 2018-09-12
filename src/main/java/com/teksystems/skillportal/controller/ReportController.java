package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.SkillReport;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.service.ReportService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/report", method = {RequestMethod.GET, RequestMethod.POST})
@CrossOrigin("*")
public class ReportController {
    private static Logger logger = Logger.getLogger(ReportController.class);
    @Autowired
    ReportService reportService;
    @Autowired
    TokenValidationService tokenValidator;


    public boolean checkValidEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean validEmployee = false;
        String employeeId;

        logger.info(ConfigurationStrings.FETCHING);
        if (!(request.getHeader(ConfigurationStrings.AUTHORIZATION) == null)) {
            if (tokenValidator.validateAdminRole(request, response)) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                validEmployee = true;
            } else {
                logger.debug(ConfigurationStrings.NOADMIN);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } else {
            logger.info(ConfigurationStrings.NOTFOUND);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
        }

        return validEmployee;
    }

    @GetMapping("/reportskill")
    public List<SubSkillDomain> topNSubSkill(@RequestParam int n, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<SubSkillDomain> toReturn = null;
        logger.info("/reportskill API Called");
        try {

            if (checkValidEmployee(request, response)) {
                toReturn = reportService.topNSubSkills(n);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }
        return toReturn;
    }

    @GetMapping("/reportskilltrend")
    public List<SubSkillDomain> topNSubSkillinLastXMonths(@RequestParam int n, @RequestParam long x, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<SubSkillDomain> toReturn = null;
        logger.info("/reportskilltrend API Called");

        try {

            if (checkValidEmployee(request, response)) {
                toReturn = reportService.topNSubSkillsinLastXMonths(n, x);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }

        return toReturn;
    }

    @GetMapping("/reportskillofemployee")
    public List<EmployeeSkillDomain> topNSubSkillinLastXMonths(@RequestParam String employeeId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<EmployeeSkillDomain> toReturn = null;
        logger.info("/reportskillofemployee API Called");

        try {

            if (checkValidEmployee(request, response)) {

                toReturn = reportService.skillsOfEmployee(employeeId);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }
        return toReturn;

    }

    @GetMapping("/reportemployeeupdation")
    public List<SkillReport> UpdatedByEmpSubSkillinLastXMonths(@RequestParam long from, @RequestParam long to, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<SkillReport> toReturn = null;
        logger.info("/reportemployeeupdation API Called");

        try {

            if (checkValidEmployee(request, response)) {

                toReturn = reportService.employeesWhoUpdatedSubSkillsInLastXMonths(from, to);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }

        return toReturn;
    }

    @GetMapping("/reportcertificateexpiry")
    public List<EmployeeCertificationDomain> certificatesExipringInNextNmonths(@RequestParam long from, @RequestParam long to, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<EmployeeCertificationDomain> toReturn = null;
        logger.info("/reportcertificationexpiry API Called");


        try {

            if (checkValidEmployee(request, response)) {

                toReturn = reportService.certificatesExpiringInNextNmonths(from, to);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }

        return toReturn;

    }

    @GetMapping("/getemployees")
    public List<String> EmployeesWithASkill(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> toReturn = null;
        logger.info("/getemployees API Called");

        try {

            if (checkValidEmployee(request, response)) {

                toReturn = reportService.employeesWithASkill();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }

        return toReturn;

    }

}
