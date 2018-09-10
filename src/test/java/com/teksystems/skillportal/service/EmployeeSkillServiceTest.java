package com.teksystems.skillportal.service;


import com.teksystems.skillportal.init.GuavaCacheInit;

import com.teksystems.skillportal.model.EmployeeSkill;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.EmployeeSkillRepository;
import com.teksystems.skillportal.repository.SubSkillRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeSkillServiceTest {

    @Mock
    EmployeeSkillRepository employeeSkillRepository;

    @Mock
    SubSkillRepository subSkillRepository;

    @Mock
    GuavaCacheInit guavaCacheInit;

//    @Mock
//    MongoOperations mongoOperation;

    @InjectMocks
    EmployeeSkillService employeeSkillService;

    final static String EMPID ="101";
    final static String SUBSKILLID = "1";
    final static int RATING =3;
    private Long DATECONSTANT = 1532677775148L;
    private Long DATECONSTANT2 = 1533019950736L;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    //TODO ASK From Sir, how to mock mongooperations
    @Test
    public void getAllUnassignedSubSkills() throws ExecutionException {
//        List<SubSkill> subSkillList = getAllSubSkill();
//        CacheLoader<String, List<SubSkill>> subSkillLoader = new CacheLoader<String, List<SubSkill>>() {
//            @Override
//            public List<SubSkill> load(String key) {
//                return subSkillList;
//            }
//        };
//        LoadingCache<String, List<SubSkill>> cache = CacheBuilder.newBuilder().build(subSkillLoader);
//        Map<String, List<SubSkill>> subSkillMap = new HashMap<>();
//        subSkillMap.put("java", subSkillList);
//        cache.putAll(subSkillMap);
//        when(guavaCacheInit.getSkillLoadingCache()).thenReturn(cache);
//
//        ApplicationContext ctx =
//                new AnnotationConfigApplicationContext(MongoConfigNew.class);
//
//
//        mongoOperation =(MongoOperations) ctx.getBean("mongoTemplate");
//
//        when(mongoOperation.getCollection("employeeskill")
//                .distinct(ConfigurationStrings.SUBSKILLID, new BasicDBObject(ConfigurationStrings.EMPID, anyString())))
//                .thenReturn(getUnassignedSkills());
//
//        List<SubSkillDomain> expected = employeeSkillService.getAllUnassignedSubSkills("101","Programming");
//        assertThat(1,is(expected.size()));

    }

    @Test
    public void addNew() {
        employeeSkillService.addNew(EMPID,SUBSKILLID,RATING);

        verify(employeeSkillRepository,times(1)).save(Matchers.any(EmployeeSkill.class));

    }
    // TODO mongooperation one
    @Test
    public void getSubSkillCount() {

    }

//    @Test
//    public void getEmployeeSkillPlaceHolderDomain() {
//        when(employeeSkillRepository.findByEmpId(anyString())).thenReturn(getAssignedEmployeeSkill());
//        when(subSkillRepository.findById("1")).thenReturn(getSubSkill());
//        when(subSkillRepository.findById("2")).thenReturn(getSubSkill1());
//
//        when(employeeSkillRepository.findTopByEmpIdOrderByLastModifiedDateDesc(anyString())).thenReturn(getEmployeeSkill());
//
//        EmployeeSkillPlaceholderDomain expectedEmployeeSkillPlaceholderDomain =
//                employeeSkillService.getEmployeeSkillPlaceHolderDomain("101");
//        assertThat(2,is(expectedEmployeeSkillPlaceholderDomain.getNumberOfSkillRated()));
//    }

    @Test
    public void getAll() {
    }

    @Test
    public void deleteSubSkill() {
    }

    List<SubSkill> getAllSubSkill(){
        List<SubSkill> toReturnList = new ArrayList<>();
        toReturnList.add(getSubSkill());
        toReturnList.add(getSubSkill1());
        toReturnList.add(getSubSkill2());
        return toReturnList;
    }


    public SubSkill getSubSkill(){
        return new SubSkill("1",
                "Basic Java",
                "Basic java Skills",
                "Java",
                "Programming Language",
                "ADM");
    }

    public SubSkill getSubSkill1(){
        return new SubSkill("2",
                "Generics",
                "Basic generics in Java",
                "Java",
                "skillGroup",
                "ADM");
    }

    public SubSkill getSubSkill2(){
        return new SubSkill("3",
                "MS Excel",
                "Basic Ms Excel",
                "MS Word",
                "Applcations",
                "Basics");
    }

    private List<String> getUnassignedSkills(){
        List<String> toReturnSkills = new ArrayList<>();
        toReturnSkills.add("1");
        toReturnSkills.add("2");
        return toReturnSkills;
    }

    private EmployeeSkill getEmployeeSkill(){
        return new EmployeeSkill("101","1",3,new Date(DATECONSTANT));
    }

    private EmployeeSkill getEmployeeSkill1(){
        return new EmployeeSkill("101","2",4,new Date(DATECONSTANT2));
    }

    private List<EmployeeSkill> getAssignedEmployeeSkill(){
        List<EmployeeSkill> toReturn = new ArrayList<>();

        toReturn.add(getEmployeeSkill());
        toReturn.add(getEmployeeSkill1());
        return toReturn;

    }
}