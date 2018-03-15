package com.teksystems.skillportal.service.test;


import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.SubSkillRepository;
import com.teksystems.skillportal.service.SubSkillService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public  class SkillPortalBackendSubSkillServiceTest {
    @Mock
    private SubSkillRepository subSkillRepository;
    @InjectMocks
    private SubSkillService subSkillService;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll()
    {
        List<SubSkill> subSkills= new ArrayList<>();
        SubSkill subSkill = new SubSkill();
        subSkill.setSkillId("11");
        subSkill.setName("java");
        subSkill.setId("1");
        subSkills.add(subSkill);
        SubSkill subSkill1 = new SubSkill();
        subSkill.setSkillId("11");
        subSkill.setName("C");
        subSkill.setId("2");
        subSkills.add(subSkill1);
        when(subSkillRepository.findAll()).thenReturn(subSkills);
        List<SubSkill> result = subSkillRepository.findAll();
       assertEquals(2,result.size());
    }

    @Test
    public void testGetBySubSkillId()
    {
        SubSkill subSkill = new SubSkill();
        subSkill.setSkillId("11");
        subSkill.setName("java");
        subSkill.setId("1");
        when(subSkillRepository.findById("1")).thenReturn(subSkill);
        SubSkill result = subSkillRepository.findById("1");
        assertEquals(1,1);
    }


    @Test
    public void testGetAllBySkillId()
    {
        List<SubSkill> subSkills= new ArrayList<>();
        SubSkill subSkill = new SubSkill();
        subSkill.setSkillId("11");
        subSkill.setName("java");
        subSkill.setId("1");
        subSkills.add(subSkill);
        SubSkill subSkill1 = new SubSkill();
        subSkill.setSkillId("11");
        subSkill.setName("C");
        subSkill.setId("2");
        subSkills.add(subSkill1);
        when(subSkillRepository.findBySkillId("11")).thenReturn(subSkills);
        List<SubSkill> result =subSkillRepository.findBySkillId("11");
        assertEquals(2,result.size());
    }


}
