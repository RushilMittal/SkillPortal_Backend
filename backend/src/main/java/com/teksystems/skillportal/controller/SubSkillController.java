package com.teksystems.skillportal.controller;



import com.teksystems.skillportal.service.SubSkillService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.teksystems.skillportal.domain.SubSkillDomain;

@RestController
@RequestMapping("/skill")
@CrossOrigin("*")
public class SubSkillController {

    private static Logger logger = Logger.getLogger(SubSkillController.class);
	@Autowired
   SubSkillService subSkillService;



    @GetMapping("/getBySubSkillId")
    public SubSkillDomain getBySubSkillId(@RequestParam String subSkillId) {
               logger.debug("Parameters : skillId "+subSkillId);
               logger.info("Subskill having subskillid "+subSkillId+" fetched");
               return subSkillService.getBySubSkillId(subSkillId);
    }
    
}
