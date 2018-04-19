package com.teksystems.skillportal.init;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.teksystems.skillportal.model.SubSkill;

@Configuration
public class SkillDataInitializer {

	@Bean
	public Void loadCache() {
		Map<String, List<String>> skillGroupMap = GuavaCacheInit.loadSkillGroup();
		GuavaCacheInit.skillGroupCache.putAll(skillGroupMap);

		Map<String, List<SubSkill>> skillMap = GuavaCacheInit.loadSkill();
		GuavaCacheInit.skillCache.putAll(skillMap);
		return null;
	}
	
}
