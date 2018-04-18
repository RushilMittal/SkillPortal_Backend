package com.teksystems.skillportal;
import com.teksystems.skillportal.controller.TokenFilter;
import com.teksystems.skillportal.init.GuavaCacheInit;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.teksystems.skillportal.repository.EmployeeSkillRepository;
//import com.teksystems.skillportal.init.MongoConfig;
import com.teksystems.skillportal.init.MongoConfigNew;
import com.teksystems.skillportal.model.EmployeeCertification;
import com.teksystems.skillportal.model.EmployeeSkill;
import com.teksystems.skillportal.model.Skill;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.SkillRepository;
import com.teksystems.skillportal.repository.SubSkillRepository;


@SpringBootApplication @ComponentScan({"com.teksystems.skillportal.*"})
public class SkillPortal_Skill   extends SpringBootServletInitializer{
	
@Autowired
EmployeeSkillRepository employeeSkillRepository;
@Autowired
 SubSkillRepository subSkillRepository;
    
@Autowired
SkillRepository skillRepository;
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
	

	public static void main(String[] args) {
		SpringApplication.run(SkillPortal_Skill.class, args);
//		ApplicationContext ctx =
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

}
