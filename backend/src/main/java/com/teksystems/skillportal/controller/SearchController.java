package com.teksystems.skillportal.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
import com.teksystems.skillportal.domain.SearchSkill;
import com.teksystems.skillportal.helper.SearchHelper;
import com.teksystems.skillportal.model.Skill;
import com.teksystems.skillportal.model.SubSkill;

import com.teksystems.skillportal.service.TrainingService;
import com.teksystems.skillportal.model.Training;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/skill")
@CrossOrigin("*")
public class SearchController {

    @Autowired
    SearchServiceAtul searchServiceAtul;

    @Autowired
    CertificationService certificationService;

    private TokenValidationService tokenValidator;

    private static Logger logger = Logger.getLogger(SearchController.class);

    @GetMapping("/searchskill")
    public List<String> searchSkill(HttpServletRequest request, @RequestParam String searchTerm) throws ExecutionException {
        logger.info("/searchskill API called");
        String employeeId = null;
        List<String> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                toReturn = searchServiceAtul.searchSkill(searchTerm);

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;
    }

    @GetMapping("/searchcertitems")
    public List<CertificationDomain> getCertSearch(HttpServletRequest request, @RequestParam String searchTerm) {
        logger.info("/searchcertitems API called");
        String employeeId = null;
        List<CertificationDomain> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                toReturn = certificationService.searchCertItems(searchTerm);

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;

    }
	
	 @GetMapping("/searchtraining")
    public List<Training> searchTraining(HttpServletRequest request, @RequestParam String searchTerm){
        logger.info("/searchtraining API called");
        String employeeId = null;
        List<Training> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                toReturn = trainingService.searchTraining(searchTerm);

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;

    }

}