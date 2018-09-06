package com.teksystems.skillportal.init;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mongodb.BasicDBObject;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.model.SubSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GuavaCacheInit {

    ApplicationContext ctx =
            new AnnotationConfigApplicationContext(MongoConfigNew.class);
    MongoOperations mongoOperation =
            (MongoOperations) ctx.getBean("mongoTemplate");

    private LoadingCache<String, List<String>> skillGroupCache;
    private LoadingCache<String, List<SubSkill>> skillCache;

    public void putSkillCache(Map<String, List<SubSkill>> skillMap) {
        skillCache.putAll(skillMap);
    }

    public void putSkillGroupCache(Map<String, List<String>> skillGroupMap) {
        skillGroupCache.putAll(skillGroupMap);
    }

    @Autowired
    public GuavaCacheInit() {
        skillGroupCache = CacheBuilder.newBuilder()
                .build(
                        new CacheLoader<String, List<String>>() {
                            @Override
                            public List<String> load(String key) throws Exception {
                                return getSkillGroup(key);
                            }
                        });

        skillCache = CacheBuilder.newBuilder()
                .build(
                        new CacheLoader<String, List<SubSkill>>() {
                            @Override
                            public List<SubSkill> load(String key) throws Exception {
                                return getSkill(key);
                            }
                        });
    }


    public LoadingCache<String, List<String>> getLoadingCache() {
        return skillGroupCache;
    }

    public LoadingCache<String, List<SubSkill>> getSkillLoadingCache() {
        return skillCache;
    }


    public List<String> getSkillGroup(String key) {


        return mongoOperation.getCollection(ConfigurationStrings.SUBSKILL).distinct(ConfigurationStrings.SKILL, new BasicDBObject(ConfigurationStrings.SKILLGROUP, key));
    }


    public List<SubSkill> getSkill(String key) {

        String[] splitKey = key.split("_");
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where(ConfigurationStrings.SKILLGROUP).is(splitKey[0]), Criteria.where(ConfigurationStrings.SKILL).is(splitKey[1]));
        Query query = new Query(criteria);
        return mongoOperation.find(query, SubSkill.class);
    }


    public Map<String, List<String>> loadSkillGroup() {

        Map<String, List<String>> skillGroupMap = new HashMap<>();

        List<String> skillGroups = mongoOperation.getCollection(ConfigurationStrings.SUBSKILL).distinct(ConfigurationStrings.SKILLGROUP);

        for (String iterable : skillGroups) {
            List<String> skills = mongoOperation.getCollection(ConfigurationStrings.SUBSKILL).distinct(ConfigurationStrings.SKILL, new BasicDBObject(ConfigurationStrings.SKILLGROUP, iterable));
            skillGroupMap.put(iterable, skills);
        }

        return skillGroupMap;
    }


    public Map<String, List<SubSkill>> loadSkill() {

        Map<String, List<String>> skillGroupMap = loadSkillGroup();

        Map<String, List<SubSkill>> skillMap = new HashMap<>();

        for (Map.Entry<String, List<String>> entrySet : skillGroupMap.entrySet()) {
            String key = entrySet.getKey();
            List<String> skillNames = skillGroupMap.get(key);

            for (String iterable : skillNames) {
                Criteria criteria = new Criteria();
                criteria.andOperator(Criteria.where(ConfigurationStrings.SKILLGROUP).is(key), Criteria.where(ConfigurationStrings.SKILL).is(iterable));
                Query query = new Query(criteria);
                List<SubSkill> subSkills = mongoOperation.find(query, SubSkill.class);
                skillMap.put(key + "_" + iterable, subSkills);
            }
        }
        return skillMap;
    }

}
