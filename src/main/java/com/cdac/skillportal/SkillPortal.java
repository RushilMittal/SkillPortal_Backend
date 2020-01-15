package com.cdac.skillportal;

import com.cdac.skillportal.init.GuavaCacheInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.cdac.skillportal.*"})
public class SkillPortal extends SpringBootServletInitializer {

    @Autowired
    static
    GuavaCacheInit guavaCacheInit;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SkillPortal.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SkillPortal.class, args);

    }

}
