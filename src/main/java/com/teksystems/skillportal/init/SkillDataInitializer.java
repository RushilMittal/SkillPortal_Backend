package com.teksystems.skillportal.init;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.teksystems.skillportal.model.SubSkill;

@Configuration
public class SkillDataInitializer {

	GuavaCacheInit guavaCacheInit;

	@Autowired
	public SkillDataInitializer(GuavaCacheInit guavaCacheInit){
		this.guavaCacheInit = guavaCacheInit;
	}

	@Bean
	public Void loadCache() {
		Map<String, List<String>> skillGroupMap =  new GuavaCacheInit().loadSkillGroup();
		guavaCacheInit.skillGroupCache.putAll(skillGroupMap);

		Map<String, List<SubSkill>> skillMap = new GuavaCacheInit().loadSkill();
		guavaCacheInit.skillCache.putAll(skillMap);
		return null;
	}
	
}
