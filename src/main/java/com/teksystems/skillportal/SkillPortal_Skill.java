package com.teksystems.skillportal;
import com.teksystems.skillportal.controller.TokenFilter;
import com.teksystems.skillportal.init.GuavaCacheInit;
import com.teksystems.skillportal.model.*;
import com.teksystems.skillportal.repository.*;
import com.teksystems.skillportal.service.TokenValidationService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import com.teksystems.skillportal.init.MongoConfigNew;


@SpringBootApplication @ComponentScan({"com.teksystems.skillportal.*"})
public class SkillPortal_Skill   extends SpringBootServletInitializer implements CommandLineRunner{
	
@Autowired
EmployeeSkillRepository employeeSkillRepository;
@Autowired
 SubSkillRepository subSkillRepository;

@Autowired
SkillRepository skillRepository;


    @Autowired
    EmployeeTrainingRepository employeeTrainingRepository;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    TrainingSessionRepository trainingSessionRepository;

/*
	public void run(String... strings) throws Exception {

		employeeSkillRepository.deleteAll();
		subSkillRepository.deleteAll();

        employeeSkillRepository.save(Arrays.asList(new EmployeeSkill("101","6",4),

                new EmployeeSkill("101","4",3),
                new EmployeeSkill("101","5",5),
                new EmployeeSkill("102","5",4),
                new EmployeeSkill("102","6",3),
                new EmployeeSkill("103","7",4),
                new EmployeeSkill("103","4",5)

        ));


        subSkillRepository.save(Arrays.asList(
                new SubSkill("4","AWS Device Farm","2"),
                new SubSkill("5","Java","1"),
                new SubSkill("6","Angular","3"),
                new SubSkill("7","C++","1")
        ));
        
        skillRepository.save(Arrays.asList(
                new Skill("1","Programming"),
                new Skill("2","AWS"),
                new Skill("3","Front End")
        ));
   }
	*/
    @Bean
    public FilterRegistrationBean tokenFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new TokenFilter());
        registrationBean.addUrlPatterns("/");
        registrationBean.addUrlPatterns("/*");
        System.out.println("Filter Bean Registered");
        return registrationBean;
    }

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SkillPortal_Skill.class);
    }
	

	public static void main(String[] args)  {
		SpringApplication.run(SkillPortal_Skill.class, args);
//        String token1="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6ImlCakwxUmNxemhpeTRmcHhJeGRacW9oTTJZayJ9.eyJhdWQiOiJlZGIzMWM3YS0xMjczLTQ0ZTgtYjBkMC01MDgzMGFhZWRlMzUiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vMzcxY2I5MTctYjA5OC00MzAzLWI4NzgtYzE4MmVjODQwM2FjL3YyLjAiLCJpYXQiOjE1MjYyOTc1MTMsIm5iZiI6MTUyNjI5NzUxMywiZXhwIjoxNTI2MzAxNDEzLCJhaW8iOiJZMmNBQWcvam5hOVA4aVl6M1pvWDZialVhcHNPNzg3RXFQNU5DVzExVzY3NWlpZE0zQjE5OFlEUlpyL2t1RHNUb2l3M2xYVURBQT09IiwibmFtZSI6IlNpbmdoLCBTYWhpYiIsIm5vbmNlIjoiNzRlY2ZiZmYtNDI2MS00ZDQ5LTg1N2QtOTIxYzNjNTAwZGE2Iiwib2lkIjoiYTk1M2YzYWQtMTMxYy00YTk2LWFlNTYtNDA1MTM5Y2U1MGNkIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic2FoaXNpbmdoQHRla3N5c3RlbXMuY29tIiwic3ViIjoiRmVZamFQRTNtbFR2TlNkSFBuX2RGczFrdWtrMjl3NWtYN1hEMXRLd1VyVSIsInRpZCI6IjM3MWNiOTE3LWIwOTgtNDMwMy1iODc4LWMxODJlYzg0MDNhYyIsInV0aSI6IlBMa1pSQjgzVkV1ZkZqUE1UVGNIQUEiLCJ2ZXIiOiIyLjAifQ.hBzKbzHj9gXpbR5CwdQDjjfzqjAiPbDOpQLdM1k1G85CgdiVb8j4WO2A4jB4q4ATQr0CygD4B_rFNVT4HtMf2JEzW132sPgMfOhobdKf68Z7o_n41V9L9I0icrloStr7pmNgEvGqoE3YQvd9hIBhm4Rhpcc8pyjhwNYVpPO0oqiRazP7yokOm01pMEp0xKGYJvk0FMp6UpDgPKaCjjBd9hjoLM_Y5PIetUVBRrfIhFjvpq3t2dWrXLOCzsGo8u9bJsRtZFuw6SN10qjmKL1uKrc0tSMeIQBqIgjjUKELxbSLURAJvS5uNQJ5F17ej7wHh7lf5yCS1lh-wq47tqSIYA";
//        TokenValidationService token = new TokenValidationService(token1);
//        try{
//            System.out.println(token.tokenValidate());
//        }catch(Exception e){
//            e.printStackTrace();
//        }

//
//		ApplicationContext ctx =Map<String, List<String>> skillGroupMap = GuavaCacheInit.loadSkillGroup();
////		GuavaCacheInit.skillGroupCache.putAll(skillGroupMap);
////
////		Map<String, List<SubSkill>> skillMap = GuavaCacheInit.loadSkill();
////		GuavaCacheInit.skillCache.putAll(skillMap);
//                new AnnotationConfigApplicationContext(MongoConfigNew.class);
//		MongoOperations mongoOperation =
//                (MongoOperations) ctx.getBean("mongoTemplate");
		/*
		mongoOperation.createCollection("employeeskill");
		mongoOperation.insert(new EmployeeSkill("101","6",4));
		mongoOperation.insert( new EmployeeSkill("101","4",3));
		mongoOperation.insert(new EmployeeSkill("101","5",5));
		mongoOperation.insert(new EmployeeSkill("102","5",4));
		mongoOperation.insert(new EmployeeSkill("102","6",3));
		mongoOperation.insert(new EmployeeSkill("103","7",4));
		mongoOperation.insert(new EmployeeSkill("103","4",5));
		
		mongoOperation.createCollection("skill");
		mongoOperation.insertAll(Arrays.asList(
                new Skill("1","Programming"),
                new Skill("2","AWS"),
                new Skill("3","Front End")
        )
        );
        
		mongoOperation.createCollection("EmployeeCertification");
		
		
		mongoOperation.insertAll(Arrays.asList(
				new EmployeeCertification("101", "101", new Date(), new Date(), 12340, "abc.com"),
				new EmployeeCertification("101", "102", new Date(), new Date(), 12341, "abc.com"),
				new EmployeeCertification("101", "103", new Date(), new Date(), 12342, "abc.com"),
				new EmployeeCertification("101", "104", new Date(), new Date(), 12343, "abc.com")
        )
        );
        
		mongoOperation.createCollection("subskill");
		mongoOperation.insertAll(Arrays.asList(
                new SubSkill("1","Java","1"),
                new SubSkill("2","C++","1"),
                new SubSkill("3","Perl","1"),
                new SubSkill("4","AWS Device Farm","2"),
                new SubSkill("5","AWS Cloud","2"),
                new SubSkill("6","HTML","3"),
                new SubSkill("7","Apache","3"),
                new SubSkill("8","Angular","3"),
                new SubSkill("9","CSS","3")
        )
        );
        
       */
	}


    @Override
    public void run(String... strings) throws Exception {

//        employeeTrainingRepository.deleteAll();
//        trainingRepository.deleteAll();
//        trainingSessionRepository.deleteAll();
//
//        employeeTrainingRepository.save(Arrays.asList(new EmployeeTraining("10125","25",new GregorianCalendar(2018,03,23).getTime()),
//                new EmployeeTraining("prachawla@teksystems.com","26",new GregorianCalendar(2018,03,23).getTime()),
//                new EmployeeTraining("prachawla@teksystems.com","27",new GregorianCalendar(2018,03,22).getTime()),
//                new EmployeeTraining("sahisingh@teksystems.com","25",new GregorianCalendar(2018,03,28).getTime()),
//                new EmployeeTraining("10126","27",new GregorianCalendar(2018,03,27).getTime()),
//                new EmployeeTraining("10127","26",new GregorianCalendar(2018,03,16).getTime())
//        ));
//
//        trainingRepository.save(Arrays.asList(new Training("25","Redhat","IT Room",25,"Technical","RHCA","Jeff"),
//                new Training("26","Amazon","Meeting Room1",35,"Technical","AWS","Adam"),
//                new Training("27","UI","Meeting Room2",50,"Technical","Angular","Satyam"),
//                new Training("28","UI Advanced","Meeting Room3",40,"Technical","ReactJS","Shiva"),
//                new Training("29","Full Stack","Meeting Room4",100,"Technical","Full Stack Developer","Jacob")
//        ));
//
//        trainingSessionRepository.save(Arrays.asList(new TrainingSession("25",new GregorianCalendar(2018,03,12).getTime(),"16:00:00","17:00:00"),
//                new TrainingSession("26",new GregorianCalendar(2018,05,15).getTime(),"12:00:00","14:00:00"),
//                new TrainingSession("27",new GregorianCalendar(2018,05,17).getTime(),"08:00:00","11:00:00"),
//                new TrainingSession("28",new GregorianCalendar(2018,03,17).getTime(),"10:00:00","12:00:00"),
//                new TrainingSession("29",new GregorianCalendar(2018,06,21).getTime(),"16:00:00","17:00:00")
//
//
//        ));

    }
}