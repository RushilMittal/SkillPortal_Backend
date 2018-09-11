package com.teksystems.skillportal.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.SkillGroupService;
import com.teksystems.skillportal.service.SkillService;
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

import javax.servlet.http.HttpServletRequest;


@RunWith(SpringRunner.class)

public class SkillControllerTest {
 

	@Mock
    SkillGroupService skillGroupService;
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

        given(skillGroupService.getAllSkillGroups()).willReturn(getAllSkillGroupMap());

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
    public void testGetAllSkillGroupsUnAuthorized() throws  Exception{

        given(skillGroupService.getAllSkillGroups()).willReturn(getAllSkillGroupMap());

        ResultActions resultAction =mockMvc.perform(get("/skill/getallskillgroups")
               );
        resultAction.andExpect(status().isUnauthorized());


    }


    @Test
    public void testGetAllSkills() throws Exception{

        given(skillService.getAllSkills()).willReturn(getSubSkillMap());
        ResultActions resultAction =mockMvc.perform(get("/skill/getallskills")
                .header("Authorization", "empId:101"));
                resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$.BPMG_Tools",hasSize(2)))
                .andExpect(jsonPath("$.BPMG_Tools[0].id",is("24")))
                .andExpect(jsonPath("$.BPMG_Tools[0].subSkill",is("Requirement maintenance")))
                .andExpect(jsonPath("$.BPMG_Tools[0].modelSubSkillDesc",is("Usage of tools like JIRA TFS (at least 2)")))
                .andExpect(jsonPath("$.BPMG_Tools[0].modelSkill",is("Tools")))
                .andExpect(jsonPath("$.BPMG_Tools[0].modelSkillGroup",is("BPMG")))
                .andExpect(jsonPath("$.BPMG_Tools[0].modelPractice",is("ADM")))

                .andExpect(jsonPath("$.BPMG_Tools[1].id",is("25")))
                .andExpect(jsonPath("$.BPMG_Tools[1].subSkill",is("Analytical representation")))
                .andExpect(jsonPath("$.BPMG_Tools[1].modelSubSkillDesc",is("Diagrams (e.g. Visio) mind map tools (e.g. Mind map) (at least 1)")))
                .andExpect(jsonPath("$.BPMG_Tools[1].modelSkill",is("Tools")))
                .andExpect(jsonPath("$.BPMG_Tools[1].modelSkillGroup",is("BPMG")))
                .andExpect(jsonPath("$.BPMG_Tools[1].modelPractice",is("ADM")))

                .andExpect(jsonPath("$.Cloud_AWS",hasSize(1)))
                .andExpect(jsonPath("$.Cloud_AWS[0].id",is("28")))
                .andExpect(jsonPath("$.Cloud_AWS[0].subSkill",is("EC2")))
                .andExpect(jsonPath("$.Cloud_AWS[0].modelSubSkillDesc",is("EC2")))
                .andExpect(jsonPath("$.Cloud_AWS[0].modelSkill",is("AWS")))
                .andExpect(jsonPath("$.Cloud_AWS[0].modelSkillGroup",is("Cloud")))
                .andExpect(jsonPath("$.Cloud_AWS[0].modelPractice",is("ADM")));


    }
    @Test
    public void testGetAllSkillsUnAuthorized() throws Exception{

        given(skillService.getAllSkills()).willReturn(getSubSkillMap());
        ResultActions resultAction =mockMvc.perform(get("/skill/getallskills")
                );
        resultAction.andExpect(status().isUnauthorized());



    }


    @Test
    public void testGetSkillGroup() throws Exception{

        given(skillService.getSkillGroup(anyString())).willReturn(getBPMGSkills());

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
    @Test
    public void testGetSkillGroupUnAuthorized() throws Exception{

        given(skillService.getSkillGroup(anyString())).willReturn(getBPMGSkills());

        mockMvc.perform(get("/skill/getskillgroup?skillGroup=BPMG")
                )
                .andExpect(status().isUnauthorized());
    }

    List<String> getBPMGSkills(){
        List<String> toReturn = new ArrayList<>();
        toReturn.add("Analysis");
        toReturn.add("Certification");
        toReturn.add("Domain knowledge");
        toReturn.add("Requirement implementation");
        toReturn.add("Requirements Management");
        toReturn.add("Tools");
        return toReturn;
    }

    Map<String,List<String>> getAllSkillGroupMap(){


        Map<String,List<String>> toReturn= new HashMap<>();
        toReturn.put("Cloud",getCloudSkills());
        toReturn.put("Programming",getProgrammingSkills());
        return toReturn;
    }

    List<String> getCloudSkills(){
        List<String> cloudsSkills = new ArrayList<>();
        cloudsSkills.add("AWS");
        cloudsSkills.add("Azure");
        return cloudsSkills;

    }
    List<String> getProgrammingSkills(){
        List<String> programmingSkills = new ArrayList<>();
        programmingSkills.add("Java");
        programmingSkills.add("C Sharp");
        programmingSkills.add("Python");
        return programmingSkills;
    }
    SubSkill getSubSkill(){
        return new SubSkill("24",
                "Requirement maintenance",
                "Usage of tools like JIRA TFS (at least 2)",
                "Tools",
                "BPMG",
                "ADM");
    }
    SubSkill getSubSkill1(){
        return new SubSkill("25",
                "Analytical representation",
                "Diagrams (e.g. Visio) mind map tools (e.g. Mind map) (at least 1)",
                "Tools",
                "BPMG",
                "ADM");
    }
    List<SubSkill> getBPMGSubSkillList(){
        List<SubSkill> toReturn= new ArrayList<>();
        toReturn.add(getSubSkill());
        toReturn.add(getSubSkill1());
        return toReturn;
    }
    SubSkill getCloudSubSkill(){
       return  new SubSkill("28",
                "EC2",
                "EC2",
                "AWS",
                "Cloud",
                "ADM"
        );
    }
    List<SubSkill> getCloudSubSkillList(){
        List<SubSkill> toReturn = new ArrayList<>();
        toReturn.add(getCloudSubSkill());
        return toReturn;
    }

    Map<String,List<SubSkill>> getSubSkillMap(){
        Map<String,List<SubSkill>>  toReturnMap = new HashMap<>();
        List<String> keys = new ArrayList<>();


        keys.add("BPMG_Tools");
        keys.add("Cloud_AWS");
        toReturnMap.put(keys.get(0),getBPMGSubSkillList());
        toReturnMap.put(keys.get(1),getCloudSubSkillList());
        return toReturnMap;
    }
}

