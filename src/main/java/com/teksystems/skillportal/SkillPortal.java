package com.teksystems.skillportal;

import com.teksystems.skillportal.controller.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.teksystems.skillportal.*"})
public class SkillPortal extends SpringBootServletInitializer {

    @Bean
    public FilterRegistrationBean tokenFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new TokenFilter());
        registrationBean.addUrlPatterns("/");
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SkillPortal.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(SkillPortal.class, args);

    }


}
