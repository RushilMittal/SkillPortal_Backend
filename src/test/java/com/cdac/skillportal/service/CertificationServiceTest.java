package com.cdac.skillportal.service;

import com.cdac.skillportal.domain.CertificationDomain;
import com.cdac.skillportal.model.Certification;
import com.cdac.skillportal.repository.CertificationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

public class CertificationServiceTest {

    //mocks repo struct
    @Mock
    private CertificationRepository certificationRepository;

    //mocks service and injects
    @InjectMocks
    private CertificationService certificationService;

    //to initialise mockito setup
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    final private String MOCKEDCERTIFICATIONNAME = "AWS-Beginner";
    final private String MOCKEDSEARCH = "AWS";


    //Unit Test case for getAllCertifications
    @Test
    public void testGetAllCertifications() {

        when(certificationRepository.findAll()).thenReturn(getCertificationList());

//        List<CertificationDomain> actual = getCertifdicationDomain();
        List<CertificationDomain> expected = certificationService.getAllCertifications();

        assertThat(2, is(expected.size()));
        assertThat("1", is(expected.get(0).getId()));
        assertThat(MOCKEDCERTIFICATIONNAME, is(expected.get(0).getCertificationName()));
        assertThat("2", is(expected.get(1).getId()));
    }

    public Certification getCertification() {
        return new Certification("1", "1", "AWS-Beginner", "AWS");
    }

    @Test
    public void testGetCertificationByName() throws Exception {

        when(certificationRepository.findBycertificationName(anyString())).thenReturn(getCertification());

        assertThat(MOCKEDCERTIFICATIONNAME, is(certificationService.getCertificationByName(MOCKEDCERTIFICATIONNAME).getCertificationName()));

    }

    @Test
    public void testSearchCertItems() {

        when(certificationRepository.findAll()).thenReturn(getCertificationList());


        List<CertificationDomain> expected = certificationService.searchCertItems(MOCKEDSEARCH);

        assertThat(2, is(expected.size()));

    }
    @Test
    public void testSearchCertItemsNotFound() {

        when(certificationRepository.findAll()).thenReturn(getCertificationList());


        List<CertificationDomain> expected = certificationService.searchCertItems("Java");

        assertThat(0, is(expected.size()));

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
