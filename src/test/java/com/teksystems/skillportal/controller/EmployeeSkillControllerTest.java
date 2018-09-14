package com.teksystems.skillportal.controller;

import com.mongodb.MongoException;
import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.EmployeeSkillPlaceholderDomain;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.service.EmployeeSkillService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(value = SpringRunner.class)


public class EmployeeSkillControllerTest {

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

        when(tokenValidationService.extractEmployeeId(Mockito.any(HttpServletRequest.class))).thenReturn("101");
        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();

    }


    @Test
    public void testGetSubSkillsBySkill() throws Exception {

        when(employeeSkillService.getAllUnassignedSubSkills(anyString(), anyString())).thenReturn(getSubSkillDomainList());
        ResultActions resultAction = mockMvc.perform(
                get("/skill/getSubSkillsBySkill?empId=101&skillName=AWS")
                        .header("Authorization", "empId:101")
        );


        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].subSkill", is("Lambda")))
                .andExpect(jsonPath("$[1].subSkill", is("EBS")));

    }

    @Test
    public void testGetSubSkillsBySkillUnAuthorized() throws Exception {

        ResultActions resultAction = mockMvc.perform(
                get("/skill/getSubSkillsBySkill?empId=101&skillName=AWS")

        );
        resultAction.andExpect(status().isUnauthorized());

    }

    @Test()
    public void testGetSubSkillsBySkillExceptions() throws Exception {

        when(employeeSkillService.getAllUnassignedSubSkills(anyString(), anyString())).thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/skill/getSubSkillsBySkill?empId=101&skillName=AWS")
                        .header("Authorization", "empId:101")
        );


        resultAction.andExpect(status().isInternalServerError());

    }

    @Test
    public void testAdd() throws Exception {


        doNothing().when(employeeSkillService).addNew(anyString(), anyString(), anyInt());

        mockMvc.perform(post("/skill/add?empId=103&subSkillId=4&rating=3")
                .header("Authorization", "empId:80"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddUnAuthorized() throws Exception {


        doNothing().when(employeeSkillService).addNew(anyString(), anyString(), anyInt());

        mockMvc.perform(post("/skill/add?empId=103&subSkillId=4&rating=3")
        )
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void testAddInvalidRatingGreaterThan5() throws Exception {


        doNothing().when(employeeSkillService).addNew(anyString(), anyString(), anyInt());

        mockMvc.perform(post("/skill/add?empId=103&subSkillId=4&rating=6")
                .header("Authorization", "empId:80"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void testAddInvalidRatingLessThan0() throws Exception {


        doNothing().when(employeeSkillService).addNew(anyString(), anyString(), anyInt());

        mockMvc.perform(post("/skill/add?empId=103&subSkillId=4&rating=-1")
                .header("Authorization", "empId:80"))
                .andExpect(status().isNotAcceptable());
    }


    @Test
    public void testAddMongoException() throws Exception {


        doThrow(MongoException.class).when(employeeSkillService).addNew(anyString(), anyString(), anyInt());

        mockMvc.perform(post("/skill/add?empId=103&subSkillId=4&rating=5")
                .header("Authorization", "empId:80"))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void testGetEmployeeSkillPlaceholder() throws Exception {

        when(employeeSkillService.getEmployeeSkillPlaceHolderDomain(anyString())).thenReturn(getEmployeeSkillPlaceholderDomain());

        ResultActions resultActions = mockMvc.perform(get("/skill/getEmployeeSkillPlaceholder")
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
    public void testGetEmployeeSkillPlaceholderUnAuthorized() throws Exception {


        when(employeeSkillService.getEmployeeSkillPlaceHolderDomain(anyString())).thenReturn(getEmployeeSkillPlaceholderDomain());

        ResultActions resultActions = mockMvc.perform(get("/skill/getEmployeeSkillPlaceholder")
        );

        resultActions.andExpect(status().isUnauthorized());

    }


    @Test
    public void testGetEmployeeSkillPlaceholderMongoException() throws Exception {


        doThrow(MongoException.class).when(employeeSkillService).getEmployeeSkillPlaceHolderDomain(anyString());

        ResultActions resultActions = mockMvc.perform(get("/skill/getEmployeeSkillPlaceholder")
                .header("Authorization", "empId:101"));

        resultActions.andExpect(status().isInternalServerError());

    }


    @Test
    public void testGetEmployeeSkills() throws Exception {

        given(employeeSkillService.getAll(anyString())).willReturn(getEmployeeSkillDomainList());

        ResultActions resultActions = mockMvc.perform(get("/skill/getEmployeeSkills")
                .header("Authorization", "empId:101"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].employeeId", is("101")))
                .andExpect(jsonPath("$[0].rating", is(3)))
                .andExpect(jsonPath("$[1].employeeId", is("101")))
                .andExpect(jsonPath("$[1].rating", is(2)));

    }


    @Test
    public void testGetEmployeeSkillsUnAuthorized() throws Exception {

        given(employeeSkillService.getAll(anyString())).willReturn(getEmployeeSkillDomainList());

        ResultActions resultActions = mockMvc.perform(get("/skill/getEmployeeSkills")
        );

        resultActions.andExpect(status().isUnauthorized());

    }

    @Test
    public void testGetEmployeeSkillsEmployeeIdNotFound() throws Exception {

        when(tokenValidationService.extractEmployeeId(Mockito.any(HttpServletRequest.class))).thenReturn(null);
        given(employeeSkillService.getAll(anyString())).willReturn(getEmployeeSkillDomainList());

        ResultActions resultActions = mockMvc.perform(get("/skill/getEmployeeSkills")
                .header("Authorization", "sample:101")
        );

        resultActions.andExpect(status().isBadRequest());

    }

    @Test
    public void testGetEmployeeSkillsMongoException() throws Exception {


        doThrow(MongoException.class).when(employeeSkillService).getAll(anyString());
        ResultActions resultActions = mockMvc.perform(get("/skill/getEmployeeSkills")
                .header("Authorization", "sample:101")
        );

        resultActions.andExpect(status().isInternalServerError());

    }


    public SubSkillDomain getSubSkillDomain() {
        return new SubSkillDomain("1", "Lambda", "Lambda", "AWS", "Cloud", "ADM", 2);
    }

    public SubSkillDomain getSubSkillDomain1() {
        return new SubSkillDomain("2", "EBS", "EBS", "AWS", "Cloud", "ADM", 1);
    }

    public List<EmployeeSkillDomain> getEmployeeSkillDomainList() {
        List<EmployeeSkillDomain> toReturn = new ArrayList<>();
        toReturn.add(new EmployeeSkillDomain("101", getSubSkillDomain(), 3, new Date()));
        toReturn.add(new EmployeeSkillDomain("101", getSubSkillDomain1(), 2, new Date()));
        return toReturn;
    }

    public EmployeeSkillPlaceholderDomain getEmployeeSkillPlaceholderDomain() {
        return new EmployeeSkillPlaceholderDomain(2, "Lambda", 3, new int[]{0, 1, 1});
    }

    public List<SubSkillDomain> getSubSkillDomainList() {
        List<SubSkillDomain> toReturnSubSkillDomainList = new ArrayList<>();
        toReturnSubSkillDomainList.add(getSubSkillDomain());
        toReturnSubSkillDomainList.add(getSubSkillDomain1());
        return toReturnSubSkillDomainList;

    }
}
