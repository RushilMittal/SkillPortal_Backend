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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class SubSkillServiceTest {

    @Mock
    GuavaCacheInit guavaCacheInit;

    @InjectMocks
    SubSkillService subSkillService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllSubSkillsOfEmployee() throws ExecutionException {

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
        Map<String, List<SubSkill>> expectedMap = subSkillService.getAllSubSkillsOfEmployee();
        assertThat(2, is(expectedMap.get("java").size()));
    }

    public List<SubSkill> getSubSkillList() {
        List<SubSkill> toReturnList = new ArrayList<>();
        toReturnList.add(getSubSkill());
        toReturnList.add(getSubSkill1());
        return toReturnList;

    }

    public SubSkill getSubSkill() {
        return new SubSkill("1",
                "Basic Java",
                "Basic java Skills",
                "Java",
                "Programming Language",
                "ADM");
    }

    public SubSkill getSubSkill1() {
        return new SubSkill("2",
                "Generics",
                "Basic generics in Java",
                "Java",
                "skillGroup",
                "practice");
    }

}