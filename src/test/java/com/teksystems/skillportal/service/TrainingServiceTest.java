package com.teksystems.skillportal.service;

import com.teksystems.skillportal.init.MongoConfigNew;
import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.model.TrainingSession;
import com.teksystems.skillportal.repository.TrainingRepository;
import com.teksystems.skillportal.repository.TrainingSessionRepository;
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
import org.springframework.data.mongodb.core.query.Query;


import java.util.*;


import static org.mockito.Matchers.any;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
@SpringBootTest
public class TrainingServiceTest {
    @Mock
    TrainingRepository trainingRepository;

    @Mock
    TrainingSessionRepository trainingSessionRepository;

    @InjectMocks
    TrainingService trainingService;

    ApplicationContext ctx;
    MongoOperations mongoOperation;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ctx = new AnnotationConfigApplicationContext(MongoConfigNew.class);
        mongoOperation =(MongoOperations) ctx.getBean("mongoTemplate");
    }

    private Long DATECONSTANT = 1532677775148L;
    private Long DATECONSTANT2 = 1533019950736L;

    @Test
    public void saveTrainingTest(){
        List<Training> traininglist = new ArrayList<>();

        traininglist.add( getTraining());

        List<TrainingSession> trainingSession = new ArrayList<>();
        trainingSession.add(new TrainingSession("1",new GregorianCalendar(2018,03,12).getTime(),"16:30","17:30"));

        trainingService.saveTraining(getTraining(),trainingSession);

        verify(trainingRepository,times(1)).save(any(Training.class));


    }

    @Test
    public void updateTrainingTest(){
        when(trainingSessionRepository.findBytrainingId(anyString())).thenReturn(getTrainingSessions());

        trainingService.updateTraining(getTraining(),getTrainingSessions());

        verify(trainingRepository,times(1)).save(any(Training.class));
        verify(trainingSessionRepository,times(1)).delete(any(List.class));
        verify(trainingSessionRepository,times(1)).save(any(List.class));
    }

//    @Test
//    public void getAllTrainingsTest(){
//        when(mongoOperation.find(any(), any())).thenReturn(Collections.singletonList(getTraningList()));
//
//
//    }



    public List<Training> getTraningList(){
        List<Training> toReturn = new ArrayList<>();
        toReturn.add(getTraining());
        return toReturn;
    }
    public Training getTraining(){
        return new  Training(
                "Java",
                "Cabin-1",
                20,
                "Technical",
                "Basics",
                "Abc",
                "sam@teksystems.com");
    }

    public List<TrainingSession> getTrainingSessions(){
        List<TrainingSession> toReturn = new ArrayList<>();
        toReturn.add(getTrainingSession());
        return toReturn;
    }
    public TrainingSession getTrainingSession(){
        return new TrainingSession(
                "1",
                new Date(DATECONSTANT),
                "02:00",
                "04:00"

        );
    }
}
