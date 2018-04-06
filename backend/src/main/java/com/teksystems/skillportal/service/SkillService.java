package com.teksystems.skillportal.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.init.GuavaCacheInit;

@Service
public class SkillService {
	
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

}
