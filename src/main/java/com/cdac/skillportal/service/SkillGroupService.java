package com.cdac.skillportal.service;

import com.cdac.skillportal.init.GuavaCacheInit;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SkillGroupService {

    @Autowired
    GuavaCacheInit guavaCacheInit;

    public Map<String, List<String>> getAllSkillGroups() {
        guavaCacheInit.loadSkill();
        LoadingCache<String, List<String>> skillGroupCache = guavaCacheInit.getLoadingCache();

        return skillGroupCache.asMap();

    }


    public List<String> getAllSkillsBySkillGroup(String skillGroup) {
        LoadingCache<String, List<String>> skillGroupCache = guavaCacheInit.getLoadingCache();
        Map<String, List<String>> received = skillGroupCache.asMap();

        List<String> skillGroups;

        skillGroups = received.get(skillGroup);

        return skillGroups;
    }
}
