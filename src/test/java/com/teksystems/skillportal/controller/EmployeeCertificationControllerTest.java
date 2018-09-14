package com.teksystems.skillportal.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationPlaceholderDomain;
import com.teksystems.skillportal.service.EmployeeCertificationService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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

        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn("101");
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
    public void getByIdUnAuthorized() throws Exception {
        when(employeeCertificationService.getEmployeeCertificationByEmployeeId(anyString())).thenReturn(getEmployeeCertificationDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/certifications/getcertifications")

        );
        resultAction.andExpect(status().isUnauthorized());

    }
    @Test
    public void getByIdMongoException() throws Exception {
        when(employeeCertificationService.getEmployeeCertificationByEmployeeId(anyString())).thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/certifications/getcertifications")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());
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
    public void addCertificationUnAuthorized() throws Exception {
        doNothing().when(employeeCertificationService).addNew("101","1",
                new Date(DATECONSTANT),new Date(DATECONSTANT),1,"www.datacamp.com");

        mockMvc.perform(post("/certifications/addCertification?certificationId=1" +
                "&certificationDateString=24/12/2018&certificationValidityDateString=24/12/2019&certificationNumber=1" +
                "&certificationUrl=www.datacamp.com")
                )
                .andExpect(status().isUnauthorized());

    }
    @Test
    public void addCertificationMongoException() throws Exception {

        doThrow(MongoException.class).when(employeeCertificationService).addNew(anyString(),anyString(), any(Date.class)
                ,any(Date.class),anyInt(),anyString());
        mockMvc.perform(post("/certifications/addCertification?certificationId=1" +
                "&certificationDateString=24/12/2018&certificationValidityDateString=24/12/2019&certificationNumber=1" +
                "&certificationUrl=www.datacamp.com")
                .header("Authorization", "empId:101"))
                .andExpect(status().isInternalServerError());

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
    public void addNewCertificateUnAuthorized() throws Exception {
        doNothing().when(employeeCertificationService).addNewCertificate(getEmployeeCertificationDomain());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        mockMvc.perform(post("/certifications/addcertificate")

                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(getEmployeeCertificationDomain()))
        )
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void addNewCertificateMongoException() throws Exception {

        doThrow(MongoException.class).when(employeeCertificationService).addNewCertificate(any(EmployeeCertificationDomain.class));
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        mockMvc.perform(post("/certifications/addcertificate")
                .header("Authorization", "empId:101")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(getEmployeeCertificationDomain()))
        )
                .andExpect(status().isInternalServerError());
    }



    @Test
    public void getTopTwoEmployeeCertificationYearById() throws Exception {
        when(employeeCertificationService.getEmployeeCertificationPlaceholderById(anyString()))
                .thenReturn(getEmployeeCertificationPlaceholderDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/certifications/getcertificationplaceholder")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].certificationName",is("RedHat Basics")));
    }
    @Test
    public void getTopTwoEmployeeCertificationYearByIdUnAuthrized() throws Exception {
        when(employeeCertificationService.getEmployeeCertificationPlaceholderById(anyString()))
                .thenReturn(getEmployeeCertificationPlaceholderDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/certifications/getcertificationplaceholder")

        );
        resultAction.andExpect(status().isUnauthorized());

    }
    @Test
    public void getTopTwoEmployeeCertificationYearByIdMongoException() throws Exception {
        when(employeeCertificationService.getEmployeeCertificationPlaceholderById(anyString()))
                .thenThrow(MongoException.class);


        ResultActions resultAction = mockMvc.perform(
                get("/certifications/getcertificationplaceholder")
                        .header("Authorization", "empId:101")

        );
        resultAction.andExpect(status().isInternalServerError());

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

    public EmployeeCertificationPlaceholderDomain getEmployeeCertificationPlaceholderDomain(){
        return new EmployeeCertificationPlaceholderDomain("RedHat Basics", "2018");
    }

    public EmployeeCertificationPlaceholderDomain getEmployeeCertificationPlaceholderDomain1(){
        return new EmployeeCertificationPlaceholderDomain("Linux Basics", "2017");
    }
    public List<EmployeeCertificationPlaceholderDomain> getEmployeeCertificationPlaceholderDomainList(){
        List<EmployeeCertificationPlaceholderDomain> toReturn= new ArrayList<>();
        toReturn.add(getEmployeeCertificationPlaceholderDomain());
        toReturn.add(getEmployeeCertificationPlaceholderDomain1());
        return toReturn;
    }


}