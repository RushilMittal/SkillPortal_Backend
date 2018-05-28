package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.AdminService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value="/admin",method= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@CrossOrigin("*")
public class AdminController {
    private static Logger logger = Logger.getLogger(AdminController.class);
    @Autowired
    private TokenValidationService tokenValidator;
    @Autowired
    private AdminService adminService;

    /*
     * Method returning the List of the Skills in Skill Collection
     * Role Method need to verify Role Roles, calls for admin rest API should have Access Token.
     * And verify the Role role by calling the method
     */
    @GetMapping("/getAllAdminSkills")
    public List<SubSkill> getAllAdminSkill(HttpServletRequest request, HttpServletResponse response){
        logger.info("getskillgroup API Called");
        String employeeId = null;
        List<SubSkill> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
//                if(tokenValidator.validateAdminRole(request,response)) {
                if(true){
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    logger.debug("Paramater received : employeeId " + employeeId);
                    toReturn = adminService.getAllAdminSkills();
                }else{
                    logger.debug("Not a Valid Role User");
                }
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;
    }

    /*
     * Conroller for updadting ths Skill called by admin.
     */
    @PutMapping("/updateNewSkill")
    void updateSkill(HttpServletRequest request, @RequestBody SubSkill subSkillReceived){
        System.out.println(subSkillReceived.toString());
        logger.info("/updateNewSkill API called");
        String employeeId =  null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
//                if(tokenValidator.validateAdminRole(request,response)) {
                if(true) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    logger.debug("Paramater received : employeeId " + employeeId);
                    adminService.updateNewSkill(subSkillReceived);
                }
                else{
                    logger.debug("Not a Valid Role User");
                }
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
    }

    /*
     * Controller for adding the new Skill in the Database
     */
    @PostMapping("/addNewSkill")
    public void addNewSkill(HttpServletRequest request,@RequestBody SubSkill subSkillReceived){
        logger.info("/addNewSkill API called");
        String employeeId =  null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
//                if(tokenValidator.validateAdminRole(request,response)) {
                if(true) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    logger.debug("Paramater received : employeeId " + employeeId);
                    adminService.addNewSkill(subSkillReceived);
                }else{
                    logger.debug("Not a Valid Role User");
                }
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
        logger.info("/add_new Certificate API called");
        String employeeId =  null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                adminService.postNewCertification(certification);

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }

    }

    /*
    * Controller for Updating existing certificate
    */
    @PutMapping("/updateCertificate")
    void updateCertificate(HttpServletRequest request,@RequestBody CertificationDomain certification){
        logger.info("/add_new Certificate API called");
        String employeeId =  null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                adminService.updateCertificate(certification);

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
    }

}
