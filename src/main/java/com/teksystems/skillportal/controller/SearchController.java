package com.teksystems.skillportal.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.service.CertificationService;
import com.teksystems.skillportal.service.SearchService;
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
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/skill")
@CrossOrigin("*")
public class SearchController {

    @Autowired
	TrainingService trainingService;

    @Autowired
    SearchService searchService;

    @Autowired
    CertificationService certificationService;

    @Autowired
    private TokenValidationService tokenValidator;

    private static Logger logger = Logger.getLogger(SearchController.class);

    @GetMapping("/searchskill")
    public List<String> searchSkill(HttpServletRequest request, @RequestParam String searchTerm, HttpServletResponse response) throws ExecutionException, IOException {
        logger.info("/searchskill API called");
        String employeeId;
        List<String> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (request.getHeader(ConfigurationStrings.AUTHORIZATION)!=null) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                toReturn = searchService.searchSkill(searchTerm);

            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ConfigurationStrings.MONGOEXCEPTION);
        }
        return toReturn;
    }

    @GetMapping("/searchcertitems")
    public List<CertificationDomain> getCertSearch(HttpServletRequest request, @RequestParam String searchTerm, HttpServletResponse response) throws IOException {
        logger.info("/searchcertitems API called");
        String employeeId ;
        List<CertificationDomain> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (request.getHeader(ConfigurationStrings.AUTHORIZATION)!=null) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                toReturn = certificationService.searchCertItems(searchTerm);

            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ConfigurationStrings.MONGOEXCEPTION);
        }
        return toReturn;

    }
	
	 @GetMapping("/searchtraining")
    public List<Training> searchTraining(HttpServletRequest request, @RequestParam String searchTerm, HttpServletResponse response) throws IOException {
        logger.info("/searchtraining API called");
        String employeeId;
        List<Training> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (request.getHeader(ConfigurationStrings.AUTHORIZATION)!=null) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                toReturn = trainingService.searchTraining(searchTerm);

            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(ConfigurationStrings.ERROR + e.toString());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ConfigurationStrings.MONGOEXCEPTION);
        }
        return toReturn;

    }

}