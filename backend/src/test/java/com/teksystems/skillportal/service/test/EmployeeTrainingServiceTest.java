package com.teksystems.skillportal.service.test;


import com.teksystems.skillportal.domain.TrainingEventDomain;
import com.teksystems.skillportal.domain.TrainingListEventDomain;
import com.teksystems.skillportal.model.EmployeeTraining;
import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.model.TrainingSession;
import com.teksystems.skillportal.repository.EmployeeTrainingRepository;
import com.teksystems.skillportal.repository.TrainingRepository;
import com.teksystems.skillportal.repository.TrainingSessionRepository;
import com.teksystems.skillportal.service.EmployeeTrainingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

@RunWith(JUnit4.class)
@SpringBootTest
public class EmployeeTrainingServiceTest {

    @Mock
    EmployeeTrainingRepository employeeTrainingRepository;

    @Mock
    TrainingSessionRepository trainingSessionRepository;

    @Mock
    TrainingRepository trainingRepository;

    @InjectMocks
    EmployeeTrainingService employeeTrainingService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getTrainingEventByEmployeeIdTest() throws Exception{
        List<EmployeeTraining> employeeTrainingList = new ArrayList<EmployeeTraining>();

        employeeTrainingList.add(new EmployeeTraining("105", "35", new GregorianCalendar(2018,03,23).getTime()));
        employeeTrainingList.add(new EmployeeTraining("105", "36", new GregorianCalendar(2018,04,04).getTime()));
        when(employeeTrainingRepository.findByempId(anyString())).thenReturn(employeeTrainingList);
        List<TrainingSession> trainingSessionList1 = new ArrayList<TrainingSession>();
        trainingSessionList1.add(new TrainingSession("35", new GregorianCalendar(2018, 05, 15).getTime(), "16:00:00", "17:00:00"));
        List<TrainingSession> trainingSessionList2 = new ArrayList<TrainingSession>();
        trainingSessionList2.add(new TrainingSession("36", new GregorianCalendar(2018, 05, 20).getTime(), "10:00:00", "12:00:00"));

        when(trainingSessionRepository.findBytrainingId(anyString())).thenReturn(trainingSessionList1).thenReturn(trainingSessionList2);
        Training training1 = new Training("35", "Redhat", "IT Room", 25, "Technical", "RHCA", "Jeff");
        Training training2 = new Training("36", "Amazon", "Meeting Room1", 35, "Technical", "AWS", "Adam");

        when(trainingRepository.findByid(anyString())).thenReturn(training1).thenReturn(training2);
        List<TrainingEventDomain> result = employeeTrainingService.getTrainingEventByEmployeeId("105");
        assertEquals(2, result.size());

    }

    @Test
    public void cancelEnrollmentTest()throws Exception {
        EmployeeTraining employeeTraining = new EmployeeTraining("105", "35", new GregorianCalendar(2018,03,23).getTime());
        when(employeeTrainingRepository.findByEmpIdAndTrainingId(anyString(), anyString())).thenReturn(employeeTraining);
        employeeTrainingService.cancelEnrollment("105", "35");

        verify(employeeTrainingRepository, times(1)).delete(employeeTraining);
    }

    @Test
    public void getTrainingListEventByEmployeeIdTest() throws Exception{

        List<EmployeeTraining> employeeTrainingList = new ArrayList<EmployeeTraining>();

        employeeTrainingList.add(new EmployeeTraining("105", "35", new GregorianCalendar(2018,03,23).getTime()));
        employeeTrainingList.add(new EmployeeTraining("105", "36", new GregorianCalendar(2018,03,23).getTime()));
        when(employeeTrainingRepository.findByempId(anyString())).thenReturn(employeeTrainingList);

        List<TrainingSession> trainingSessionList1 = new ArrayList<TrainingSession>();
        trainingSessionList1.add(new TrainingSession("35", new GregorianCalendar(2018, 05, 15).getTime(), "16:00:00", "17:00:00"));
        List<TrainingSession> trainingSessionList2 = new ArrayList<TrainingSession>();
        trainingSessionList2.add(new TrainingSession("36", new GregorianCalendar(2018, 05, 20).getTime(), "10:00:00", "12:00:00"));

        when(trainingSessionRepository.findBytrainingId(anyString())).thenReturn(trainingSessionList1).thenReturn(trainingSessionList2);
        Training training1 = new Training("35", "Redhat", "IT Room", 25, "Technical", "RHCA", "Jeff");
        Training training2 = new Training("36", "Amazon", "Meeting Room1", 35, "Technical", "AWS", "Adam");

        when(trainingRepository.findByid(anyString())).thenReturn(training1).thenReturn(training2);
        List<TrainingListEventDomain> result = employeeTrainingService.getTrainingListEventByEmployeeId("105");
        assertEquals(2, result.size());
    }




}
