package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.helper.SkillHelper;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.SkillGroupService;
import com.teksystems.skillportal.service.SkillService;
import com.teksystems.skillportal.service.SubSkillService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController

@RequestMapping(value="/skill",method= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@CrossOrigin("*")
public class SkillController {
    private static Logger logger = Logger.getLogger(SkillController.class);

    @Autowired
    SkillHelper skillHelper;
    @Autowired
    SkillGroupService skillGroupService;
    @Autowired
    SubSkillService subSkillService;
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
    public  Map<String,List<String>> getAllSkillGroups(HttpServletRequest request) throws ExecutionException
    {
        System.out.println("/getallskillgroups API called");
        String employeeId = null;
        Map<String,List<String>> toReturn = null;
        try {
            System.out.println("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if(!((HttpServletRequest) request).getHeader("Authorization").equals(null)) {
                System.out.println("inside the if");
                employeeId = tokenValidator.ExtractEmployeeId(request);
                System.out.println("Paramater received : employeeId " + employeeId);
                toReturn = skillGroupService.getAllSkillGroups();
            } else {
                System.out.println("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        System.out.println(toReturn);
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
    public Map<String,List<SubSkill>> getAllSkills(HttpServletRequest request) throws ExecutionException
    {
        logger.info("getallskills API called");
        String employeeId = null;
        Map<String,List<SubSkill>> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                toReturn = skillService.getAllSkills();

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
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
    public List<String> getSkillGroup(HttpServletRequest request,@RequestParam String skillGroup) throws ExecutionException
    {
        logger.info("getskillgroup API Called");
        String employeeId = null;
        List<String> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                toReturn = skillService.getSkillGroup(skillGroup);

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occured: " + e.toString());
        }
        return toReturn;
    }







}
