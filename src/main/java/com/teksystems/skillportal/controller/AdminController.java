package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.AdminService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.Buffer;
import java.util.List;

@RestController
@RequestMapping(value = "/admin", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@CrossOrigin("*")
public class AdminController {
    private static Logger logger = Logger.getLogger(AdminController.class);
    @Autowired
    TokenValidationService tokenValidator;
    @Autowired
    AdminService adminService;

    public boolean checkAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean adminStatus = false;
        String employeeId;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(request.getHeader(ConfigurationStrings.AUTHORIZATION) ==null)) {
                if (tokenValidator.validateAdminRole(request, response)) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                    adminStatus = true;
                } else {
                    logger.debug(ConfigurationStrings.NOADMIN);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
                }
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } catch (IOException e) {
            logger.info("Some Error Occured: " + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return adminStatus;
    }

    /*
     * Method returning the List of the Skills in Skill Collection
     * Role Method need to verify Role Roles, calls for admin rest API should have Access Token.
     * And verify the Role role by calling the method
     */
    @GetMapping("/getAllAdminSkills")
    public List<SubSkill> getAllAdminSkill(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("getskillgroup API Called");

        List<SubSkill> toReturn = null;
        try {
            if (checkAdmin(request, response)) {
                toReturn = adminService.getAllAdminSkills();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return toReturn;
    }

    /*
     * Conroller for updadting ths Skill called by admin.
     */
    @PutMapping("/updateNewSkill")
    void updateSkill(HttpServletRequest request, @RequestBody SubSkill subSkillReceived, HttpServletResponse response) throws IOException {

        logger.info("/updateNewSkill API called");

        try {
            if (checkAdmin(request, response)) {
                adminService.updateNewSkill(subSkillReceived);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Controller for adding the new Skill in the Database
     */
    @PostMapping("/addNewSkill")
    public void addNewSkill(HttpServletRequest request, @RequestBody SubSkill subSkillReceived, HttpServletResponse response) throws IOException {
        logger.info("/addNewSkill API called");

        try {
            if (checkAdmin(request, response)) {
                adminService.addNewSkill(subSkillReceived);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Controller for Adding New Certification(For adding in the "Available Certification")
     * UI:- For adding the certification in the list "Available Certification List".
     * Param:- HttpServletRequest(for verifying the Authorization token),certificationdomain (contains info about the new certification )
     * EmployeeID validation done :- 14-04-2018
     */
    @PostMapping("/add_new")
    void postNewUniqueEntry(HttpServletRequest request, @RequestBody CertificationDomain certification, HttpServletResponse response) throws IOException {
        logger.info("/add_new Certificate API called");

        try {

            if (checkAdmin(request, response)) {
                adminService.postNewCertification(certification);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    /*
     * Controller for Updating existing certificate
     */
    @PutMapping("/updateCertificate")
    void updateCertificate(HttpServletRequest request, @RequestBody CertificationDomain certification, HttpServletResponse response) throws IOException {
        logger.info("/add_new Certificate API called");

        try {
            if (checkAdmin(request, response)) {
                adminService.updateCertificate(certification);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * Controller for uploading the skill csv
     */
    @PostMapping("/uploadskillcsv")
    void uploadSkillCsv(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] files) throws IOException {
        logger.info("/uploadskillcsv Certificate API called");


        try {
            boolean a = checkAdmin(request, response);
            BufferedReader bf = getFileData(files[0]);
            boolean b = adminService.skilluploadcsv(bf);

            if (a && (!b)) {

                response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Unknow Format");

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("/uploadcertificatecsv")
    void uploadCertificateCsv(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] files) throws IOException {
        logger.info("/uploadskillcsv Certificate API called");

        try {

            if (checkAdmin(request, response) && (!adminService.certificateuploadcsv(getFileData(files[0])))) {

                response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Unknow Format");

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public BufferedReader getFileData(MultipartFile file) {
        InputStream a = null;
        BufferedReader toReturn = null;
        try {
            a = file.getInputStream();
            Reader targetReader = new InputStreamReader(a);
            toReturn = new BufferedReader(targetReader);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return toReturn;
    }


}
