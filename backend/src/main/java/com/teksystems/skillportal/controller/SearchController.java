package com.teksystems.skillportal.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.teksystems.skillportal.service.CertificationService;
import com.teksystems.skillportal.service.SearchServiceAtul;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.domain.SearchSkill;
import com.teksystems.skillportal.helper.SearchHelper;
import com.teksystems.skillportal.model.Skill;
import com.teksystems.skillportal.model.SubSkill;

@RestController
@RequestMapping("/skill")
@CrossOrigin("*")
public class SearchController {

	@Autowired
	SearchServiceAtul searchServiceAtul;

	@Autowired
	CertificationService certificationService;

	@GetMapping("/searchskill")
	public List<String> searchSkill(@RequestParam String searchTerm) throws ExecutionException
	{
		return searchServiceAtul.searchSkill(searchTerm);
	}

//	@GetMapping("/searchitems")
//	 public List<SearchSkill> getSearch(@RequestParam String searchTerm) {
//          return searchService.searchSkillItems(searchTerm);
//		 }
//
//	@GetMapping("/searchskillitems")
//	 public List<Skill> getSkillSearch(@RequestParam String searchTerm) {
//         return searchService.searchItems(searchTerm);
//		 }
//
//	@GetMapping("/searchsubskillitems")
//	 public List<SubSkill> getSubSkillSearch(@RequestParam String searchTerm) {
//        return searchService.searchSubSkillItems(searchTerm);
//		 }
//
	@GetMapping("/searchcertitems")
	 public List<CertificationDomain> getCertSearch(@RequestParam String searchTerm) {

         return certificationService.searchCertItems(searchTerm);
		 }
}