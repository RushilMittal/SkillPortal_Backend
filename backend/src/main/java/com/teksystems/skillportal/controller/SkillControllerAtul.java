package com.teksystems.skillportal.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.teksystems.skillportal.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/skill",method= {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin("*")
public class SkillControllerAtul {
	
	@Autowired
	SkillService skillService;
	
	@GetMapping("/getskillgroup")
	public List<String> getSkillGroup(@RequestParam String skillGroup) throws ExecutionException
	{
		return skillService.getSkillGroup(skillGroup);
	}

	//Done to Domain Way
//	@GetMapping("/getallskillgroups")
//	public Map<String,List<String>> getAllSkillGroups() throws ExecutionException
//	{
//		return skillServiceAtul.getAllSkillGroups();
//	}

}
