package com.teksystems.skillportal.service;

import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.init.GuavaCacheInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SkillGroupService {

    @Autowired
    GuavaCacheInit guavaCacheInit;

    public Map<String, List<String>> getAllSkillGroups() {

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
