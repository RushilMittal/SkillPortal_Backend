package com.teksystems.skillportal.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.teksystems.skillportal.domain.EmployeeSkillPlaceholderDomain;
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
import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.service.EmployeeSkillService;
import javax.servlet.http.HttpServletRequest;


@RunWith(SpringRunner.class)

public class  EmployeeSkillControllerTest {

	@Mock
    EmployeeSkillService employeeSkillService;

	@Mock
    TokenValidationService tokenValidationService;

    @InjectMocks
    EmployeeSkillController controllerUnderTest;

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
    public void testGetSubSkillsBySkill() throws Exception{
        List<SubSkillDomain> subSkillDomains = Arrays.asList(new SubSkillDomain("1","Lambda","Lambda","AWS","Cloud","ADM",2),
                                                             new SubSkillDomain("2","EBS","EBS","AWS","Cloud","ADM",1)
                                                            );
        when(employeeSkillService.getAllUnassignedSubSkills("101","AWS")).thenReturn(subSkillDomains);
        ResultActions resultAction = mockMvc.perform(
                get("/skill/getSubSkillsBySkill?empId=101&skillName=AWS")
                .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
            .andExpect(jsonPath("$",hasSize(2)))
            .andExpect(jsonPath("$[0].subSkill",is("Lambda")))
            .andExpect(jsonPath("$[1].subSkill",is("EBS")));

    }
    @Test
	public void testAdd() throws Exception {


		doNothing().when(employeeSkillService).addNew("103", "4", 3);

		mockMvc.perform(post("/skill/add?empId=103&subSkillId=4&rating=3")
                .header("Authorization", "empId:80"))
                .andExpect(status().isOk());
	}

	@Test
    public void testGetEmployeeSkillPlaceholder() throws Exception{
        EmployeeSkillPlaceholderDomain a = new EmployeeSkillPlaceholderDomain(2,"Lambda",3, new int[]{0, 1, 1});
        when(employeeSkillService.getEmployeeSkillPlaceHolderDomain(anyString())).thenReturn(a);

         ResultActions resultActions = mockMvc.perform(get("/skill/getEmployeeSkillPlaceholder?empId=101")
                 .header("Authorization", "empId:101"));

                resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("numberOfSkillRated", is(2)))
                .andExpect(jsonPath("higestRatedSkill", is("Lambda")))
                .andExpect(jsonPath("highestRating", is(3)))
                .andExpect(jsonPath("lastUpdatedPeriod[0]", is(0)))
                .andExpect(jsonPath("lastUpdatedPeriod[1]", is(1)))
                .andExpect(jsonPath("lastUpdatedPeriod[2]", is(1)));
    }
    @Test
    public void testGetEmployeeSkills() throws Exception{
        SubSkillDomain temp = new SubSkillDomain("1","Lambda","Lambda","AWS","Cloud","ADM",2);
        SubSkillDomain temp2 = new SubSkillDomain("2","EBS","EBS","AWS","Cloud","ADM",1);

        List<EmployeeSkillDomain> employeeSkillDomains = Arrays.asList(
                new EmployeeSkillDomain("101",temp,3,new Date()),
                new EmployeeSkillDomain("101",temp2,2,new Date())

        );
        given(employeeSkillService.getAll("101")).willReturn(employeeSkillDomains);

        ResultActions resultActions =mockMvc.perform(get("/skill/getEmployeeSkills?empId=101")
                .header("Authorization", "empId:101"));

                resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].employeeId",is("101")))
                .andExpect(jsonPath("$[0].rating",is(3)))
                .andExpect(jsonPath("$[1].employeeId",is("101")))
                .andExpect(jsonPath("$[1].rating",is(2)));



    }



}
