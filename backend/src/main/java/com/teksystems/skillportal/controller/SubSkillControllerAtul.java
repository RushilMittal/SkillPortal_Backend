package com.teksystems.skillportal.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.teksystems.skillportal.service.SubSkillServiceAtul;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.SubSkillService;

@RestController
@RequestMapping(value="/skill",method= {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin("*")
public class SubSkillControllerAtul {
	
	@Autowired
	SubSkillServiceAtul subSkillServiceAtul;
	
//	@GetMapping("/getskill")
//	public List<SubSkill> getSkill(@RequestParam String skill) throws ExecutionException
//	{
//		return subSkillServiceAtul.getSkill(skill);
//	}
//
//	@GetMapping("/getallskills")
//	public Map<String,List<SubSkill>> getAllSkills() throws ExecutionException
//	{
//		return subSkillServiceAtul.getAllSkills();
//	}

}
