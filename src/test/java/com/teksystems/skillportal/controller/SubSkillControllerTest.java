package com.teksystems.skillportal.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.teksystems.skillportal.model.SubSkill;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.teksystems.skillportal.controller.SubSkillController;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.service.SubSkillService;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)

public class SubSkillControllerTest {
	
	@Mock
    SubSkillService subSkillService;

    @Mock
    TokenValidationService tokenValidationService;

    @InjectMocks
    SubSkillController controllerUnderTest;

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
    public void testGetAllSubSkillsOfEmployee() throws Exception{
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
        keys.add("BPMG_Tools");
        toReturn.put(keys.get(0),forKey1);

        given(subSkillService.getAllSubSkillsOfEmployee()).willReturn(toReturn);
        mockMvc.perform(get("/skill/getallsubskill?skillName=BPMG_Tools")
                .header("Authorization", "empId:101"))
                .andExpect(status().isOk())
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
                .andExpect(jsonPath("BPMG_Tools[1].practice",is("ADM")));

    }


}
