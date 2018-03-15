package com.teksystems.skillportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.domain.SearchSkill;
import com.teksystems.skillportal.helper.SearchHelper;

@RestController
@RequestMapping("/skill")
@CrossOrigin("*")
public class SearchController {
	
	@Autowired
	SearchHelper searchService;

	@GetMapping("/searchitems")
	 public List<SearchSkill> getSearch(@RequestParam String searchTerm) {
          return searchService.searchSkillItems(searchTerm);    
		 }
	
	@GetMapping("/searchcertitems")
	 public List<CertificationDomain> getCertSearch(@RequestParam String searchTerm) {
         return searchService.searchCertItems(searchTerm);    
		 }
}