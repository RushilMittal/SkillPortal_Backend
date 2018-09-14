package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.service.CertificationService;
import com.teksystems.skillportal.service.SearchService;
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

    public boolean checkEmployeeId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String employeeId;
        boolean status = false;

        logger.info(ConfigurationStrings.FETCHING);
        if (request.getHeader(ConfigurationStrings.AUTHORIZATION) != null) {
            employeeId = tokenValidator.extractEmployeeId(request);
            logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
            status = true;
        } else {
            logger.info(ConfigurationStrings.NOTFOUND);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
        }
        return status;
    }

    @GetMapping("/searchskill")
    public List<String> searchSkill(HttpServletRequest request, @RequestParam String searchTerm, HttpServletResponse response) throws IOException {
        logger.info("/searchskill API called");
        List<String> toReturn = null;
        if (checkEmployeeId(request, response)) {
            toReturn = searchService.searchSkill(searchTerm);
        }
        return toReturn;
    }

    @GetMapping("/searchcertitems")
    public List<CertificationDomain> getCertSearch(HttpServletRequest request, @RequestParam String searchTerm, HttpServletResponse response) throws IOException {
        logger.info("/searchcertitems API called");

        List<CertificationDomain> toReturn = null;
        if (checkEmployeeId(request, response)) {
            toReturn = certificationService.searchCertItems(searchTerm);
        }
        return toReturn;
    }

    @GetMapping("/searchtraining")
    public List<Training> searchTraining(HttpServletRequest request, @RequestParam String searchTerm, HttpServletResponse response) throws IOException {
        logger.info("/searchtraining API called");

        List<Training> toReturn = null;
        if (checkEmployeeId(request, response)) {
            toReturn = trainingService.searchTraining(searchTerm);
        }
        return toReturn;

    }

}