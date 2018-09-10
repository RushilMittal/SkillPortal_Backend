package com.teksystems.skillportal.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.init.GuavaCacheInit;
import com.teksystems.skillportal.model.SubSkill;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SearchServiceTest {

    @Mock
    GuavaCacheInit guavaCacheInit;
    @InjectMocks
    SearchService searchService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        List<SubSkill> subSkillList = getAllSubSkill();
        CacheLoader<String, List<SubSkill>> subSkillLoader = new CacheLoader<String, List<SubSkill>>() {
            @Override
            public List<SubSkill> load(String key) {
                return subSkillList;
            }
        };
        LoadingCache<String, List<SubSkill>> cache = CacheBuilder.newBuilder().build(subSkillLoader);
        Map<String, List<SubSkill>> subSkillMap = new HashMap<>();
        subSkillMap.put("java", subSkillList);
        cache.putAll(subSkillMap);
        when(guavaCacheInit.getSkillLoadingCache()).thenReturn(cache);
    }

    @Test
    public void searchSkill() {
        List<String> expected = searchService.searchSkill("Java");

        assertThat(1, is(expected.size()));



    }

    List<SubSkill> getAllSubSkill(){
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