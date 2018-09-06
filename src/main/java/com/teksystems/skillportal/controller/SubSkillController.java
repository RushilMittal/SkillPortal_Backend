package com.teksystems.skillportal.controller;



import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.SubSkillService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
    public Map<String,List<SubSkill>> getAllSubSkillsOfEmployee(HttpServletRequest request,@RequestParam String skillName) throws ExecutionException {
        logger.info("getallsubskill API called");
        String employeeId = null;
        Map<String,List<SubSkill>> toReturn = null;
        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (!(((HttpServletRequest) request).getHeader(ConfigurationStrings.AUTHORIZATION).toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                toReturn = subSkillService.getAllSubSkillsOfEmployee();

            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
            }
        } catch (Exception e) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
        }
        return toReturn;
    }
}
