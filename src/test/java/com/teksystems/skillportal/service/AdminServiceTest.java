package com.teksystems.skillportal.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.init.GuavaCacheInit;
import com.teksystems.skillportal.model.AdminRoles;
import com.teksystems.skillportal.model.Certification;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.AdminRoleRepository;
import com.teksystems.skillportal.repository.CertificationRepository;
import com.teksystems.skillportal.repository.SubSkillRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class AdminServiceTest {
    @Mock
    public AdminRoleRepository adminRoleRepository;
    @Mock
    public SubSkillRepository subSkillRepository;
    @Mock
    private CertificationRepository certificationRepository;
    @Mock
    public GuavaCacheInit guavaCacheInit;
    @InjectMocks
    public AdminService adminService;


    final static String SKILLSAMPLECSV = "1,ADM,Programming,Python,Basics,Basics Python Skills";
    final static String CERTIFICATIONSAMPLECSV = "1,Python Basics,DataCamp";

    @Before
    public void setUp() throws Exception {


        MockitoAnnotations.initMocks(this);

        List<String> skillCacheList = getSkillList();
        CacheLoader<String, List<String>> loader = new CacheLoader<String, List<String>>() {
            @Override
            public List<String> load(String s) throws Exception {
                return skillCacheList;
            }
        };
        LoadingCache<String, List<String>> skillGroupCache = CacheBuilder.newBuilder().build(loader);
        Map<String, List<String>> map = new HashMap<>();
        map.put("Programming", skillCacheList);
        skillGroupCache.putAll(map);
        when(guavaCacheInit.getLoadingCache()).thenReturn(skillGroupCache);
        when(guavaCacheInit.loadSkillGroup()).thenReturn(map);

        List<SubSkill> subSkillList = getAllSubSkill();
        CacheLoader<String, List<SubSkill>> subSkillLoader = new CacheLoader<String, List<SubSkill>>() {
            @Override
            public List<SubSkill> load(String key) {
                return subSkillList;
            }
        };
        LoadingCache<String, List<SubSkill>> cache = CacheBuilder.newBuilder().build(subSkillLoader);
        Map<String, List<SubSkill>> subSkillMap = new HashMap<>();
        subSkillMap.put("java", subSkillList);
        cache.putAll(subSkillMap);
        when(guavaCacheInit.getSkillLoadingCache()).thenReturn(cache);
        when(guavaCacheInit.loadSkill()).thenReturn(subSkillMap);


    }

    @Test
    public void testIsAdmin() {
        AdminRoles adminRoles = new AdminRoles("1", "admin");

        when(adminRoleRepository.findByUserRole(anyString())).thenReturn(adminRoles);

        boolean expected = adminService.isAdmin("admin");
        assertThat(true, is(expected));

    }

    @Test
    public void testGetAllAdminSkills() {

        when(subSkillRepository.findAll()).thenReturn(getAllSubSkill());

        List<SubSkill> expected = adminService.getAllAdminSkills();

        assertThat(2, is(expected.size()));
        assertThat("1", is(expected.get(0).getId()));
        assertThat("2", is(expected.get(1).getId()));

    }

    @Test
    public void testUpdateNewSkill() {
        when(subSkillRepository.findById(anyString())).thenReturn(getSubSkill());


        adminService.updateNewSkill(getSubSkill());
        verify(subSkillRepository, times(1)).save(any(SubSkill.class));

    }

    @Test
    public void testAddNewSkill() {
        adminService.addNewSkill(getSubSkill());
        verify(subSkillRepository, times(1)).save(any(SubSkill.class));
    }

    @Test
    public void testReloadCache() {
        adminService.reloadCache();
        verify(guavaCacheInit, times(1)).getLoadingCache();
        verify(guavaCacheInit, times(1)).loadSkillGroup();
        verify(guavaCacheInit, times(1)).putSkillGroupCache(any(Map.class));

    }

    @Test
    public void testReloadSkillCache() {
        adminService.reloadSkillCache();
        verify(guavaCacheInit, times(1)).getSkillLoadingCache();
        verify(guavaCacheInit, times(1)).loadSkill();
        verify(guavaCacheInit, times(1)).putSkillCache(any(Map.class));

    }


    @Test
    public void testUpdateCertificate() {
        when(certificationRepository.findById(anyString())).thenReturn(getCertification());

        adminService.updateCertificate(getCertificationDomain());

        verify(certificationRepository, times(1)).save(any(Certification.class));


    }

    @Test
    public void postNewCertification() {

        when(certificationRepository.findAll()).thenReturn(getCertificationList());
        when(certificationRepository.findOne(anyString())).thenReturn(getCertification());

        adminService.postNewCertification(getUniqueCertificationDomain());

        verify(certificationRepository, times(1)).save((any(Certification.class)));

    }

    @Test
    public void skilluploadcsv() {
        when(subSkillRepository.count()).thenReturn((long) 0);
        // To make that skill doesn't exist in the db, sending null
        when(subSkillRepository.findBySubSkill(anyString())).thenReturn(null);
        String uploadString = SKILLSAMPLECSV;
        Reader inputString = new StringReader(uploadString);
        BufferedReader sampleInpurFileContentFromUser = new BufferedReader(inputString);

        boolean expecteed = adminService.skilluploadcsv(sampleInpurFileContentFromUser);

        assertThat(true, is(expecteed));
    }

    @Test
    public void certificateuploadcsv() {
        when(certificationRepository.count()).thenReturn((long) 0);

        when(certificationRepository.findBycertificationName(anyString())).thenReturn(null);

        String uploadString = CERTIFICATIONSAMPLECSV;
        Reader inputStringReader = new StringReader(uploadString);

        BufferedReader sampleInputFileContentFromUser = new BufferedReader(inputStringReader);

        boolean expected = adminService.certificateuploadcsv(sampleInputFileContentFromUser);
        assertThat(true, is(expected));


    }

    List<SubSkill> getAllSubSkill() {
        List<SubSkill> toReturnList = new ArrayList<>();
        toReturnList.add(getSubSkill());
        toReturnList.add(getSubSkill1());
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
                "practice");
    }

    private List<String> getSkillList() {
        List<String> toReturn = new ArrayList<>();
        toReturn.add("Java");
        toReturn.add("Python");
        return toReturn;
    }

    CertificationDomain getCertificationDomain() {
        return new CertificationDomain("1", "1", "AWS-Beginner", "AWS");
    }

    Certification getCertification() {
        return new Certification("1", "1", "AWS-Beginner", "AWS");
    }

    CertificationDomain getUniqueCertificationDomain() {
        return new CertificationDomain("3", "1", "Sample Certificate", "Sample");
    }

    // helper method to return the dummy data for testing
    public List<Certification> getCertificationList() {

        List<Certification> certifications = new ArrayList<>();

        Certification certification = new Certification("1", "1", "AWS-Beginner", "AWS");
        Certification certification1 = new Certification("2", "2", "AWS-Intermediate", "AWS");

        certifications.add(certification);
        certifications.add(certification1);

        return certifications;
    }
}
