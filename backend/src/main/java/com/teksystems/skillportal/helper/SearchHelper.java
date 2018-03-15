package com.teksystems.skillportal.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.domain.SearchSkill;
import com.teksystems.skillportal.domain.SkillDomain;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.service.CertificationService;
import com.teksystems.skillportal.service.SubSkillService;
@Service
public class SearchHelper {
    
    @Autowired
    SubSkillService subSkillService;

    @Autowired
	SkillHelper skillService;
    
    @Autowired
    CertificationService certificationService;

    /* Service to add skills and subskills to search items to be sent to front end
 	 *  
 	 */
    public List<SearchSkill> searchSkillItems(String searchTerm){
    	
    	final List<SkillDomain> allSkills = skillService.getAll();
    	final List<SubSkillDomain> allSubSkills = subSkillService.getAll();
    	
    	List<SearchSkill> searchItems = new LinkedList<SearchSkill>();
    	
    	for(SkillDomain iterable: allSkills)
    	{
    		Pattern p = Pattern.compile(searchTerm); 
    		Matcher m = p.matcher((iterable.getName()).toLowerCase());
    		boolean match = m.find();
    		if(match)
    		{
    		 SearchSkill searchOne = new SearchSkill();

    		 searchOne.setSkillId(iterable.getId());
    		 searchOne.setName(iterable.getName());
    		 searchOne.setIsChild(false);
    		 searchOne.setSubSkillId(null);
    		
    		 searchItems.add(searchOne);
    		}
    	}
    	
    	for(SubSkillDomain iterable: allSubSkills)
    	{
    		Pattern p = Pattern.compile(searchTerm); 
    		Matcher m = p.matcher((iterable.getName()).toLowerCase());
    		boolean match = m.find();
    		if(match)
    		{
    		SearchSkill searchOne = new SearchSkill();

    		 searchOne.setSkillId(iterable.getSkillId());
    		 searchOne.setName(iterable.getName());
    		 searchOne.setIsChild(true);
    		 searchOne.setSubSkillId(iterable.getId());
    		
    		 searchItems.add(searchOne);
    		}
    	}
    	
    	return searchItems;
    }
    
    public List<CertificationDomain> searchCertItems(String searchTerm){
    	
      List<CertificationDomain> certDomain = certificationService.getAllCertifications();
      List<CertificationDomain> certSearch = new LinkedList<>();
      
      for(CertificationDomain iterable: certDomain)
  	   {
  		Pattern p = Pattern.compile(searchTerm); 
  		Matcher m1 = p.matcher((iterable.getCertificationName()).toLowerCase());
  		Matcher m2 = p.matcher((iterable.getInstitution()).toLowerCase());
  		if(m1.find()||m2.find())
  		{
  			certSearch.add(iterable);
  		}
  	   }
      return certSearch;
    }

}