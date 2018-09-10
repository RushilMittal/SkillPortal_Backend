package com.teksystems.skillportal.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.init.GuavaCacheInit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class SkillGroupServiceTest {

    @Mock
    GuavaCacheInit guavaCacheInit;
    @InjectMocks
    SkillGroupService skillGroupService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
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
    }

    @Test
    public void getAllSkillGroups() {
        Map<String,List<String>> expected = skillGroupService.getAllSkillGroups();

        assertThat(1,is(expected.size()));
        assertThat(true, is(expected.containsKey("Programming")));


    }

    @Test
    public void getAllSkillsBySkillGroup() {
        List<String> expected = skillGroupService.getAllSkillsBySkillGroup("Programming");
        assertThat("Java",is(expected.get(0)));
        assertThat("Python", is(expected.get(1)));
    }


    private List<String> getSkillList(){
        List<String> toReturn = new ArrayList<>();
        toReturn.add("Java");
        toReturn.add("Python");
        return  toReturn;
    }
}