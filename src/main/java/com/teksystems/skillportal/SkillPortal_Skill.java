package com.teksystems.skillportal;
import com.teksystems.skillportal.controller.TokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication @ComponentScan({"com.teksystems.skillportal.*"})
public class SkillPortal_Skill   extends SpringBootServletInitializer implements CommandLineRunner{

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

    }


    @Override
    public void run(String... strings) throws Exception {

    }
}
