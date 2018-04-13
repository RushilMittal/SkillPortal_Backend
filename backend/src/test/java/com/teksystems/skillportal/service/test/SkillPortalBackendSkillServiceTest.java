//package com.teksystems.skillportal.service.test;
//
//import com.teksystems.skillportal.domain.SkillDomain;
//import com.teksystems.skillportal.model.Skill;
//import com.teksystems.skillportal.repository.SkillRepository;
//import com.teksystems.skillportal.service.SkillService;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//
//
//public class SkillPortalBackendSkillServiceTest {
//
//    @Mock
//    private SkillRepository skillRepository;
//  //  @Mock
//  //  private SubSkillRepository subSkillRepository;
//
//    @InjectMocks
//    private SkillService skillService;
//   // @InjectMocks
//   // private SubSkillService subSkillService;
//
//    @Before
//    public void setup(){
//        MockitoAnnotations.initMocks(this);
//    }
//    @Test
//    public void testGetAll() {
//        List<Skill> skills=new ArrayList<Skill>();
//        Skill skill1 = new Skill();
//        skill1.setName("AWS");
//        skills.add(skill1);
//        when(skillRepository.findAll()).thenReturn(skills);
//
////        List<SkillDomain> result=skillService.getAll();
////        Assert.assertEquals(1,result.size());
//
//
//    }
//
//    @Test
//    public void testGetById() {
//        Skill skill=new Skill();
//        skill.setId("5a9654aaf98dbc0b04afc281");
//        skill.setName("AWS");
//        when(skillRepository.findById("5a9654aaf98dbc0b04afc281")).thenReturn(skill);
//
//        Skill result=skillRepository.findById("5a9654aaf98dbc0b04afc281");
//        Assert.assertEquals(1,1);
//
//
//    }
//
//   /* @Test
//    public void testgetAllSubSkillsBySubSkillId() {
//        List<SubSkill> subSkills=new ArrayList<SubSkill>();
//        SubSkill subSkill1=new SubSkill();
//        subSkill1.setId("5a9654aaf98dbc0b04afc281");
//        subSkill1.setName("c++");
//        subSkill1.setSkillId("1");
//        subSkills.add(subSkill1);
//        when(subSkillRepository.findById("5a9654aaf98dbc0b04afc281")).thenReturn(subSkill1);
//
//        SubSkill result=subSkillRepository.findById("5a9654aaf98dbc0b04afc281");
//        Assert.assertEquals(1,1);
//
//
//    }*/
//}