package com.cdac.skillportal.service;

import com.cdac.skillportal.domain.TrainingDomain;
import com.cdac.skillportal.init.MongoConfigNew;
import com.cdac.skillportal.model.EmployeeTraining;
import com.cdac.skillportal.model.Training;
import com.cdac.skillportal.model.TrainingSession;
import com.cdac.skillportal.repository.EmployeeTrainingRepository;
import com.cdac.skillportal.repository.TrainingRepository;
import com.cdac.skillportal.repository.TrainingSessionRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(JUnit4.class)
@SpringBootTest
public class TrainingServiceTest {
    @Mock
    TrainingRepository trainingRepository;

    @Mock
    TrainingSessionRepository trainingSessionRepository;

    @Mock
    EmployeeTrainingRepository employeeTrainingRepository;

    @InjectMocks
    TrainingService trainingService;

    ApplicationContext ctx;
    MongoOperations mongoOperation;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ctx = new AnnotationConfigApplicationContext(MongoConfigNew.class);
        mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    }

    private Long DATECONSTANT = 1532677775148L;
    private Long DATECONSTANT2 = 1533019950736L;

    @Test
    public void saveTrainingTest() {


        List<TrainingSession> trainingSession = new ArrayList<>();
        trainingSession.add(new TrainingSession("1", new GregorianCalendar(2018, 03, 12).getTime(), "16:30", "17:30"));

        trainingService.saveTraining(getTraining(), trainingSession);

        verify(trainingRepository, times(1)).save(any(Training.class));
        verify(trainingSessionRepository,times(1)).save(any(List.class));

    }


    @Test
    public void updateTrainingTest() {
        when(trainingSessionRepository.findBytrainingId(anyString())).thenReturn(getTrainingSessions());

        trainingService.updateTraining(getTraining(), getTrainingSessions());

        verify(trainingRepository, times(1)).save(any(Training.class));
        verify(trainingSessionRepository, times(1)).delete(any(List.class));
        verify(trainingSessionRepository, times(1)).save(any(List.class));
    }

    @Test
    public void getAllTrainingsTest(){
        when(trainingRepository.findBySeatsGreaterThan(anyInt())).thenReturn(getTraningList());
        when(employeeTrainingRepository.findByempId(anyString())).thenReturn(getEmployeeTrainings());
        when(trainingSessionRepository.findBytrainingId("1")).thenReturn(getTrainingSessionList1());
        when(trainingSessionRepository.findBytrainingId("2")).thenReturn(getTrainingSessionList2());

        List<TrainingDomain> expected =  trainingService.getAllTrainings("101");

        MatcherAssert.assertThat("2", CoreMatchers.is(expected.get(0).getTraining().getId()));
        MatcherAssert.assertThat("2", CoreMatchers.is(expected.get(0).getTrainingSessions().get(0).getTrainingId()));

    }


    @Test
    public void enrollTrainingTest(){
        when(trainingRepository.findByid("1")).thenReturn(getTraining());
        trainingService.enrollTraining("1","1");

        verify(employeeTrainingRepository).save(any(EmployeeTraining.class));

        verify(trainingRepository,times(1)).save(any(Training.class));

    }

    @Test
    public void searchTrainingTest(){
        when(trainingRepository.findByNameRegex(anyString())).thenReturn(getTraningList());
        List<Training> expected = trainingService.searchTraining("Java");

        verify(trainingRepository,times(1)).findByNameRegex(anyString());

        assertThat(2,is(expected.size()));
    }


    public List<Training> getTraningList() {
        List<Training> toReturn = new ArrayList<>();
        toReturn.add(getTraining());
        toReturn.add(getTraining1());
        return toReturn;
    }
    public List<EmployeeTraining> getEmployeeTrainings() {
        List<EmployeeTraining> toReturn = new LinkedList<>();
        toReturn.add(new EmployeeTraining("101", "1", new Date(DATECONSTANT)));
//        toReturn.add(new EmployeeTraining("101", "2", new Date(DATECONSTANT2)));

        return toReturn;
    }

    public Training getTraining() {
        Training a =  new Training(
                "Java",
                "Cabin-1",
                20,
                "Technical",
                "Basics",
                "Abc",
                "sam@teksystems.com");
        a.setId("1");
        return a;
    }
    public Training getTraining1() {
        Training a =  new Training(
                "Java",
                "Cabin-2",
                20,
                "Technical",
                "Basics",
                "Abc",
                "sam@teksystems.com");
        a.setId("2");
        return a;
    }


    public List<TrainingSession> getTrainingSessions() {
        List<TrainingSession> toReturn = new ArrayList<>();
        toReturn.add(getTrainingSession());
        toReturn.add(getTrainingSession1());
        return toReturn;
    }

    public List<TrainingSession> getTrainingSessionList1(){
        List<TrainingSession> toReturn  = new ArrayList<>();
        toReturn.add(getTrainingSession());
        return toReturn;
    }
    public TrainingSession getTrainingSession() {
        return new TrainingSession(
                "1",
                new Date(DATECONSTANT),
                "02:00",
                "04:00"

        );
    }

    public List<TrainingSession> getTrainingSessionList2(){
        List<TrainingSession> toReturn  = new ArrayList<>();
        toReturn.add(getTrainingSession1());
        return toReturn;
    }

    public TrainingSession getTrainingSession1() {
        return new TrainingSession(
                "2",
                new Date(DATECONSTANT),
                "03:00",
                "05:00"

        );
    }


}
