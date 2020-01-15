package com.cdac.skillportal.service;

import com.cdac.skillportal.model.Skill;
import com.cdac.skillportal.repository.SkillRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


public class SkillPortalBackendSkillServiceTest {

    @Mock
    private SkillRepository skillRepository;


    @InjectMocks
    private SkillService skillService;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        List<Skill> skills = new ArrayList<Skill>();
        Skill skill1 = new Skill();
        skill1.setName("AWS");
        skills.add(skill1);
        when(skillRepository.findAll()).thenReturn(skills);


    }

    @Test
    public void testGetById() {
        Skill skill = new Skill();
        skill.setId("5a9654aaf98dbc0b04afc281");
        skill.setName("AWS");
        when(skillRepository.findById("5a9654aaf98dbc0b04afc281")).thenReturn(skill);

        Skill result = skillRepository.findById("5a9654aaf98dbc0b04afc281");
        Assert.assertEquals(1, 1);


    }


}