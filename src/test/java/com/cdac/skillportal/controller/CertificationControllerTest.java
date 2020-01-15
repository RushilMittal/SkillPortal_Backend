package com.cdac.skillportal.controller;

import com.cdac.skillportal.domain.CertificationDomain;
import com.cdac.skillportal.service.AdminService;
import com.cdac.skillportal.service.CertificationService;
import com.cdac.skillportal.service.TokenValidationService;
import com.mongodb.MongoException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CertificationControllerTest {

    @Mock
    CertificationService certificationService;

    @Mock
    TokenValidationService tokenValidationService;

    @Mock
    AdminService adminService;

    @InjectMocks
    CertificationController certificationController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(tokenValidationService.extractEmployeeId(Mockito.any(HttpServletRequest.class))).thenReturn("101");
        this.mockMvc = MockMvcBuilders.standaloneSetup(certificationController).build();
    }

    @Test
    public void getAvailableCertifications() throws Exception {

        when(certificationService.getAllCertifications()).thenReturn(getCertificationDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/certifications/all")
                        .header("Authorization", "empId:101")
        );


        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].certificationName", is("Python")));
    }

    @Test
    public void getAvailableCertificationsUnAuthorized() throws Exception {

        when(certificationService.getAllCertifications()).thenReturn(getCertificationDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/certifications/all")

        );


        resultAction.andExpect(status().isUnauthorized());

    }


    @Test
    public void getAvailableCertificationsMongoException() throws Exception {


        doThrow(MongoException.class).when(certificationService).getAllCertifications();

        ResultActions resultAction = mockMvc.perform(
                get("/certifications/all")
                        .header("Authorization", "empId:101")

        );


        resultAction.andExpect(status().isInternalServerError());

    }


    @Test
    public void postNewCert() throws Exception {
        doNothing().when(adminService).postNewCertification(getCertificationDomain());

        mockMvc.perform(post("/certifications/addnewCert?id=1&certificationName=Python&" +
                "institution=DataCamp&skillId=4")
                .header("Authorization", "empId:80"))
                .andExpect(status().isOk());
    }

    @Test
    public void postNewCertUnAuthorized() throws Exception {
        doNothing().when(adminService).postNewCertification(getCertificationDomain());

        mockMvc.perform(post("/certifications/addnewCert?id=1&certificationName=Python&" +
                "institution=DataCamp&skillId=4")
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void postNewCertMongoException() throws Exception {


        doThrow(MongoException.class).when(adminService).postNewCertification(any(CertificationDomain.class));

        mockMvc.perform(post("/certifications/addnewCert?id=1&certificationName=Python&" +
                "institution=DataCamp&skillId=4")
                .header("Authorization", "empId:80"))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void addNewEmployeeCertificate() throws Exception {
        doNothing().when(adminService).postNewCertification(getCertificationDomain());

        mockMvc.perform(post("/certifications/addnewemployeecertificate?certificationName=Python&" +
                "institution=DataCamp&skillId=4")
                .header("Authorization", "empId:80"))
                .andExpect(status().isOk());
    }

    @Test
    public void addNewEmployeeCertificateUnAuthorized() throws Exception {
        doNothing().when(adminService).postNewCertification(getCertificationDomain());

        mockMvc.perform(post("/certifications/addnewemployeecertificate?certificationName=Python&" +
                "institution=DataCamp&skillId=4")
        )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addNewEmployeeCertificateMongoException() throws Exception {

        doThrow(MongoException.class).when(adminService).postNewCertification(any(CertificationDomain.class));
        mockMvc.perform(post("/certifications/addnewemployeecertificate?certificationName=Python&" +
                "institution=DataCamp&skillId=4")
                .header("Authorization", "empId:80"))
                .andExpect(status().isInternalServerError());
    }


    List<CertificationDomain> getCertificationDomainList() {
        List<CertificationDomain> toReturn = new ArrayList<>();
        toReturn.add(getCertificationDomain());
        return toReturn;
    }

    CertificationDomain getCertificationDomain() {
        return new CertificationDomain("1", "1", "Python", "DataCamp");
    }
}