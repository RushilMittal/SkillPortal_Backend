package com.teksystems.skillportal.controller;


import com.mongodb.MongoException;
import com.teksystems.skillportal.domain.TrainingDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.service.TokenValidationService;
import com.teksystems.skillportal.service.TrainingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/training", method = {RequestMethod.GET, RequestMethod.POST})
@CrossOrigin("*")
public class TrainingController {
    private static Logger logger = Logger.getLogger(TrainingController.class);

    @Autowired
    TrainingService trainingService;
    @Autowired
    private TokenValidationService tokenValidator;

    public String getEmployeeId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fetchedEmployeeId = null;

        logger.info(ConfigurationStrings.FETCHING);
        if (request.getHeader(ConfigurationStrings.AUTHORIZATION) != null) {
            fetchedEmployeeId = tokenValidator.ExtractEmployeeId(request);
            logger.debug(ConfigurationStrings.EMPLOYEEID + fetchedEmployeeId);
        } else {
            logger.info(ConfigurationStrings.NOTFOUND);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
        }
        return fetchedEmployeeId;
    }

    @PostMapping("/add")
    public void add(HttpServletRequest request, @RequestBody TrainingDomain training, HttpServletResponse response) throws IOException {
        logger.info("/add Training API called");
        try {
            if (getEmployeeId(request, response) != null) {
                trainingService.saveTraining(training.getTraining(), training.getTrainingSessions());
            }
        } catch (MongoException e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }
    }

    @GetMapping("/getalltraining")
    public List<TrainingDomain> getTraining(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("/gettraining API called");

        List<TrainingDomain> toReturn = null;
        String employeeId = getEmployeeId(request, response);
        try {
            if (employeeId != null) {
                toReturn = trainingService.getAllTrainings(employeeId);
            }
        } catch (MongoException e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);

        }

        return toReturn;
    }


    @PostMapping("/enroll")
    public void enrollTraining(HttpServletRequest request, @RequestParam String trainingId, HttpServletResponse response) throws IOException {
        logger.info("/enroll Training API called");
        String employeeId = getEmployeeId(request, response);
        try {
            if (employeeId != null) {
                trainingService.enrollTraining(employeeId, trainingId);
            }
        } catch (MongoException e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }


    }

    @PostMapping("/update")
    public void update(HttpServletRequest request, @RequestBody TrainingDomain training, HttpServletResponse response) throws IOException {
        logger.info("/update Training API called");
        try {
            if (getEmployeeId(request, response) != null) {
                trainingService.updateTraining(training.getTraining(), training.getTrainingSessions());
            }
        } catch (MongoException e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ConfigurationStrings.MONGOEXCEPTION);
        }


    }

}