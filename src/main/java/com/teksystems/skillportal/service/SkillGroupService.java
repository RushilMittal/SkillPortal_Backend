package com.teksystems.skillportal.service;

import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.domain.SkillGroupDomain;
import com.teksystems.skillportal.init.GuavaCacheInit;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class SkillGroupService {

    public Map<String,List<String>> getAllSkillGroups() throws ExecutionException {
        System.out.println("INside the getallservices");
        LoadingCache<String, List<String>> skillGroupCache = GuavaCacheInit.getLoadingCache();
        System.out.println("Cache Size:" + skillGroupCache.size());
        return skillGroupCache.asMap();

        /* No need to create the domain
        LoadingCache<String, List<String>> skillGroupCache = GuavaCacheInit.getLoadingCache();
        Map<String, List<String>> received =skillGroupCache.asMap();

        List<SkillGroupDomain> skillGroupDomains = new LinkedList<>();

        for(Map.Entry<String, List<String>> entry : received.entrySet()) {
            SkillGroupDomain temp = new SkillGroupDomain(entry.getKey(),entry.getValue());
            skillGroupDomains.add(temp);
        }

        return skillGroupDomains;
        */
    }


    public List<String> getAllSkillsBySkillGroup(String skillGroup) throws Exception {
        LoadingCache<String, List<String>> skillGroupCache = GuavaCacheInit.getLoadingCache();
        Map<String, List<String>> received =skillGroupCache.asMap();

        List<String> skillGroups = new LinkedList<>();

        skillGroups = received.get(skillGroup);

        return skillGroups;
    }
}
