package com.cdac.skillportal.controller;


import com.cdac.skillportal.helper.ConfigurationStrings;
import com.cdac.skillportal.model.SubSkill;
import com.cdac.skillportal.service.SubSkillService;
import com.cdac.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skill")
@CrossOrigin("*")
public class SubSkillController {

    private static Logger logger = Logger.getLogger(SubSkillController.class);
    @Autowired
    SubSkillService subSkillService;

    @Autowired
    private TokenValidationService tokenValidator;

    /*
     * For fetching all the Subskills of a Particular Skill
     * UI:- for displaying the list of Subskill to rate on clicking "Explore"
     * params:- skillName,,format(skillgroupname_skill_name),,Ex:- Cloud_AWS
     * Integration Testing done:-(11-04-2018)
     * EmployeeId Validation
     */
    @GetMapping("/getallsubskill")
    public Map<String, List<SubSkill>> getAllSubSkillsOfEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("getallsubskill API called");
        String employeeId;
        Map<String, List<SubSkill>> toReturn = null;

        logger.info(ConfigurationStrings.FETCHING);
        if (request.getHeader(ConfigurationStrings.AUTHORIZATION) != null) {
            employeeId = tokenValidator.extractEmployeeId(request);
            logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
            toReturn = subSkillService.getAllSubSkillsOfEmployee();

        } else {
            logger.info(ConfigurationStrings.NOTFOUND);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return toReturn;
    }
}
