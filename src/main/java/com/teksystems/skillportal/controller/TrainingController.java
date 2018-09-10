package com.teksystems.skillportal.controller;


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
@RequestMapping(value="/training",method= {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin("*")
public class TrainingController {
    private static Logger logger = Logger.getLogger(TrainingController.class);

    @Autowired
    TrainingService trainingService;
    @Autowired
    private TokenValidationService tokenValidator;

    @PostMapping("/add")
    public void add(HttpServletRequest request, @RequestBody TrainingDomain training, HttpServletResponse response) throws IOException {
        logger.info("/add Training API called");
        String empId;

        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!( request.getHeader(ConfigurationStrings.AUTHORIZATION)==null)) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to add new Training");
                    trainingService.saveTraining(training.getTraining(), training.getTrainingSessions());
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getalltraining")
    public List<TrainingDomain> getTraining(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("/gettraining API called");
        String empId ;
        List<TrainingDomain> toReturn = null;

        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!( request.getHeader(ConfigurationStrings.AUTHORIZATION)==null)) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to fetch all the trainings available");
                toReturn = trainingService.getAllTrainings(empId);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return toReturn;
    }


    @PostMapping("/enroll")
    public void enrollTraining(HttpServletRequest request, @RequestParam String trainingId, HttpServletResponse response) throws IOException {
        logger.info("/enroll Training API called");
        String empId;

        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!( request.getHeader(ConfigurationStrings.AUTHORIZATION)==null)) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to enroll a New Training for a particular employee");
                trainingService.enrollTraining(empId, trainingId);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/update")
    public void update(HttpServletRequest request, @RequestBody TrainingDomain training, HttpServletResponse response) throws IOException {
        logger.info("/update Training API called");
        String empId;

        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!( request.getHeader(ConfigurationStrings.AUTHORIZATION)==null)) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to update Training details ");
                trainingService.updateTraining(training.getTraining(), training.getTrainingSessions());
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

}