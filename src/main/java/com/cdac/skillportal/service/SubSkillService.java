package com.cdac.skillportal.service;

import com.cdac.skillportal.init.GuavaCacheInit;
import com.cdac.skillportal.model.SubSkill;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SubSkillService {
    @Autowired
    GuavaCacheInit guavaCacheInit;

    public Map<String, List<SubSkill>> getAllSubSkillsOfEmployee() {

        LoadingCache<String, List<SubSkill>> skillCache = guavaCacheInit.getSkillLoadingCache();
        return skillCache.asMap();

    }


}