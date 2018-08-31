package com.teksystems.skillportal.controller;


import com.teksystems.skillportal.domain.TrainingDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.service.TokenValidationService;
import com.teksystems.skillportal.service.TrainingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public void add(HttpServletRequest request, @RequestBody TrainingDomain training)
    {
        logger.info("/add Training API called");
        String empId = null;

        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(((HttpServletRequest) request).getHeader(ConfigurationStrings.AUTHORIZATION).toString().equals(null))) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to add new Training");
                    trainingService.saveTraining(training.getTraining(), training.getTrainingSessions());
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
        }
    }

    @GetMapping("/getalltraining")
    public List<TrainingDomain> getTraining(HttpServletRequest request)
    {
        logger.info("/gettraining API called");
        String empId = null;
        List<TrainingDomain> toReturn = null;

        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(((HttpServletRequest) request).getHeader(ConfigurationStrings.AUTHORIZATION).toString().equals(null))) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to fetch all the trainings available");
                toReturn = trainingService.getAllTrainings(empId);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
        }
        return toReturn;
    }


    @PostMapping("/enroll")
    public void enrollTraining(HttpServletRequest request,@RequestParam String trainingId)
    {
        logger.info("/enroll Training API called");
        String empId = null;

        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(((HttpServletRequest) request).getHeader(ConfigurationStrings.AUTHORIZATION).toString().equals(null))) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to enroll a New Training for a particular employee");
                trainingService.enrollTraining(empId, trainingId);
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
        }

    }

    @PostMapping("/update")
    public void update(HttpServletRequest request,@RequestBody TrainingDomain training)
    {
        logger.info("/update Training API called");
        String empId = null;

        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(((HttpServletRequest) request).getHeader(ConfigurationStrings.AUTHORIZATION).toString().equals(null))) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + empId);
                logger.info("Trying to update Training details ");
                trainingService.updateTraining(training.getTraining(), training.getTrainingSessions());
            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
        }

    }

}