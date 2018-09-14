package com.teksystems.skillportal.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.teksystems.skillportal.domain.TrainingDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.model.TrainingSession;
import com.teksystems.skillportal.service.TokenValidationService;
import com.teksystems.skillportal.service.TrainingService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TrainingControllerTest {

    @Mock
    TrainingService trainingService;
    @Mock
    TokenValidationService tokenValidationService;
    @InjectMocks
    TrainingController trainingController;

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
        this.mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
    }

    @Test
    public void getEmployeeId() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        String expectedEmployeeId = trainingController.getEmployeeId(request, response);

        assertThat("101", is(expectedEmployeeId));
    }

    @Test
    public void getEmployeeIdUnAuthorized() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn(null);

        String expectedEmployeeId = trainingController.getEmployeeId(request, response);

        assertThat(null, is(expectedEmployeeId));
    }

    @Test(expected = IOException.class)
    public void getEmployeeIdIOException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn(null);


        doThrow(IOException.class).when(response).sendError(anyInt(), anyString());

        trainingController.getEmployeeId(request, response);
    }


    @Test
    public void add() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        doNothing().when(trainingService).saveTraining(any(Training.class), anyList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                post("/training/add")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getTrainingDomain()))
        );
        resultAction.andExpect(status().isOk());

    }

    @Test
    public void addInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn(null);
        doNothing().when(trainingService).saveTraining(any(Training.class), anyList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                post("/training/add")

                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getTrainingDomain()))
        );
        resultAction.andExpect(status().isUnauthorized());

    }

    @Test
    public void addMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        doThrow(MongoException.class).when(trainingService).saveTraining(any(Training.class), anyList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                post("/training/add")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getTrainingDomain()))
        );
        resultAction.andExpect(status().isInternalServerError());

    }

    @Test
    public void getTraining() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");


        when(trainingService.getAllTrainings(anyString())).thenReturn(getTrainingDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/training/getalltraining")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].training.name", is("Basic Python")));

    }

    @Test
    public void getTrainingMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");


        when(trainingService.getAllTrainings(anyString())).thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/training/getalltraining")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());

    }

    @Test
    public void getTrainingInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn(null);

        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn(null);

        when(trainingService.getAllTrainings(anyString())).thenReturn(getTrainingDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/training/getalltraining")

        );
        resultAction.andExpect(status().isUnauthorized());

    }


    @Test
    public void enrollTraining() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");


        doNothing().when(trainingService).enrollTraining(anyString(), anyString());

        ResultActions resultAction = mockMvc.perform(
                get("/training/enroll?trainingId=1")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk());
    }

    @Test
    public void enrollTrainingInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn(null);

        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn(null);
        doNothing().when(trainingService).enrollTraining(anyString(), anyString());

        ResultActions resultAction = mockMvc.perform(
                get("/training/enroll?trainingId=1")

        );
        resultAction.andExpect(status().isUnauthorized());
    }

    @Test
    public void enrollTrainingMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");


        doThrow(MongoException.class).when(trainingService).enrollTraining(anyString(), anyString());

        ResultActions resultAction = mockMvc.perform(
                get("/training/enroll?trainingId=1")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());
    }

    @Test
    public void update() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        doNothing().when(trainingService).updateTraining(any(Training.class), anyList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                post("/training/update")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getTrainingDomain()))
        );
        resultAction.andExpect(status().isOk());
    }

    @Test
    public void updateInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn(null);

        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn(null);
        doNothing().when(trainingService).updateTraining(any(Training.class), anyList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                post("/training/update")

                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getTrainingDomain()))
        );
        resultAction.andExpect(status().isUnauthorized());
    }

    @Test
    public void updateMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        doThrow(MongoException.class).when(trainingService).updateTraining(any(Training.class), anyList());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                post("/training/update")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getTrainingDomain()))
        );
        resultAction.andExpect(status().isInternalServerError());
    }

    List<TrainingDomain> getTrainingDomainList() {
        List<TrainingDomain> toReturn = new ArrayList<>();
        toReturn.add(getTrainingDomain());
        return toReturn;
    }

    TrainingDomain getTrainingDomain() {
        TrainingDomain trainingDomain = new TrainingDomain();
        trainingDomain.setTraining(getTrainingPython());
        trainingDomain.setTrainingSessions(getTrainingSessionList());
        return trainingDomain;
    }

    Training getTrainingPython() {
        return new Training("Basic Python",
                "Training Room 1",
                20, "Technical",
                "Basic Python Training",
                "John",
                "john@teksystems.com");
    }

    List<TrainingSession> getTrainingSessionList() {
        List<TrainingSession> toReturn = new ArrayList<>();
        TrainingSession trainingSession = new TrainingSession("1",
                new Date(DATECONSTANT),
                "02:00",
                "04:00");

        toReturn.add(trainingSession);
        return toReturn;
    }
}