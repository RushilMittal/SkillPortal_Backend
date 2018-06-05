package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.SkillReport;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.service.AdminService;
import com.teksystems.skillportal.service.ReportService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    AdminService adminService;

    @GetMapping("/reportskill")
    public List<SubSkillDomain> topNSubSkill(@RequestParam int n, HttpServletRequest request, HttpServletResponse response) {
        List<SubSkillDomain> toReturn = null;
        logger.info("/reportskill API Called");
        String employeeId = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                if (tokenValidator.validateAdminRole(request, response)) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    logger.debug("Paramater received : employeeId " + employeeId);
                    toReturn = reportService.topNSubSkills(n);
                } else {
                    logger.debug("Employee doesn't have Admin Role");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                }
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;
    }

    @GetMapping("/reportskilltrend")
    public List<SubSkillDomain> topNSubSkillinLastXMonths(@RequestParam int n, @RequestParam long x, HttpServletRequest request, HttpServletResponse response) {
        List<SubSkillDomain> toReturn = null;
        logger.info("/reportskilltrend API Called");
        String employeeId = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                if (tokenValidator.validateAdminRole(request, response)) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    logger.debug("Paramater received : employeeId " + employeeId);
                    toReturn = reportService.topNSubSkillsinLastXMonths(n, x);
                } else {
                    logger.debug("Employee doesn't have Admin Role");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                }
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;
    }

    @GetMapping("/reportskillofemployee")
    public List<EmployeeSkillDomain> topNSubSkillinLastXMonths(@RequestParam String empId, HttpServletRequest request, HttpServletResponse response) {
        List<EmployeeSkillDomain> toReturn = null;
        logger.info("/reportskillofemployee API Called");
        String employeeId = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                if (tokenValidator.validateAdminRole(request, response)) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    logger.debug("Paramater received : employeeId " + employeeId);
                    toReturn = reportService.skillsOfEmployee(empId);
                } else {
                    logger.debug("Employee doesn't have Admin Role");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                }
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;

    }

    @GetMapping("/reportemployeeupdation")
    public List<SkillReport> UpdatedByEmpSubSkillinLastXMonths(@RequestParam long from, @RequestParam long to, HttpServletRequest request, HttpServletResponse response) {
        List<SkillReport> toReturn = null;
        logger.info("/reportskillofemployee API Called");
        String employeeId = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                if (tokenValidator.validateAdminRole(request, response)) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    logger.debug("Paramater received : employeeId " + employeeId);
                    toReturn = reportService.EmployeesWhoUpdatedSubSkillsinLastXMonths(from, to);
                } else {
                    logger.debug("Employee doesn't have Admin Role");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                }
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;
    }

    @GetMapping("/reportcertificateexpiry")
    public List<EmployeeCertificationDomain> certificatesExipringInNextNmonths(@RequestParam long from, @RequestParam long to, HttpServletRequest request, HttpServletResponse response) {
        List<EmployeeCertificationDomain> toReturn = null;
        logger.info("/reportskillofemployee API Called");
        String employeeId = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                if (tokenValidator.validateAdminRole(request, response)) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    logger.debug("Paramater received : employeeId " + employeeId);
                    toReturn = reportService.CertificatesExipringInNextNmonths(from, to);
                } else {
                    logger.debug("Employee doesn't have Admin Role");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                }
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;

    }

    @GetMapping("/getemployees")
    public List<String> EmployeesWithASkill(HttpServletRequest request, HttpServletResponse response) {
        List<String> toReturn = null;
        logger.info("/reportskillofemployee API Called");
        String employeeId = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                if (tokenValidator.validateAdminRole(request, response)) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    logger.debug("Paramater received : employeeId " + employeeId);
                    toReturn = reportService.EmployeesWithASkill();
                } else {
                    logger.debug("Employee doesn't have Admin Role");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                }
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;

    }

}
