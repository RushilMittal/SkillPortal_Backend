package com.teksystems.skillportal.controller;

import com.mongodb.MongoException;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.model.AdminRoles;
import com.teksystems.skillportal.service.RoleService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/role", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@CrossOrigin("*")
public class RoleController {
    private static Logger logger = Logger.getLogger(RoleController.class);
    @Autowired
    private TokenValidationService tokenValidator;
    @Autowired
    RoleService roleService;

    public boolean checkEmployeeId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean employeeIdPresent = false;
        String employeeId;

        logger.info(ConfigurationStrings.FETCHING);
        if (request.getHeader(ConfigurationStrings.AUTHORIZATION) != null) {
            employeeId = tokenValidator.extractEmployeeId(request);
            if (employeeId != null) {
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                employeeIdPresent = true;
            } else {
                logger.info(ConfigurationStrings.NOEMAIL);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }

        } else {
            logger.info(ConfigurationStrings.AUTHORIZATIONHEADER);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
        }

        return employeeIdPresent;
    }

    /*
     * Controller to fetch all the admin Roles of the app to provide the auth in the frontend
     */
    @GetMapping("/adminRoles")
    public List<AdminRoles> getAllAdminRoles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("adminRoles API Called");

        List<AdminRoles> toReturn = null;
        try {
            if (checkEmployeeId(request, response)) {
                toReturn = roleService.getAdminRoles();
            }

        }catch (MongoException e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }

        return toReturn;
    }

    /*
     * Controller for adding new Role.
     */
    @PostMapping("/addAdminRole")
    private void addAdminRole(HttpServletRequest request, @RequestBody AdminRoles role, HttpServletResponse response) throws IOException {
        logger.info("addadminRoles API Called");
        try {
            if (checkEmployeeId(request, response)) {
                roleService.addRoles(role);
            }
        } catch (MongoException e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }

    }

    @DeleteMapping("/deleteRole")
    public void deleteRole(HttpServletRequest request, @RequestParam String id, HttpServletResponse response) throws IOException {
        logger.info("deleteRoles API Called");
        try {
            if (checkEmployeeId(request, response)) {
                roleService.deleteRole(id);
            }
        }catch (MongoException e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }

    }

}
