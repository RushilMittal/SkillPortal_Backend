package com.teksystems.skillportal.service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.SubSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.init.GuavaCacheInit;

@Service
public class SkillService {

    SubSkillRepository subSkillRepository;

	GuavaCacheInit guavaCacheInit;

    @Autowired
	public SkillService(SubSkillRepository subSkillRepository, GuavaCacheInit guavaCacheInit) {
    	this.subSkillRepository = subSkillRepository;
    	this.guavaCacheInit = guavaCacheInit;
	}
	public List<String> getSkillGroup(String skillGroup) throws ExecutionException
	{
		LoadingCache<String, List<String>> skillGroupCache = guavaCacheInit.getLoadingCache();
		return skillGroupCache.get(skillGroup);
	}
	
	public Map<String,List<String>> getAllSkillGroups() throws ExecutionException
	{
		LoadingCache<String, List<String>> skillGroupCache = guavaCacheInit.getLoadingCache();

		return skillGroupCache.asMap();
	}

    public Map<String,List<SubSkill>> getAllSkills() throws ExecutionException{
        LoadingCache<String, List<SubSkill>> skillCache = guavaCacheInit.getSkillLoadingCache();

        return skillCache.asMap();
    }


}
