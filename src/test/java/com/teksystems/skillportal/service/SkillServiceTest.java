package com.teksystems.skillportal.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.teksystems.skillportal.init.GuavaCacheInit;
import com.teksystems.skillportal.init.MongoConfigNew;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.SubSkillRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SkillServiceTest {

    @Mock
    SubSkillRepository subSkillRepository;

    @Mock
    GuavaCacheInit guavaCacheInit;

    @Mock
    MongoOperations mongoOperation;

    @InjectMocks
    SkillService skillService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(MongoConfigNew.class);


        mongoOperation =(MongoOperations) ctx.getBean("mongoTemplate");
    }

    @Test
    public void getSkillGroup() throws ExecutionException {
        List<String> skillCacheList = getSkillList();

        CacheLoader<String, List<String>> loader = new CacheLoader<String, List<String>>() {
            @Override
            public List<String> load(String s) throws Exception {
                return skillCacheList;
            }
        };

        LoadingCache<String, List<String>> skillGroupCache = CacheBuilder.newBuilder().build(loader);

        Map<String, List<String>> map = new HashMap<>();

        map.put("Programming", skillCacheList);

        skillGroupCache.putAll(map);

        when(guavaCacheInit.getLoadingCache()).thenReturn(skillGroupCache);

        List<String> expected = skillService.getSkillGroup("Programming");

        assertThat(2,is(expected.size()));


     }

    @Test
    public void getAllSkillGroups() throws ExecutionException {
        List<String> skillCacheList = getSkillList();

        CacheLoader<String, List<String>> loader = new CacheLoader<String, List<String>>() {
            @Override
            public List<String> load(String s) throws Exception {
                return skillCacheList;
            }
        };

        LoadingCache<String, List<String>> skillGroupCache = CacheBuilder.newBuilder().build(loader);

        Map<String, List<String>> map = new HashMap<>();

        map.put("Programming", skillCacheList);

        skillGroupCache.putAll(map);

        when(guavaCacheInit.getLoadingCache()).thenReturn(skillGroupCache);

        Map<String,List<String>> expected = skillService.getAllSkillGroups();

        assertThat(1,is(expected.size()));
        assertThat(2,is(expected.get("Programming").size()));


    }

    @Test
    public void getAllSkills() throws ExecutionException {
        List<SubSkill> subSkillList = getSubSkillList();

        CacheLoader<String, List<SubSkill>> loader = new CacheLoader<String, List<SubSkill>>() {
            @Override
            public List<SubSkill> load(String key) {
                return subSkillList;
            }
        };

        LoadingCache<String, List<SubSkill>> cache = CacheBuilder.newBuilder().build(loader);

        Map<String, List<SubSkill>> map = new HashMap<>();

        map.put("java", subSkillList);

        cache.putAll(map);

        when(guavaCacheInit.getSkillLoadingCache()).thenReturn(cache);

        Map<String,List<SubSkill>> expectedMap = skillService.getAllSkills();
        assertThat(2,is(expectedMap.get("java").size()));

    }

    private List<String> getSkillList(){
        List<String> toReturn = new ArrayList<>();
        toReturn.add("Java");
        toReturn.add("Python");
        return  toReturn;
    }

    public List<SubSkill> getSubSkillList(){
        List<SubSkill> toReturnList = new ArrayList<>();
        toReturnList.add(getSubSkill());
        toReturnList.add(getSubSkill1());
        return toReturnList;

    }

    public SubSkill getSubSkill(){
        return new SubSkill("1",
                "Basic Java",
                "Basic java Skills",
                "Java",
                "Programming Language",
                "ADM");
    }

    public SubSkill getSubSkill1(){
        return new SubSkill("2",
                "Generics",
                "Basic generics in Java",
                "Java",
                "skillGroup",
                "practice");
    }

}