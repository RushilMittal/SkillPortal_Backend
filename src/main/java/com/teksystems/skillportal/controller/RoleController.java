package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.model.AdminRoles;
import com.teksystems.skillportal.service.RoleService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /*
     * Controller to fetch all the admin Roles of the app to provide the auth in the frontend
     */
    @GetMapping("/adminRoles")
    public List<AdminRoles> getAllAdminRoles(HttpServletRequest request) {
        logger.info("adminRoles API Called");
        String employeeId = null;
        List<AdminRoles> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                if (employeeId != null) {
                    logger.debug("Paramater received : employeeId " + employeeId);
                    toReturn = roleService.getAdminRoles();
                } else {
                    logger.info("Employee Email id not Present");
                }

            } else {
                logger.info("Authorization Header not found");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;
    }

    /*
     * Controller for adding new Role.
     */
    @PostMapping("/addAdminRole")
    private void addAdminRole(HttpServletRequest request, @RequestBody AdminRoles role, HttpServletResponse response) {
        logger.info("adminRoles API Called");
        String employeeId = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                if (tokenValidator.validateAdminRole(request, response)) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    if (employeeId != null) {
                        logger.debug("Paramater received : employeeId " + employeeId);
                        roleService.addRoles(role);
                    } else {
                        logger.info("Employee Email id not Present");
                    }
                } else {
                    logger.debug("Employee doesn't have Admin Role");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                }
            } else {
                logger.info("Authorization Header not found");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
    }

    @DeleteMapping("/deleteRole")
    public void deleteRole(HttpServletRequest request, @RequestParam String id, HttpServletResponse response) {
        logger.info("adminRoles API Called");
        String employeeId = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                if (tokenValidator.validateAdminRole(request, response)) {
                    employeeId = tokenValidator.ExtractEmployeeId(request);
                    if (employeeId != null) {
                        logger.debug("Paramater received : employeeId " + employeeId);
                        roleService.deleteRole(id);
                    } else {
                        logger.info("Employee Email id not Present");
                    }
                } else {
                    logger.debug("Employee doesn't have Admin Role");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                }
            } else {
                logger.info("Authorization Header not found");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
    }

}
