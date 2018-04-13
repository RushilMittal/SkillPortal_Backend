package com.teksystems.skillportal.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.init.GuavaCacheInit;
import com.teksystems.skillportal.model.SubSkill;
@Service
public class SubSkillServiceAtul {
	
	public List<SubSkill> getSkill(@RequestParam String skill) throws ExecutionException
	{
		LoadingCache<String, List<SubSkill>> skillCache = GuavaCacheInit.getSkillLoadingCache();
		System.out.println("Cache Size:" + skillCache.size());
		return skillCache.get(skill);
	}
	
	public Map<String,List<SubSkill>> getAllSkills() throws ExecutionException
	{
		LoadingCache<String, List<SubSkill>> skillCache = GuavaCacheInit.getSkillLoadingCache();
		System.out.println("Cache Size:" + skillCache.size());
		return skillCache.asMap();
	}

}
