package com.teksystems.skillportal.controller;

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
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (request.getHeader(ConfigurationStrings.AUTHORIZATION)!=null) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
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
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return employeeIdPresent;
    }

     /*
     * Controller to fetch all the admin Roles of the app to provide the auth in the frontend
     */
    @GetMapping("/adminRoles")
    public List<AdminRoles> getAllAdminRoles(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("adminRoles API Called");
        String employeeId ;
        List<AdminRoles> toReturn = null;
        if(checkEmployeeId(request,response)){
            toReturn = roleService.getAdminRoles();
        }else{
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return toReturn;
    }

    /*
     * Controller for adding new Role.
     */
    @PostMapping("/addAdminRole")
    private void addAdminRole(HttpServletRequest request, @RequestBody AdminRoles role, HttpServletResponse response) throws IOException {
        logger.info("addadminRoles API Called");
        String employeeId;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (request.getHeader(ConfigurationStrings.AUTHORIZATION)!=null) {
                if (tokenValidator.validateAdminRole(request, response)) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    if (employeeId != null) {
                        logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                        roleService.addRoles(role);
                    } else {
                        logger.info(ConfigurationStrings.NOEMAIL);
                    }
                } else {
                    logger.debug(ConfigurationStrings.NOADMIN);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
                }
            } else {
                logger.info(ConfigurationStrings.AUTHORIZATIONHEADER);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteRole")
    public void deleteRole(HttpServletRequest request, @RequestParam String id, HttpServletResponse response) throws IOException {
        logger.info("deleteRoles API Called");
        String employeeId;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (request.getHeader(ConfigurationStrings.AUTHORIZATION)!=null) {
                if (tokenValidator.validateAdminRole(request, response)) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    if (employeeId != null) {
                        logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                        roleService.deleteRole(id);
                    } else {
                        logger.info(ConfigurationStrings.NOEMAIL);
                    }
                } else {
                    logger.debug(ConfigurationStrings.NOADMIN);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
                }
            } else {
                logger.info(ConfigurationStrings.AUTHORIZATIONHEADER);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
