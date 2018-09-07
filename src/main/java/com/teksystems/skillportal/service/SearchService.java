package com.teksystems.skillportal.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.init.GuavaCacheInit;
import com.teksystems.skillportal.model.SubSkill;

@Service
public class SearchService {

	@Autowired
	GuavaCacheInit guavaCacheInit;
	
	public List<String> searchSkill(@RequestParam String search)
	{
		LoadingCache<String, List<SubSkill>> skillCache = guavaCacheInit.getSkillLoadingCache();
		List<String> searchRes = new LinkedList<>();
		Map<String,List<SubSkill>> searchup = skillCache.asMap();
		for (Map.Entry<String, List<SubSkill>> entrySet : searchup.entrySet()) {
			String key = entrySet.getKey();
			Pattern p = Pattern.compile(search); 
    		Matcher m = p.matcher(key.toLowerCase());
    		if( m.find() )
    			searchRes.add(key);
    		List<SubSkill> searchSub = searchup.get(key);
    		for (SubSkill value : searchSub) {
    			Pattern p1 = Pattern.compile(search.toLowerCase());
        		Matcher m1 = p1.matcher(value.getSubSkill().toLowerCase());
        		if( m1.find() )
        			searchRes.add(key+"_"+value.getSubSkill());	
    		}
		}
		return searchRes;
	}

	}