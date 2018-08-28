package com.teksystems.skillportal.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.SkillGroupService;
import com.teksystems.skillportal.service.SkillService;
import com.teksystems.skillportal.service.SubSkillService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;

import static org.hamcrest.core.Is.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.teksystems.skillportal.controller.SkillController;
import com.teksystems.skillportal.domain.SkillDomain;
import com.teksystems.skillportal.helper.SkillHelper;

import javax.servlet.http.HttpServletRequest;


@RunWith(SpringRunner.class)

public class SkillControllerTest {
 
	@Mock
    SkillHelper skillHelper;
	@Mock
    SkillGroupService skillGroupService;
	@Mock
    SubSkillService subSkillService;
	@Mock
    SkillService skillService;
    @Mock
    TokenValidationService tokenValidationService;
	
    @InjectMocks
    SkillController controllerUnderTest;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.initMocks(this);

        when(tokenValidationService.ExtractEmployeeId(Mockito.any(HttpServletRequest.class))).thenReturn("101");
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();

    }

    @Test
    public void testGetAllSkillGroups() throws  Exception{
        List<String> cloudsSkills = new ArrayList<>();
        cloudsSkills.add("AWS");
        cloudsSkills.add("Azure");
        List<String> programmingSkills = new ArrayList<>();
        programmingSkills.add("Java");
        programmingSkills.add("C Sharp");
        programmingSkills.add("Python");
        Map<String,List<String>> toReturn= new HashMap<>();
        toReturn.put("Cloud",cloudsSkills);
        toReturn.put("Programming",programmingSkills);

        given(skillGroupService.getAllSkillGroups()).willReturn(toReturn);

        ResultActions resultAction =mockMvc.perform(get("/skill/getallskillgroups")
                .header("Authorization", "empId:101"));
                resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("Cloud",hasSize(2)))
                .andExpect(jsonPath("Cloud[0]",is("AWS")))
                .andExpect(jsonPath("Cloud[1]",is("Azure")))
                .andExpect(jsonPath("Programming",hasSize(3)))
                .andExpect(jsonPath("Programming[0]",is("Java")))
                .andExpect(jsonPath("Programming[1]",is("C Sharp")))
                .andExpect(jsonPath("Programming[2]",is("Python")));


    }

    @Test
    public void testGetAllSkills() throws Exception{
        Map<String,List<SubSkill>> toReturn = new HashMap<>();
        List<String> keys = new ArrayList<>();
        SubSkill value1key1 = new SubSkill("24",
                    "Requirement maintenance",
                "Usage of tools like JIRA TFS (at least 2)",
                "Tools",
                "BPMG",
                "ADM");
        SubSkill value2key1 = new SubSkill("25",
                "Analytical representation",
                "Diagrams (e.g. Visio) mind map tools (e.g. Mind map) (at least 1)",
                "Tools",
                "BPMG",
                "ADM");
        List<SubSkill> forKey1 = new ArrayList<>();
         forKey1.add(value1key1);
         forKey1.add(value2key1);
        SubSkill value1Key2 = new SubSkill("28",
                "EC2",
             "EC2",
                    "AWS",
                "Cloud",
                 "ADM"
        );
        List<SubSkill> forKey2 = new ArrayList<>();
        forKey2.add(value1Key2);

        keys.add("BPMG_Tools");
        keys.add("Cloud_AWS");
        toReturn.put(keys.get(0),forKey1);
        toReturn.put(keys.get(1),forKey2);

        given(skillService.getAllSkills()).willReturn(toReturn);
        ResultActions resultAction =mockMvc.perform(get("/skill/getallskills")
                .header("Authorization", "empId:101"));
                resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("BPMG_Tools",hasSize(2)))
                .andExpect(jsonPath("BPMG_Tools[0].id",is("24")))
                .andExpect(jsonPath("BPMG_Tools[0].subSkill",is("Requirement maintenance")))
                .andExpect(jsonPath("BPMG_Tools[0].subSkillDesc",is("Usage of tools like JIRA TFS (at least 2)")))
                .andExpect(jsonPath("BPMG_Tools[0].skill",is("Tools")))
                .andExpect(jsonPath("BPMG_Tools[0].skillGroup",is("BPMG")))
                .andExpect(jsonPath("BPMG_Tools[0].practice",is("ADM")))

                .andExpect(jsonPath("BPMG_Tools[1].id",is("25")))
                .andExpect(jsonPath("BPMG_Tools[1].subSkill",is("Analytical representation")))
                .andExpect(jsonPath("BPMG_Tools[1].subSkillDesc",is("Diagrams (e.g. Visio) mind map tools (e.g. Mind map) (at least 1)")))
                .andExpect(jsonPath("BPMG_Tools[1].skill",is("Tools")))
                .andExpect(jsonPath("BPMG_Tools[1].skillGroup",is("BPMG")))
                .andExpect(jsonPath("BPMG_Tools[1].practice",is("ADM")))

                .andExpect(jsonPath("Cloud_AWS",hasSize(1)))
                .andExpect(jsonPath("Cloud_AWS[0].id",is("28")))
                .andExpect(jsonPath("Cloud_AWS[0].subSkill",is("EC2")))
                .andExpect(jsonPath("Cloud_AWS[0].subSkillDesc",is("EC2")))
                .andExpect(jsonPath("Cloud_AWS[0].skill",is("AWS")))
                .andExpect(jsonPath("Cloud_AWS[0].skillGroup",is("Cloud")))
                .andExpect(jsonPath("Cloud_AWS[0].practice",is("ADM")));

    }
    
    @Test
    public void testGetSkillGroup() throws Exception{
        List<String> toReturn = new ArrayList<>();
        toReturn.add("Analysis");
        toReturn.add("Certification");
        toReturn.add("Domain knowledge");
        toReturn.add("Requirement implementation");
        toReturn.add("Requirements Management");
        toReturn.add("Tools");

        given(skillService.getSkillGroup("BPMG")).willReturn(toReturn);

        mockMvc.perform(get("/skill/getskillgroup?skillGroup=BPMG")
                .header("Authorization", "empId:101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(6)))
                .andExpect(jsonPath("$[0]",is("Analysis")))
                .andExpect(jsonPath("$[1]",is("Certification")))
                .andExpect(jsonPath("$[2]",is("Domain knowledge")))
                .andExpect(jsonPath("$[3]",is("Requirement implementation")))
                .andExpect(jsonPath("$[4]",is("Requirements Management")))
                .andExpect(jsonPath("$[5]",is("Tools")));



    }
}

