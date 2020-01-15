package com.cdac.skillportal.service;

import com.cdac.skillportal.init.GuavaCacheInit;
import com.cdac.skillportal.model.SubSkill;
import com.google.common.cache.LoadingCache;
import com.cdac.skillportal.repository.SubSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class SkillService {

    SubSkillRepository subSkillRepository;

    GuavaCacheInit guavaCacheInit;

    @Autowired
    public SkillService(SubSkillRepository subSkillRepository, GuavaCacheInit guavaCacheInit) {
        this.subSkillRepository = subSkillRepository;
        this.guavaCacheInit = guavaCacheInit;
    }

    public List<String> getSkillGroup(String skillGroup) throws ExecutionException {
        LoadingCache<String, List<String>> skillGroupCache = guavaCacheInit.getLoadingCache();
        return skillGroupCache.get(skillGroup);
    }

    public Map<String, List<String>> getAllSkillGroups() {
        LoadingCache<String, List<String>> skillGroupCache = guavaCacheInit.getLoadingCache();

        return skillGroupCache.asMap();
    }

    public Map<String, List<SubSkill>> getAllSkills() {
        LoadingCache<String, List<SubSkill>> skillCache = guavaCacheInit.getSkillLoadingCache();

        return skillCache.asMap();
    }


}
