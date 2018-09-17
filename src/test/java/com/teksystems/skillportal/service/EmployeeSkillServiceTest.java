package com.teksystems.skillportal.service;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.EmployeeSkillPlaceholderDomain;
import com.teksystems.skillportal.domain.SubSkillDomain;
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

import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

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

    final static String EMPID = "101";
    final static String SUBSKILLID = "1";
    final static int RATING = 3;
    private Long DATECONSTANT = 1532677775148L;
    private Long DATECONSTANT2 = 1533019950736L;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    //TODO ASK From Sir, how to mock mongooperations
    @Test
    public void getAllUnassignedSubSkills() throws ExecutionException {
        List<SubSkill> subSkillList = getAllSubSkillList();
        CacheLoader<String, List<SubSkill>> loader = new CacheLoader<String, List<SubSkill>>() {
            @Override
            public List<SubSkill> load(String key) {
                return subSkillList;
            }
        };
        LoadingCache<String, List<SubSkill>> cache = CacheBuilder.newBuilder().build(loader);
        Map<String, List<SubSkill>> map = new HashMap<>();
        map.put("java", subSkillList);
        cache.putAll(map);
        when(guavaCacheInit.getSkillLoadingCache()).thenReturn(cache);

        when(employeeSkillRepository.findByEmpId(anyString())).thenReturn(getAssignedEmployeeSkill());

        List<SubSkillDomain> expected = employeeSkillService.getAllUnassignedSubSkills("101","Java");

        assertThat(1,is(expected.size()));
        assertThat("Advanced Java",is(expected.get(0).getSubSkill()));


    }

    @Test
    public void addNew() {
        employeeSkillService.addNew(EMPID, SUBSKILLID, RATING);

        verify(employeeSkillRepository, times(1)).save(Matchers.any(EmployeeSkill.class));

    }


    @Test
    public void getSubSkillCount() {
        when(employeeSkillRepository.findBySubSkillId(anyString())).thenReturn(getAssignedEmployeeSkill());

        int expected = employeeSkillService.getSubSkillCount("1");

        assertThat(1,is(expected));

    }

    @Test
    public void getEmployeeSkillPlaceHolderDomain() {
        when(employeeSkillRepository.findByEmpId(anyString())).thenReturn(getAssignedEmployeeSkill());
        when(subSkillRepository.findById("1")).thenReturn(getSubSkill());
        when(subSkillRepository.findById("2")).thenReturn(getSubSkill1());

        when(employeeSkillRepository.findTopByEmpIdOrderByLastModifiedDateDesc(anyString())).thenReturn(getEmployeeSkill());

        EmployeeSkillPlaceholderDomain expectedEmployeeSkillPlaceholderDomain =
                employeeSkillService.getEmployeeSkillPlaceHolderDomain("101");
        assertThat(2,is(expectedEmployeeSkillPlaceholderDomain.getNumberOfSkillRated()));
    }



    @Test
    public void getAll() {
        when(employeeSkillRepository.findByEmpId(anyString())).thenReturn(getAssignedEmployeeSkill());
        when(subSkillRepository.findById("1")).thenReturn(getSubSkill());
        when(subSkillRepository.findById("2")).thenReturn(getSubSkill1());

        List<EmployeeSkillDomain> expected = employeeSkillService.getAll("101");
        assertThat(2,is(expected.size()));
        assertThat("Basic Java",is(expected.get(0).getSubSkill().getSubSkill()));
        assertThat("Generics",is(expected.get(1).getSubSkill().getSubSkill()));
    }


    @Test
    public void deleteSubSkill() {
        when(employeeSkillRepository.findByEmpIdAndSubSkillId(anyString(),anyString()))
                .thenReturn(getAssignedEmployeeSkill());

        employeeSkillService.deleteSubSkill("101","1");
        verify(employeeSkillRepository,times(2)).delete(any(EmployeeSkill.class));

    }


    @Test
    public void getAssignedSkillIdsTest(){
        when(employeeSkillRepository.findByEmpId(anyString())).thenReturn(getAssignedEmployeeSkill());

        List<String> expected = employeeSkillService.getAssignedSkillIds("101");

        assertThat(2,is(expected.size()));
        assertThat("1",is(expected.get(0)));
        assertThat("2",is(expected.get(1)));

    }

    List<SubSkill> getAllSubSkillList() {
        List<SubSkill> toReturnList = new ArrayList<>();
        toReturnList.add(getSubSkill());
        toReturnList.add(getSubSkill1());
        toReturnList.add(getSubSkill2());
        return toReturnList;
    }


    public SubSkill getSubSkill() {
        return new SubSkill("1",
                "Basic Java",
                "Basic java Skills",
                "Java",
                "Programming Language",
                "ADM");
    }

    public SubSkill getSubSkill1() {
        return new SubSkill("2",
                "Generics",
                "Basic generics in Java",
                "Java",
                "skillGroup",
                "ADM");
    }

    public SubSkill getSubSkill2(){
        return new SubSkill("3",
                "Advanced Java",
                "Advanced Java",
                "Java",
                "skillGroup",
                "ADM");
    }


    private List<String> getUnassignedSkills() {
        List<String> toReturnSkills = new ArrayList<>();
        toReturnSkills.add("1");
        toReturnSkills.add("2");
        return toReturnSkills;
    }

    private EmployeeSkill getEmployeeSkill() {
        return new EmployeeSkill("101", "1", 3, new Date(DATECONSTANT));
    }

    private EmployeeSkill getEmployeeSkill1() {
        return new EmployeeSkill("101", "2", 4, new Date(DATECONSTANT2));
    }

    private List<EmployeeSkill> getAssignedEmployeeSkill() {
        List<EmployeeSkill> toReturn = new ArrayList<>();

        toReturn.add(getEmployeeSkill());
        toReturn.add(getEmployeeSkill1());
        return toReturn;

    }
}