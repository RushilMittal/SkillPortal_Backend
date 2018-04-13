package com.teksystems.skillportal.controller;



import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.SubSkillService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.teksystems.skillportal.domain.SubSkillDomain;

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

    /*
     * For fetching all the Subskills of a Particular Skill
     * UI:- for displaying the list of Subskill to rate on clicking "Explore"
     * params:- skillName,,format(skillgroupname_skill_name),,Ex:- Cloud_AWS
     * Integration Testing done:-(11-04-2018)
     */
    @GetMapping("/getallsubskill")
    public Map<String,List<SubSkill>> getAllSubSkillsOfEmployee(@RequestParam String skillName) throws ExecutionException {
        System.out.println("Received :" + skillName);
        return subSkillService.getAllSubSkillsOfEmployee(skillName);
    }


    
}
