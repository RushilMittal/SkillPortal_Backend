package com.cdac.skillportal.init;

import com.cdac.skillportal.model.SubSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class SkillDataInitializer {

    GuavaCacheInit guavaCacheInit;

    @Autowired
    public SkillDataInitializer(GuavaCacheInit guavaCacheInit) {
        this.guavaCacheInit = guavaCacheInit;
    }

    @Bean
    public Void loadCache() {
        Map<String, List<String>> skillGroupMap = new GuavaCacheInit().loadSkillGroup();
        guavaCacheInit.putSkillGroupCache(skillGroupMap);

        Map<String, List<SubSkill>> skillMap = new GuavaCacheInit().loadSkill();
        guavaCacheInit.putSkillCache(skillMap);
        return null;
    }

}
