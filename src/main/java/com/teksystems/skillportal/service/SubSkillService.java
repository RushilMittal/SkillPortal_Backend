package com.teksystems.skillportal.service;
import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.init.GuavaCacheInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.teksystems.skillportal.model.SubSkill;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class SubSkillService {
    @Autowired
    GuavaCacheInit guavaCacheInit;
    
    public Map<String,List<SubSkill>> getAllSubSkillsOfEmployee(){

        LoadingCache<String, List<SubSkill>> skillCache = guavaCacheInit.getSkillLoadingCache();
        return skillCache.asMap();

    }
    


    
 
}