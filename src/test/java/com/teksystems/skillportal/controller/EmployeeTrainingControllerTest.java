package com.teksystems.skillportal.controller;

import com.mongodb.MongoException;
import com.teksystems.skillportal.domain.EmployeeTrainingDomain;
import com.teksystems.skillportal.domain.EmployeeTrainingPlaceholderDomain;
import com.teksystems.skillportal.domain.TrainingEventDomain;
import com.teksystems.skillportal.domain.TrainingListEventDomain;
import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.service.EmployeeTrainingService;
import com.teksystems.skillportal.service.TokenValidationService;
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
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeTrainingControllerTest {

    @Mock
    EmployeeTrainingService employeeTrainingService;
    @Mock
    TokenValidationService tokenValidationService;

    @InjectMocks
    EmployeeTrainingController employeeTrainingController;

    private MockMvc mockMvc;
    private Long DATECONSTANT = 1532677775148L;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        when(tokenValidationService.extractEmployeeId(Mockito.any(HttpServletRequest.class))).thenReturn("101");
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeTrainingController).build();
    }

    @Test
    public void getByEmpId() throws Exception {
        when(employeeTrainingService.getEmployeeTrainingByEmployeeId(anyString()))
                .thenReturn(getEmployeeTrainingList());

        ResultActions resultAction = mockMvc.perform(
                get("/training/gettraining")
                        .header("Authorization", "empId:101")
        );


        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].empId", is("101")))
                .andExpect(jsonPath("$[0].lastModified", is(DATECONSTANT)))
                .andExpect(jsonPath("$[0].training.name", is("Basic Python")))
                .andExpect(jsonPath("$[1].empId", is("101")))
                .andExpect(jsonPath("$[1].lastModified", is(DATECONSTANT)))
                .andExpect(jsonPath("$[1].training.name", is("Advanced Python")));
    }

    @Test
    public void getByEmpIdUnAuthorized() throws Exception {
        when(employeeTrainingService.getEmployeeTrainingByEmployeeId(anyString()))
                .thenReturn(getEmployeeTrainingList());

        ResultActions resultAction = mockMvc.perform(
                get("/training/gettraining")

        );


        resultAction.andExpect(status().isUnauthorized());
    }

    @Test
    public void getByEmpIdMOngoException() throws Exception {
        when(employeeTrainingService.getEmployeeTrainingByEmployeeId(anyString()))
                .thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/training/gettraining")
                        .header("Authorization", "empId:101")
        );


        resultAction.andExpect(status().isInternalServerError());
    }

    @Test
    public void getEventByEmpId() throws Exception {
        when(employeeTrainingService.getTrainingEventByEmployeeId(anyString())).
                thenReturn(getTrainingEventDomainList());
        ResultActions resultAction = mockMvc.perform(
                get("/training/gettrainingevent")
                        .header("Authorization", "empId:101")
        );


        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Basic Python")))
                .andExpect(jsonPath("$[1].title", is("Advanced Python")));
    }

    @Test
    public void getEventByEmpIdUnAuthorized() throws Exception {
        when(employeeTrainingService.getTrainingEventByEmployeeId(anyString())).
                thenReturn(getTrainingEventDomainList());
        ResultActions resultAction = mockMvc.perform(
                get("/training/gettrainingevent")

        );


        resultAction.andExpect(status().isUnauthorized());
    }

    @Test
    public void getEventByEmpIdMongoException() throws Exception {
        when(employeeTrainingService.getTrainingEventByEmployeeId(anyString())).
                thenThrow(MongoException.class);
        ResultActions resultAction = mockMvc.perform(
                get("/training/gettrainingevent")
                        .header("Authorization", "empId:101")
        );


        resultAction.andExpect(status().isInternalServerError());
    }

    @Test
    public void getByEmpIdAndSubSkillId() throws Exception {
        doNothing().when(employeeTrainingService).cancelEnrollment(anyString(), anyString());
        ResultActions resultAction = mockMvc.perform(
                delete("/training/delete?trainingId=1")
                        .header("Authorization", "empId:101")
        );

        resultAction.andExpect(status().isOk());
    }

    @Test
    public void getByEmpIdAndSubSkillIdUnAuthorized() throws Exception {
        doNothing().when(employeeTrainingService).cancelEnrollment(anyString(), anyString());
        ResultActions resultAction = mockMvc.perform(
                delete("/training/delete?trainingId=1")

        );

        resultAction.andExpect(status().isUnauthorized());
    }

    @Test
    public void getByEmpIdAndSubSkillIdMongoException() throws Exception {
        doThrow(MongoException.class).when(employeeTrainingService).cancelEnrollment(anyString(), anyString());
        ResultActions resultAction = mockMvc.perform(
                delete("/training/delete?trainingId=1")
                        .header("Authorization", "empId:101")
        );

        resultAction.andExpect(status().isInternalServerError());
    }

    @Test
    public void getEventListByEmpId() throws Exception {
        when(employeeTrainingService.getTrainingListEventByEmployeeId(anyString()))
                .thenReturn(getTrainingListEventDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/training/gettrainingeventlist")
                        .header("Authorization", "empId:101")
        );


        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Basic Python")))
                .andExpect(jsonPath("$[1].name", is("Advanced Python")));
    }

    @Test
    public void getEventListByEmpIdUnAuthorized() throws Exception {
        when(employeeTrainingService.getTrainingListEventByEmployeeId(anyString()))
                .thenReturn(getTrainingListEventDomainList());

        ResultActions resultAction = mockMvc.perform(
                get("/training/gettrainingeventlist")

        );


        resultAction.andExpect(status().isUnauthorized());
    }

    @Test
    public void getEventListByEmpIdMongoException() throws Exception {
        when(employeeTrainingService.getTrainingListEventByEmployeeId(anyString()))
                .thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/training/gettrainingeventlist")
                        .header("Authorization", "empId:101")
        );


        resultAction.andExpect(status().isInternalServerError());

    }

    @Test
    public void getEnrolledTraining() throws Exception {
        when(employeeTrainingService.getEnrolledTraining(anyString()))
                .thenReturn(getEmployeeTrainingPlaceholderDomainList());
        ResultActions resultAction = mockMvc.perform(
                get("/training/getenrolledtrainings")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Basic Python")))
                .andExpect(jsonPath("$[1].name", is("Advanced Python")));
    }

    @Test
    public void getEnrolledTrainingUnAuthorized() throws Exception {
        when(employeeTrainingService.getEnrolledTraining(anyString()))
                .thenReturn(getEmployeeTrainingPlaceholderDomainList());
        ResultActions resultAction = mockMvc.perform(
                get("/training/getenrolledtrainings")

        );
        resultAction.andExpect(status().isUnauthorized());
    }

    @Test
    public void getEnrolledTrainingMongoException() throws Exception {
        when(employeeTrainingService.getEnrolledTraining(anyString()))
                .thenThrow(MongoException.class);
        ResultActions resultAction = mockMvc.perform(
                get("/training/getenrolledtrainings")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());
    }

    @Test
    public void getUpcomingTraining() throws Exception {
        when(employeeTrainingService.getUpcomingTraining())
                .thenReturn(getEmployeeTrainingPlaceholderDomainList());
        ResultActions resultAction = mockMvc.perform(
                get("/training/getupcomingtrainings")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Basic Python")))
                .andExpect(jsonPath("$[1].name", is("Advanced Python")));
    }

    @Test
    public void getUpcomingTrainingUnAuthorized() throws Exception {
        when(employeeTrainingService.getUpcomingTraining())
                .thenReturn(getEmployeeTrainingPlaceholderDomainList());
        ResultActions resultAction = mockMvc.perform(
                get("/training/getupcomingtrainings")

        );
        resultAction.andExpect(status().isUnauthorized());

    }

    @Test
    public void getUpcomingTrainingMongoException() throws Exception {
        when(employeeTrainingService.getUpcomingTraining())
                .thenThrow(MongoException.class);
        ResultActions resultAction = mockMvc.perform(
                get("/training/getupcomingtrainings")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());

    }


    // Helper Methods to be shifted in the seperate file
    public List<EmployeeTrainingDomain> getEmployeeTrainingList() {
        List<EmployeeTrainingDomain> toReturnList = new ArrayList<>();
        toReturnList.add(getEmployeeTrainingDomain());
        toReturnList.add(getEmployeeTrainingDomain1());
        return toReturnList;
    }

    public EmployeeTrainingDomain getEmployeeTrainingDomain() {
        return new EmployeeTrainingDomain("101", getTrainingPython(), new Date(DATECONSTANT));
    }

    public EmployeeTrainingDomain getEmployeeTrainingDomain1() {
        return new EmployeeTrainingDomain("101", getTrainingAdvancedePython(), new Date(DATECONSTANT));
    }

    public Training getTrainingPython() {
        return new Training("Basic Python",
                "Training Room 1",
                20, "Technical",
                "Basic Python Training",
                "John",
                "john@teksystems.com");
    }

    public Training getTrainingAdvancedePython() {
        return new Training("Advanced Python",
                "Training Room 1",
                20, "Technical",
                "Advanced Python Training",
                "John",
                "john@teksystems.com");
    }

    public TrainingEventDomain getTrainingEventDomain() {
        return new TrainingEventDomain("1", "Basic Python", "02:00", "04:00");
    }

    public TrainingEventDomain getTrainingEventDomain1() {
        return new TrainingEventDomain("1", "Advanced Python", "02:00", "04:00");
    }

    public List<TrainingEventDomain> getTrainingEventDomainList() {
        List<TrainingEventDomain> toReturn = new ArrayList<>();
        toReturn.add(getTrainingEventDomain());
        toReturn.add(getTrainingEventDomain1());
        return toReturn;
    }

    public TrainingListEventDomain getTrainingListEventDomain() {
        return new TrainingListEventDomain("1",
                new Date(DATECONSTANT),
                "02:00",
                "04:00",
                "Basic Python",
                "John",
                "Training Room");
    }

    public TrainingListEventDomain getTrainingListEventDomain1() {
        return new TrainingListEventDomain("2",
                new Date(DATECONSTANT),
                "02:00",
                "04:00",
                "Advanced Python",
                "John",
                "Training Room");
    }

    public List<TrainingListEventDomain> getTrainingListEventDomainList() {
        List<TrainingListEventDomain> toReturn = new ArrayList<>();
        toReturn.add(getTrainingListEventDomain());
        toReturn.add(getTrainingListEventDomain1());
        return toReturn;
    }

    public List<EmployeeTrainingPlaceholderDomain> getEmployeeTrainingPlaceholderDomainList() {
        List<EmployeeTrainingPlaceholderDomain> toReturn = new ArrayList<>();
        toReturn.add(getEmployeeTrainingPlaceholderDomain());
        toReturn.add(getEmployeeTrainingPlaceholderDomain1());
        return toReturn;
    }

    public EmployeeTrainingPlaceholderDomain getEmployeeTrainingPlaceholderDomain() {
        return new EmployeeTrainingPlaceholderDomain("1", "Basic Python", new Date(DATECONSTANT));
    }

    public EmployeeTrainingPlaceholderDomain getEmployeeTrainingPlaceholderDomain1() {
        return new EmployeeTrainingPlaceholderDomain("2", "Advanced Python", new Date(DATECONSTANT));
    }

}