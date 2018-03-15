package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.helper.SkillHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.teksystems.skillportal.domain.SkillDomain;
import com.teksystems.skillportal.helper.SkillHelper;

import java.util.List;

@RestController
@RequestMapping("/skill")
@CrossOrigin("*")
public class SkillController {
    private static Logger logger = Logger.getLogger(SkillController.class);

    @Autowired
    SkillHelper skillHelper;


    @GetMapping("/all")
    public List<SkillDomain> getAllSkills()
    {   logger.info("All skills fetched");
        return skillHelper.getAll();
    }

    @GetMapping("/getBySkillId")
    public SkillDomain getBySubSkillId(@RequestParam String skillId)
    {
        logger.debug("Parameters : skillId "+skillId);
        logger.info("Skill having skillid "+skillId+" fetched");
        return skillHelper.getById(skillId);
    }
    
    @GetMapping("/getBySkillName")
    public SkillDomain getBySkillName(@RequestParam String skillName)
    {
        logger.debug("Parameters : skillName "+skillName);
        logger.info("Skill having skillName "+skillName+" fetched");
        return skillHelper.getBySkillName(skillName);
    }


}
