package com.teksystems.skillportal.controller;

import com.mongodb.MongoException;
import com.teksystems.skillportal.domain.*;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.service.ReportService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ReportControllerTest {

    @Mock
    ReportService reportService;
    @Mock
    TokenValidationService tokenValidationService;
    @InjectMocks
    ReportController reportController;

    private MockMvc mockMvc;

    private Long DATECONSTANT = 1532677775148L;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(tokenValidationService.ExtractEmployeeId(any(HttpServletRequest.class))).thenReturn("101");
        this.mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    public void checkValidEmployeeTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(request,response)).thenReturn(true);

        boolean expected = reportController.checkValidEmployee(request,response);
        assertThat(true, is(expected));
    }
    @Test
    public void checkValidEmployeeTestUnAuthorized() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn(null);
        when(tokenValidationService.validateAdminRole(request,response)).thenReturn(true);

        boolean expected = reportController.checkValidEmployee(request,response);
        assertThat(false, is(expected));

    }
    @Test
    public void checkValidEmployeeTestNotAnAdmin() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(request,response)).thenReturn(false);

        boolean expected = reportController.checkValidEmployee(request,response);
        assertThat(false, is(expected));
    }
    @Test
    public void checkValidEmployeeTestIOException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(false);

        doThrow(IOException.class).when(response).sendError(anyInt(),anyString());

        boolean expected = reportController.checkValidEmployee(request,response);
        assertThat(false, is(expected));
    }

    @Test
    public void topNSubSkill() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.topNSubSkills(anyInt())).thenReturn(getSubSkillDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/report/reportskill?n=1")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].subSkill", is("Lambda")))
                .andExpect(jsonPath("$[1].subSkill", is("EBS")));

    }
    @Test
    public void topNSubSkillMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.topNSubSkills(anyInt())).thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/report/reportskill?n=2")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());

    }


    @Test
    public void topNSubSkillinLastXMonths() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.topNSubSkillsinLastXMonths(anyInt(), anyInt())).thenReturn(getSubSkillDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/report/reportskilltrend?n=1&x=2")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].subSkill", is("Lambda")))
                .andExpect(jsonPath("$[1].subSkill", is("EBS")));
    }
    @Test
    public void topNSubSkillinLastXMonthsMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.topNSubSkillsinLastXMonths(anyInt(), anyInt())).thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/report/reportskilltrend?n=1&x=2")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());
    }

    @Test
    public void topNSubSkillinLastXMonths1() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.skillsOfEmployee(anyString())).thenReturn(getEmployeeSkillDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/report/reportskillofemployee?employeeId=101")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].subSkill.subSkill", is("Lambda")))
                .andExpect(jsonPath("$[1].subSkill.subSkill", is("EBS")));
    }
    @Test
    public void topNSubSkillinLastXMonths1MongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.skillsOfEmployee(anyString())).thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/report/reportskillofemployee?employeeId=101")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());
    }

    @Test
    public void updatedByEmpSubSkillinLastXMonths() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.EmployeesWhoUpdatedSubSkillsinLastXMonths(anyLong(),anyLong()))
                .thenReturn(getSkillReportList());

        ResultActions resultAction = mockMvc.perform(
                get("/report/reportemployeeupdation?from=1042&to=1053")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].subSkillId", is("1")))
                .andExpect(jsonPath("$[1].subSkillId", is("2")));
    }
    @Test
    public void updatedByEmpSubSkillinLastXMonthsMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.EmployeesWhoUpdatedSubSkillsinLastXMonths(anyLong(),anyLong()))
                .thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/report/reportemployeeupdation?from=1042&to=1053")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());

    }

    @Test
    public void certificatesExipringInNextNmonths() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.CertificatesExipringInNextNmonths(anyLong(),anyLong()))
                .thenReturn(getEmployeeCertificationDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/report/reportcertificateexpiry?from=1042&to=1053")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].empId", is("101")));

    }
    @Test
    public void certificatesExipringInNextNmonthsMOngoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.CertificatesExipringInNextNmonths(anyLong(),anyLong()))
                .thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/report/reportcertificateexpiry?from=1042&to=1053")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());

    }

    @Test
    public void employeesWithASkill() throws Exception{
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.EmployeesWithASkill())
                .thenReturn(getEmployeeId());

        ResultActions resultAction = mockMvc.perform(
                get("/report/getemployees")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0]", is("abc@teksystems.com")));
    }
    @Test
    public void employeesWithASkillMongoException() throws Exception{
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(reportService.EmployeesWithASkill())
                .thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/report/getemployees")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());
    }

     SubSkillDomain getSubSkillDomain(){
        return new SubSkillDomain("1","Lambda","Lambda","AWS","Cloud","ADM",2);
    }

     SubSkillDomain getSubSkillDomain1(){
        return new SubSkillDomain("2","EBS","EBS","AWS","Cloud","ADM",1);
    }

     List<SubSkillDomain> getSubSkillDomainList(){
        List<SubSkillDomain> toReturnSubSkillDomainList = new ArrayList<>();
        toReturnSubSkillDomainList.add(getSubSkillDomain());
        toReturnSubSkillDomainList.add(getSubSkillDomain1());
        return toReturnSubSkillDomainList;

    }
     List<EmployeeSkillDomain> getEmployeeSkillDomainList(){
        List<EmployeeSkillDomain> toReturn = new ArrayList<>();
        toReturn.add( new EmployeeSkillDomain("101",getSubSkillDomain(),3,new Date()));
        toReturn.add(new EmployeeSkillDomain("101",getSubSkillDomain1(),2,new Date()));
        return toReturn;
    }
     SkillReport getSkillReport(){
        return new SkillReport("101",
                "1",
                5,
                2,
                new Date(DATECONSTANT),
                new Date(DATECONSTANT));
    }
    SkillReport getSkillReport1(){
        return new SkillReport("101",
                "2",
                3,
                4,
                new Date(DATECONSTANT),
                new Date(DATECONSTANT));
    }
    List<SkillReport> getSkillReportList(){
        List<SkillReport> toReturn = new ArrayList<>();
        toReturn.add(getSkillReport());
        toReturn.add(getSkillReport1());
        return toReturn;
    }

    private List<EmployeeCertificationDomain> getEmployeeCertificationDomainList() {

        List<EmployeeCertificationDomain> toReturn = new ArrayList<>();
        toReturn.add(getEmployeeCertificationDomain());
        return toReturn;
    }

    public CertificationDomain getCertificationDomain(){
        return new CertificationDomain(
                "1",
                "1",
                "AWS-Beginner",
                "AWS");
    }

    public EmployeeCertificationDomain getEmployeeCertificationDomain(){
        return new EmployeeCertificationDomain(
                "101",
                getCertificationDomain(),
                new Date(DATECONSTANT),
                new Date(DATECONSTANT),
                1,
                "www.amazon.com");

    }

    List<String> getEmployeeId(){
        List<String> toReturnEmpId = new ArrayList<>();
        toReturnEmpId.add("abc@teksystems.com");
        toReturnEmpId.add("def@teksystems.com");
        return toReturnEmpId;
    }
}