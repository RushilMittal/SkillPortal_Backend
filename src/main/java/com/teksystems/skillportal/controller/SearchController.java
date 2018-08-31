package com.teksystems.skillportal.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.service.CertificationService;
import com.teksystems.skillportal.service.SearchServiceAtul;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teksystems.skillportal.domain.CertificationDomain;


import com.teksystems.skillportal.service.TrainingService;
import com.teksystems.skillportal.model.Training;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/skill")
@CrossOrigin("*")
public class SearchController {

    @Autowired
	TrainingService trainingService;

    @Autowired
    SearchServiceAtul searchServiceAtul;

    @Autowired
    CertificationService certificationService;

    @Autowired
    private TokenValidationService tokenValidator;

    private static Logger logger = Logger.getLogger(SearchController.class);

    @GetMapping("/searchskill")
    public List<String> searchSkill(HttpServletRequest request, @RequestParam String searchTerm) throws ExecutionException {
        logger.info("/searchskill API called");
        String employeeId = null;
        List<String> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(((HttpServletRequest) request).getHeader(ConfigurationStrings.AUTHORIZATION).toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                toReturn = searchServiceAtul.searchSkill(searchTerm);

            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
        }
        return toReturn;
    }

    @GetMapping("/searchcertitems")
    public List<CertificationDomain> getCertSearch(HttpServletRequest request, @RequestParam String searchTerm) {
        logger.info("/searchcertitems API called");
        String employeeId = null;
        List<CertificationDomain> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(((HttpServletRequest) request).getHeader(ConfigurationStrings.AUTHORIZATION).toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                toReturn = certificationService.searchCertItems(searchTerm);

            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
        }
        return toReturn;

    }
	
	 @GetMapping("/searchtraining")
    public List<Training> searchTraining(HttpServletRequest request, @RequestParam String searchTerm){
        logger.info("/searchtraining API called");
        String employeeId = null;
        List<Training> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(((HttpServletRequest) request).getHeader(ConfigurationStrings.AUTHORIZATION).toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                toReturn = trainingService.searchTraining(searchTerm);

            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
        }
        return toReturn;

    }

}