package com.teksystems.skillportal.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.service.EmployeeCertificationService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeCertificationControllerTest {
    @Mock
    TokenValidationService tokenValidationService;
    @Mock
    EmployeeCertificationService employeeCertificationService;

    @InjectMocks
    EmployeeCertificationController employeeCertificationController;

    private Long DATECONSTANT = 1532677775148L;

    private MockMvc mockMvc;
    public static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(tokenValidationService.ExtractEmployeeId(Mockito.any(HttpServletRequest.class))).thenReturn("101");
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeCertificationController).build();
    }

    @Test
    public void getById() throws Exception {
        when(employeeCertificationService.getEmployeeCertificationByEmployeeId(anyString())).thenReturn(getEmployeeCertificationDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/certifications/getcertifications")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
            .andExpect(jsonPath("$",hasSize(1)))
            .andExpect(jsonPath("$[0].empId",is("101")));
    }

    @Test
    public void addCertification() throws Exception {
        doNothing().when(employeeCertificationService).addNew("101","1",
                new Date(DATECONSTANT),new Date(DATECONSTANT),1,"www.datacamp.com");

        mockMvc.perform(post("/certifications/addCertification?certificationId=1" +
                "&certificationDateString=24/12/2018&certificationValidityDateString=24/12/2019&certificationNumber=1" +
                "&certificationUrl=www.datacamp.com")
                .header("Authorization", "empId:101"))
                .andExpect(status().isOk());

    }


    @Test
    public void addNewCertificate() throws Exception {
        doNothing().when(employeeCertificationService).addNewCertificate(getEmployeeCertificationDomain());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        mockMvc.perform(post("/certifications/addcertificate")
                .header("Authorization", "empId:101")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(getEmployeeCertificationDomain()))
                )
        .andExpect(status().isOk());


    }

    @Test
    public void getTopTwoEmployeeCertificationYearById() {
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

}