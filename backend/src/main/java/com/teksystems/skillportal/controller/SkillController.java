package com.teksystems.skillportal.controller;

import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.domain.SkillGroupDomain;
import com.teksystems.skillportal.helper.SkillHelper;
import com.teksystems.skillportal.init.GuavaCacheInit;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.SkillGroupService;
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

    /*
    * For fetching the skill groups with the skills
    * UI :- for "All Skill", listing all skill groups with skills (DropDown Menu)
     */
    @GetMapping("/getallskillgroups")
    public  Map<String,List<String>> getAllSkillGroups() throws ExecutionException
    {
        return skillGroupService.getAllSkillGroups();

    }




    @GetMapping("/getskill")
    public List<SubSkill> getSkill(@RequestParam String skill) throws ExecutionException
    {
        LoadingCache<String, List<SubSkill>> skillCache = GuavaCacheInit.getSkillLoadingCache();
        System.out.println("Cache Size:" + skillCache.size());
        return skillCache.get(skill);
    }

    @GetMapping("/getallskills")
    public Map<String,List<SubSkill>> getAllSkills() throws ExecutionException
    {

        LoadingCache<String, List<SubSkill>> skillCache = GuavaCacheInit.getSkillLoadingCache();
        System.out.println("Cache Size:" + skillCache.size());
        return skillCache.asMap();
    }

    @GetMapping("/getallskillsbyskillgroup")
    public List<String> getAllSkillsBySkillGroup(@RequestParam String skillGroup) throws ExecutionException
    {
        return skillGroupService.getAllSkillsBySkillGroup(skillGroup);

    }





//
//    @GetMapping("/all")
//    public List<SkillDomain> getAllSkills()
//    {   logger.info("All skills fetched");
//        return skillHelper.getAll();
//    }
//
//    @GetMapping("/getBySkillId")
//    public SkillDomain getBySubSkillId(@RequestParam String skillId)
//    {
//        logger.debug("Parameters : skillId "+skillId);
//        logger.info("Skill having skillid "+skillId+" fetched");
//        return skillHelper.getById(skillId);
//    }
//
//    @GetMapping("/getBySkillName")
//    public SkillDomain getBySkillName(@RequestParam String skillName)
//    {
//        logger.debug("Parameters : skillName "+skillName);
//        logger.info("Skill having skillName "+skillName+" fetched");
//        return skillHelper.getBySkillName(skillName);
//    }

}
