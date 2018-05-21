package com.teksystems.skillportal.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.SubSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.init.GuavaCacheInit;

@Service
public class SkillService {
	@Autowired
    SubSkillRepository subSkillRepository;
	public List<String> getSkillGroup(@RequestParam String skillGroup) throws ExecutionException
	{
		LoadingCache<String, List<String>> skillGroupCache = GuavaCacheInit.getLoadingCache();
		System.out.println("Cache Size:" + skillGroupCache.size());
		return skillGroupCache.get(skillGroup);
	}
	
	public Map<String,List<String>> getAllSkillGroups() throws ExecutionException
	{
		LoadingCache<String, List<String>> skillGroupCache = GuavaCacheInit.getLoadingCache();
		System.out.println("Cache Size:" + skillGroupCache.size());
		return skillGroupCache.asMap();
	}

    public Map<String,List<SubSkill>> getAllSkills() throws ExecutionException{
        LoadingCache<String, List<SubSkill>> skillCache = GuavaCacheInit.getSkillLoadingCache();
        System.out.println("Cache Size:" + skillCache.size());
        return skillCache.asMap();
    }
    public List<SubSkill> getAllAdminSkills() throws ExecutionException{
        List<SubSkill> toReturn = subSkillRepository.findAll();
        System.out.println(toReturn);
        return toReturn;

	}


}
