package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.SkillGroupService;
import com.teksystems.skillportal.service.SkillService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController

@RequestMapping(value = "/skill", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@CrossOrigin("*")
public class SkillController {
    private static Logger logger = Logger.getLogger(SkillController.class);

    @Autowired
    SkillGroupService skillGroupService;

    @Autowired
    SkillService skillService;
    @Autowired
    private TokenValidationService tokenValidator;

    /*
     * For fetching the skill groups with the skills
     * UI :- for "All Skill", listing all skill groups with skills (DropDown Menu)
     * Param:- NOPARAM
     * Integration Testing Done :- 	11-04-2018
     * EmployeeId validation added:- 14-04-2018
     */
    @GetMapping("/getallskillgroups")
    public Map<String, List<String>> getAllSkillGroups(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, List<String>> toReturn = null;

        if (request.getHeader(ConfigurationStrings.AUTHORIZATION) != null) {


            toReturn = skillGroupService.getAllSkillGroups();
        } else {
            logger.info(ConfigurationStrings.NOTFOUND);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return toReturn;
    }

    /*
     * For fetching the all skills of all the Skill Group
     * UI :- Used for populating the dropdown under the "New Certification"
     * Param:- NOPARAM
     * Integration Testing Done :- 	11-04-2018
     * EmployeeId Validation added :- 14-04-2018
     */
    @GetMapping("/getallskills")
    public Map<String, List<SubSkill>> getAllSkills(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("getallskills API called");
        String employeeId;
        Map<String, List<SubSkill>> toReturn = null;

        logger.info(ConfigurationStrings.FETCHING);
        if ((request.getHeader(ConfigurationStrings.AUTHORIZATION) != null)) {
            employeeId = tokenValidator.extractEmployeeId(request);
            logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
            toReturn = skillService.getAllSkills();
        } else {
            logger.info(ConfigurationStrings.NOTFOUND);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        return toReturn;
    }

    /*
     * For fetching the skills of particular Skill Group
     * UI :- NOT USED YET
     * Param:- skillgroup name format:-(skillgroup)
     * Integration Testing Done :- 	11-04-2018
     * EmployeeId Validation added :- 14-04-2018
     */

    @GetMapping("/getskillgroup")
    public List<String> getSkillGroup(HttpServletRequest request, @RequestParam String skillGroup, HttpServletResponse response) throws IOException, ExecutionException {
        logger.info("getskillgroup API Called");
        String employeeId;
        List<String> toReturn = null;


        logger.info(ConfigurationStrings.FETCHING);
        if ((request.getHeader(ConfigurationStrings.AUTHORIZATION) != null)) {
            employeeId = tokenValidator.extractEmployeeId(request);
            logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
            toReturn = skillService.getSkillGroup(skillGroup);

        } else {
            logger.info(ConfigurationStrings.NOTFOUND);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }


        return toReturn;
    }


}
