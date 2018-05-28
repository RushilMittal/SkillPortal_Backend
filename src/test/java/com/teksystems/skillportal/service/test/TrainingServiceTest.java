package com.teksystems.skillportal.service.test;

import com.teksystems.skillportal.model.Training;
import com.teksystems.skillportal.model.TrainingSession;
import com.teksystems.skillportal.repository.TrainingRepository;
import com.teksystems.skillportal.repository.TrainingSessionRepository;
import com.teksystems.skillportal.service.TrainingService;
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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTrainingTest(){
        List<Training> traininglist = new ArrayList<>();
        Training training = new Training("1","Java","Cabin-1",20,"Technical","Basics","Abc","sam@teksystems.com");
        traininglist.add( training);

        List<TrainingSession> trainingSession = new ArrayList<>();
        trainingSession.add(new TrainingSession("1",new GregorianCalendar(2018,03,12).getTime(),"16:30","17:30"));

        when(trainingRepository.findAll()).thenReturn(traininglist);
        when(trainingSessionRepository.findAll()).thenReturn(trainingSession);

        this.trainingService.saveTraining(training,trainingSession);
        verify(trainingRepository,times(1)).save(training);
        verify(trainingSessionRepository,times(1)).save(trainingSession);

    }
}
