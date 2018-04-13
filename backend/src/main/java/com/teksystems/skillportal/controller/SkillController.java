package com.teksystems.skillportal.controller;

import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.domain.SkillGroupDomain;
import com.teksystems.skillportal.helper.SkillHelper;
import com.teksystems.skillportal.init.GuavaCacheInit;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.SkillGroupService;
import com.teksystems.skillportal.service.SkillService;
import com.teksystems.skillportal.service.SubSkillService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController

@RequestMapping(value="/skill",method= {RequestMethod.GET,RequestMethod.POST})
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

    /*
    * For fetching the skill groups with the skills
    * UI :- for "All Skill", listing all skill groups with skills (DropDown Menu)
    * Param:- NOPARAM
    * Integration Testing Done :- 	11-04-2018
    */
    @GetMapping("/getallskillgroups")
    public  Map<String,List<String>> getAllSkillGroups() throws ExecutionException
    {
        return skillGroupService.getAllSkillGroups();

    }
    /*
     * For fetching the all skills of all the Skill Group
     * UI :- Used for populating the dropdown under the "New Certification"
     * Param:- NOPARAM
     * Integration Testing Done :- 	11-04-2018
     */
    @GetMapping("/getallskills")
    public Map<String,List<SubSkill>> getAllSkills() throws ExecutionException
    {

        return skillService.getAllSkills();
    }

    /*
     * For fetching the skills of particular Skill Group
     * UI :- NOT USED YET
     * Param:- skillgroup name format:-(skillgroup)
     * Integration Testing Done :- 	11-04-2018
     */

    @GetMapping("/getskillgroup")
    public List<String> getSkillGroup(@RequestParam String skillGroup) throws ExecutionException
    {
        return skillService.getSkillGroup(skillGroup);
    }

}
