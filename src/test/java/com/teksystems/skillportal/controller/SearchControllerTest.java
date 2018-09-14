package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.service.CertificationService;
import com.teksystems.skillportal.service.SearchService;
import com.teksystems.skillportal.service.TokenValidationService;
import com.teksystems.skillportal.service.TrainingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SearchControllerTest {

    @Mock
    TrainingService trainingService;
    @Mock
    SearchService searchService;
    @Mock
    CertificationService certificationService;
    @Mock
    TokenValidationService tokenValidationService;
    @InjectMocks
    SearchController searchController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn("101");
        this.mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    public void checkEmployeeId() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        boolean expected = searchController.checkEmployeeId(request, response);

        assertThat(true, is(expected));
    }

    @Test
    public void checkEmployeeIdUnAuthorized() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn(null);

        boolean expected = searchController.checkEmployeeId(request, response);

        assertThat(false, is(expected));
    }

    @Test(expected = IOException.class)
    public void checkEmployeeIdIOException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn(null);


        doThrow(IOException.class).when(response).sendError(anyInt(), anyString());
        searchController.checkEmployeeId(request, response);
        assertThat(500, is(response.getStatus()));

    }

    @Test
    public void searchSkill() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        when(searchService.searchSkill(anyString())).thenReturn(getSkillSearchList());

        ResultActions resultAction = mockMvc.perform(
                get("/skill/searchskill?searchTerm=AWS")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("AWS")))
                .andExpect(jsonPath("$[1]", is("AWS Cloud")));
    }

    @Test
    public void searchSkillInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        when(searchService.searchSkill(anyString())).thenReturn(getSkillSearchList());

        ResultActions resultAction = mockMvc.perform(
                get("/skill/searchskill?searchTerm=AWS")

        );
        resultAction.andExpect(status().isUnauthorized());


    }

    @Test
    public void getCertSearch() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        when(certificationService.searchCertItems(anyString())).thenReturn(getCertificateSearchList());

        ResultActions resultAction = mockMvc.perform(
                get("/skill/searchcertitems?searchTerm=AWS")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].skillId", is("1")));

    }

    @Test
    public void getCertSearchInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        when(certificationService.searchCertItems(anyString())).thenReturn(getCertificateSearchList());

        ResultActions resultAction = mockMvc.perform(
                get("/skill/searchcertitems?searchTerm=AWS")

        );
        resultAction.andExpect(status().isUnauthorized());


    }

    @Test
    public void searchTraining() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        when(trainingService.searchTraining(anyString())).thenReturn(getTrainingSearchList());

        ResultActions resultAction = mockMvc.perform(
                get("/skill/searchtraining?searchTerm=AWS")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Basic Python")));

    }

    @Test
    public void searchTrainingInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        when(trainingService.searchTraining(anyString())).thenReturn(getTrainingSearchList());

        ResultActions resultAction = mockMvc.perform(
                get("/skill/searchtraining?searchTerm=AWS")

        );
        resultAction.andExpect(status().isUnauthorized());

    }

    List<String> getSkillSearchList() {
        List<String> searchresult = new ArrayList<>();
        searchresult.add("AWS");
        searchresult.add("AWS Cloud");
        return searchresult;
    }

    List<CertificationDomain> getCertificateSearchList() {
        List<CertificationDomain> searchresult = new ArrayList<>();
        searchresult.add(getCertificationDomain());
        return searchresult;
    }

    CertificationDomain getCertificationDomain() {
        return new CertificationDomain("1", "Basic AWS Certification", "AWS");
    }

    List<Training> getTrainingSearchList() {
        List<Training> searchresult = new ArrayList<>();
        searchresult.add(getTraining());
        return searchresult;
    }

    Training getTraining() {
        return new Training("Basic Python",
                "Training Room 1",
                20, "Technical",
                "Basic Python Training",
                "John",
                "john@teksystems.com");
    }
}