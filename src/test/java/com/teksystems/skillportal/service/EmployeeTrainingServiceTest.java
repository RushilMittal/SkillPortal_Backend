package com.teksystems.skillportal.service;

import com.teksystems.skillportal.domain.EmployeeTrainingDomain;
import com.teksystems.skillportal.domain.EmployeeTrainingPlaceholderDomain;
import com.teksystems.skillportal.domain.TrainingEventDomain;
import com.teksystems.skillportal.domain.TrainingListEventDomain;
import com.teksystems.skillportal.model.EmployeeTraining;
import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.model.TrainingSession;
import com.teksystems.skillportal.repository.EmployeeTrainingRepository;
import com.teksystems.skillportal.repository.TrainingRepository;
import com.teksystems.skillportal.repository.TrainingSessionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeTrainingServiceTest {

    @Mock
    EmployeeTrainingRepository employeeTrainingRepository;

    @Mock
    TrainingRepository trainingRepository;

    @Mock
    TrainingSessionRepository trainingSessionRepository;

    @InjectMocks
    EmployeeTrainingService employeeTrainingService;

    private Long DATECONSTANT = 1532677775148L;
    private Long DATECONSTANT2 = 1533019950736L;
    private Long STARTTIMETRAININGONE = 1532656800L;
    private Long STARTTIMETRAININGTWO = 1533002400L;
    private Long ENDTIMETRAININGONE = 1532664000L;
    private Long ENDTIMETRAININGTWO = 1533009600L;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEmployeeTrainingByEmployeeId() throws Exception  {
        when(employeeTrainingRepository.findByempId(anyString())).thenReturn(getEmployeeTrainings());
        when(trainingRepository.findByid("1")).thenReturn(getFirstTraining());
        when(trainingRepository.findByid("2")).thenReturn(getSecondTraining());

        List<EmployeeTrainingDomain> expected = employeeTrainingService.getEmployeeTrainingByEmployeeId("101");
        assertThat(2,is(expected.size()));
        assertThat("Java - Core",is(expected.get(0).getTraining().getName()));
        assertThat("Java - Generics",is(expected.get(1).getTraining().getName()));
        assertThat("John",is(expected.get(0).getTraining().getTrainer()));
        assertThat("Sam",is(expected.get(1).getTraining().getTrainer()));

    }

    @Test
    public void testGetTrainingEventByEmployeeId() throws Exception{
        when(employeeTrainingRepository.findByempId(anyString())).thenReturn(getEmployeeTrainings());
        when(trainingSessionRepository.findBytrainingId("1")).thenReturn(getTraningSessionOfFirstTraining());
        when(trainingSessionRepository.findBytrainingId("2")).thenReturn(getTraningSessionOfSecondTraining());

        when(trainingRepository.findByid("1")).thenReturn(getFirstTraining());
        when(trainingRepository.findByid("2")).thenReturn(getSecondTraining());

        List<TrainingEventDomain> expected  = employeeTrainingService.getTrainingEventByEmployeeId("101");

        assertThat(3,is(expected.size()));
        assertThat("Java - Core",is(expected.get(0).getTitle()));
        assertThat("Java - Core",is(expected.get(1).getTitle()));
        assertThat("Java - Generics",is(expected.get(2).getTitle()));



    }

    @Test
    public void testCancelEnrollment() throws Exception {
        when(trainingRepository.findByid("1")).thenReturn(getFirstTraining());
        when(employeeTrainingRepository.findByEmpIdAndTrainingId(anyString(),anyString())).thenReturn(getEmployeeTraining());
        employeeTrainingService.cancelEnrollment("101","1");

        verify(employeeTrainingRepository,times(1)).delete(any(EmployeeTraining.class));
        verify(trainingRepository,times(1)).save(any(Training.class));


    }

    @Test
    public void testGetTrainingListEventByEmployeeId() throws Exception {
        when(employeeTrainingRepository.findByempId(anyString())).thenReturn(getEmployeeTrainings());
        when(trainingSessionRepository.findBytrainingId("1")).thenReturn(getTraningSessionOfFirstTraining());
        when(trainingSessionRepository.findBytrainingId("2")).thenReturn(getTraningSessionOfSecondTraining());

        when(trainingRepository.findByid("1")).thenReturn(getFirstTraining());
        when(trainingRepository.findByid("2")).thenReturn(getSecondTraining());

        List<TrainingListEventDomain> expected = employeeTrainingService.getTrainingListEventByEmployeeId("101");


        assertThat(3,is(expected.size()));
        assertThat("Java - Core",is(expected.get(0).getName()));
        assertThat("Training Room - 1",is(expected.get(1).getLocation()));
        assertThat("Sam",is(expected.get(2).getTrainer()));


    }

    @Test
    public void testGetUpcomingTraining() throws Exception {
        when(trainingRepository.findAll()).thenReturn(getTrainingDetails());

        when(trainingSessionRepository.findBytrainingId("1")).thenReturn(getTraningSessionOfFirstTraining());
        when(trainingSessionRepository.findBytrainingId("2")).thenReturn(getTraningSessionOfSecondTraining());

        List<EmployeeTrainingPlaceholderDomain> expected = employeeTrainingService.getUpcomingTraining();

        assertThat(2,is(expected.size()));
        assertThat("Java - Core",is(expected.get(0).getName()));
        assertThat("2",is(expected.get(1).getTrainingId()));

    }

    @Test
    public void testGetEnrolledTraining() throws Exception {
        when(employeeTrainingRepository.findByempId(anyString())).thenReturn(getEmployeeTrainings());
        when(trainingRepository.findByid("1")).thenReturn(getFirstTraining());
        when(trainingRepository.findByid("2")).thenReturn(getSecondTraining());

        when(trainingSessionRepository.findBytrainingId("1")).thenReturn(getTraningSessionOfFirstTraining());
        when(trainingSessionRepository.findBytrainingId("2")).thenReturn(getTraningSessionOfSecondTraining());

        List<EmployeeTrainingPlaceholderDomain> expected = employeeTrainingService.getEnrolledTraining("101");

        assertThat(2,is(expected.size()));
        assertThat("Java - Core",is(expected.get(0).getName()));
        assertThat(new Date(DATECONSTANT),is(expected.get(1).getTrainingDate()));




    }

    public List<EmployeeTraining> getEmployeeTrainings(){
        List<EmployeeTraining> toReturn = new LinkedList<>();
        toReturn.add(new EmployeeTraining("101","1",new Date(DATECONSTANT)));
        toReturn.add(new EmployeeTraining("101","2",new Date(DATECONSTANT2)));

        return toReturn;
    }

    public EmployeeTraining getEmployeeTraining(){
        return new EmployeeTraining("101","1",new Date(DATECONSTANT));
    }

    public List<Training> getTrainingDetails(){
        List<Training> toReturn = new LinkedList<>();
        toReturn.add(
                new Training(
                        "1",
                        "Java - Core",
                        "Training Room - 1",
                        25,
                        "Technical",
                        "Training on core java Concepts",
                        "John",
                        "john@teksystems.com")
        );

        toReturn.add(
                new Training(
                        "2",
                        "Java - Generics",
                        "Training Room - 2",
                        20,
                        "Technical",
                        "Training on generics in Java",
                        "Sam",
                        "sam@teksystems.com")
        );
        return toReturn;
    }

    public Training getFirstTraining(){
        return new Training(
                "1",
                "Java - Core",
                "Training Room - 1",
                25,
                "Technical",
                "Training on core java Concepts",
                "John",
                "john@teksystems.com");
    }

    public Training getSecondTraining(){
        return new Training(
                "2",
                "Java - Generics",
                "Training Room - 2",
                20,
                "Technical",
                "Training on generics in Java",
                "Sam",
                "sam@teksystems.com");
    }

    public List<TrainingSession> getTraningSessionOfFirstTraining(){
        List<TrainingSession> toReturn = new LinkedList<>();
        toReturn.add(new TrainingSession("1",new Date(DATECONSTANT),"2018-07-27 02:00","2018-07-27 04:00"));
        toReturn.add(new TrainingSession("1",new Date(DATECONSTANT),"2018-07-28 02:00","2018-07-28 05:00"));
        return toReturn;
    }

    public List<TrainingSession> getTraningSessionOfSecondTraining(){
        List<TrainingSession> toReturn = new LinkedList<>();
        toReturn.add(new TrainingSession("1",new Date(DATECONSTANT),"2018-07-31 02:00","2018-07-31 04:00"));
        return toReturn;
    }
}