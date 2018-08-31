package com.teksystems.skillportal.service;

import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.init.GuavaCacheInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class SkillGroupService {

    @Autowired
    GuavaCacheInit guavaCacheInit;

    public Map<String,List<String>> getAllSkillGroups() throws ExecutionException {
        System.out.println("INside the getallservices");
        LoadingCache<String, List<String>> skillGroupCache = guavaCacheInit.getLoadingCache();
        System.out.println("Cache Size:" + skillGroupCache.size());
        return skillGroupCache.asMap();


    }


    public List<String> getAllSkillsBySkillGroup(String skillGroup) throws Exception {
        LoadingCache<String, List<String>> skillGroupCache = guavaCacheInit.getLoadingCache();
        Map<String, List<String>> received =skillGroupCache.asMap();

        List<String> skillGroups = new LinkedList<>();

        skillGroups = received.get(skillGroup);

        return skillGroups;
    }
}
