package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.EmployeeTrainingDomain;
import com.teksystems.skillportal.domain.EmployeeTrainingPlaceholderDomain;
import com.teksystems.skillportal.domain.TrainingEventDomain;
import com.teksystems.skillportal.domain.TrainingListEventDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.service.EmployeeTrainingService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value="/training",method= RequestMethod.GET)
@CrossOrigin()
public class EmployeeTrainingController {

    private static Logger logger = Logger.getLogger(EmployeeTrainingController.class);

    @Autowired
    EmployeeTrainingService employeeTrainingService;
    @Autowired
    private TokenValidationService tokenValidator;

    @GetMapping("/gettraining")
    public List<EmployeeTrainingDomain> getByEmpId(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        logger.info("/gettraining API called");
        String empId ;
        List<EmployeeTrainingDomain> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(request.getHeader(ConfigurationStrings.AUTHORIZATION) == null)) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to fetch employee's added Training");
                toReturn = employeeTrainingService.getEmployeeTrainingByEmployeeId(empId);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return toReturn;
    }

    @GetMapping("/gettrainingevent")
    public List<TrainingEventDomain> getEventByEmpId(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        logger.info("/gettrainingevent API called");
        String empId ;
        List<TrainingEventDomain> toReturn = null;

        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(request.getHeader(ConfigurationStrings.AUTHORIZATION) == null)) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to fetch employee's added Events for Calendar view");
                toReturn = employeeTrainingService.getTrainingEventByEmployeeId(empId);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return toReturn;
    }

    @DeleteMapping("/delete")
    public void getByEmpIdAndSubSkillId(HttpServletRequest request,
                                        @RequestParam("trainingId")String trainingId,
                                        HttpServletResponse response)
            throws IOException {
        logger.info("/delete API called");
        String empId = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (request.getHeader(ConfigurationStrings.AUTHORIZATION)!=null) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to delete employee's added Events");
                employeeTrainingService.cancelEnrollment(empId,trainingId);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/gettrainingeventlist")
    public List<TrainingListEventDomain> getEventListByEmpId(HttpServletRequest request,
                                                             HttpServletResponse response)
            throws IOException {
        logger.info("/gettrainingeventlist API called");
        String empId = null;
        List<TrainingListEventDomain> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(request.getHeader(ConfigurationStrings.AUTHORIZATION) == null)) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to fetch employee's added Events for list view");
                toReturn = employeeTrainingService.getTrainingListEventByEmployeeId(empId);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return toReturn;
    }

    @GetMapping("/getenrolledtrainings")
    public List<EmployeeTrainingPlaceholderDomain> getEnrolledTraining(HttpServletRequest request,
                                                                       HttpServletResponse response)
            throws IOException {
        logger.info("/getenrolledtrainings API called");
        String empId = null;
        List<EmployeeTrainingPlaceholderDomain> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!( request.getHeader(ConfigurationStrings.AUTHORIZATION)== null)) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to fetch employee's added Trainings for training placeholder");
                toReturn = employeeTrainingService.getEnrolledTraining(empId);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return toReturn;
    }

    @GetMapping("/getupcomingtrainings")
    public List<EmployeeTrainingPlaceholderDomain> getUpcomingTraining(HttpServletRequest request,
                                                                       HttpServletResponse response)
            throws IOException {
        logger.info("/getupcomingtrainings API called");
        String empId ;
        List<EmployeeTrainingPlaceholderDomain> toReturn = null;

        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!( request.getHeader(ConfigurationStrings.AUTHORIZATION)== null)) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to fetch Upcoming Trainings for training placeholder");
                toReturn = employeeTrainingService.getUpcomingTraining();
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return toReturn;
    }
}