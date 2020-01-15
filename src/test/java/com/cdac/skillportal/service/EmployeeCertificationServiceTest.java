package com.cdac.skillportal.service;

import com.cdac.skillportal.domain.CertificationDomain;
import com.cdac.skillportal.domain.EmployeeCertificationDomain;
import com.cdac.skillportal.domain.EmployeeCertificationPlaceholderDomain;
import com.cdac.skillportal.model.Certification;
import com.cdac.skillportal.model.EmployeeCertification;
import com.cdac.skillportal.repository.CertificationRepository;
import com.cdac.skillportal.repository.EmployeeCertificationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class EmployeeCertificationServiceTest {

    @Mock
    private EmployeeCertificationRepository employeeCertificationRepository;

    @Mock
    private CertificationRepository certificationRepository;

    @InjectMocks
    private EmployeeCertificationService employeeCertificationService;

    private Long DATECONSTANT = 1532677775148L;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEmployeeCertificationByEmployeeId() {
        when(employeeCertificationRepository.findByempId(anyString())).thenReturn(getEmployeeCertificationList());
        when(certificationRepository.findById("1")).thenReturn(certificate1());

        when(certificationRepository.findById("2")).thenReturn(certificate2());

        List<EmployeeCertificationDomain> expected = employeeCertificationService.getEmployeeCertificationByEmployeeId("101");

        assertThat(2, is(expected.size()));
    }

    @Test
    public void testAddNew() {
        //when(employeeCertificationRepository.save(any(EmployeeCertification.class))).thenReturn(getEmployeeCertification());

        employeeCertificationService.addNew(getEmployeeCertification().getEmployeeId(), getEmployeeCertification().getCertificationId(), getEmployeeCertification().getCertificationDate(), getEmployeeCertification().getCertificationValidityDate(), getEmployeeCertification().getCertificationNumber(), getEmployeeCertification().getCertificationUrl());
        verify(employeeCertificationRepository, times(1)).save(any(EmployeeCertification.class));
    }

    @Test
    public void testAddNewCertificate() {
        employeeCertificationService.addNewCertificate(getEmployeeCertificationDomain());

        verify(employeeCertificationRepository, times(1)).save(any(EmployeeCertification.class));
    }

    // Test when EmployeeCertificationPlaceholder when it returns list of size 2
    @Test
    public void testGetTwoEmployeeCertificationPlaceholderById() {
        when(employeeCertificationRepository.findByempId(anyString())).thenReturn(getEmployeeCertificationList());
        when(certificationRepository.findById("1")).thenReturn(certificate1());
        when(certificationRepository.findById("2")).thenReturn(certificate2());

        Calendar mockedCalendar = mock(GregorianCalendar.class);
        when(mockedCalendar.get(Calendar.YEAR)).thenReturn(2018);

        List<EmployeeCertificationPlaceholderDomain> expectedEmployeeCertificationPlaceholderDomain = employeeCertificationService.getEmployeeCertificationPlaceholderById("101");

        assertThat(2, is(expectedEmployeeCertificationPlaceholderDomain.size()));
        assertThat("2018", is(expectedEmployeeCertificationPlaceholderDomain.get(0).getYear()));
        assertThat("AWS-Beginner", is(expectedEmployeeCertificationPlaceholderDomain.get(0).getCertificationName()));
    }


    // Test when EmployeeCertificationPlaceholder when it returns list of size 2
    @Test
    public void testGetOneEmployeeCertificationPlaceholderById() {

        when(employeeCertificationRepository.findByempId(anyString())).thenReturn(Collections.singletonList(getEmployeeCertification()));
        when(certificationRepository.findById("1")).thenReturn(certificate1());


        Calendar mockedCalendar = mock(GregorianCalendar.class);
        when(mockedCalendar.get(Calendar.YEAR)).thenReturn(2018);

        List<EmployeeCertificationPlaceholderDomain> expectedEmployeeCertificationPlaceholderDomain = employeeCertificationService.getEmployeeCertificationPlaceholderById("101");

        assertThat(1, is(expectedEmployeeCertificationPlaceholderDomain.size()));
        assertThat("2018", is(expectedEmployeeCertificationPlaceholderDomain.get(0).getYear()));
        assertThat("AWS-Beginner", is(expectedEmployeeCertificationPlaceholderDomain.get(0).getCertificationName()));
    }


    // Test when EmployeeCertificationPlaceholder when it returns list of size 0
    @Test
    public void testGetNoEmployeeCertificationPlaceholderById() {
        when(employeeCertificationRepository.findByempId(anyString())).thenReturn(new LinkedList<EmployeeCertification>());

        when(certificationRepository.findById("1")).thenReturn(certificate1());
        Calendar mockedCalendar = mock(GregorianCalendar.class);
        when(mockedCalendar.get(Calendar.YEAR)).thenReturn(2018);

        List<EmployeeCertificationPlaceholderDomain> expectedEmployeeCertificationPlaceholderDomain = employeeCertificationService.getEmployeeCertificationPlaceholderById("101");

        assertThat(0, is(expectedEmployeeCertificationPlaceholderDomain.size()));

    }


    @Test
    public void testDateExtractor() {

        Calendar mockedCalendar = mock(GregorianCalendar.class);

        Date expected = employeeCertificationService.dateExtractor("2018-07-27", new GregorianCalendar());
        Date temp = new Date(DATECONSTANT);
        assertThat(temp.getDate(), is(expected.getDate()));
        assertThat(temp.getYear(), is(expected.getYear()));


    }

    //helper method to return the List of employee Certification
    private List<EmployeeCertification> getEmployeeCertificationList() {
        EmployeeCertification employeeCertification = new EmployeeCertification(
                "101",
                "1",
                new Date(DATECONSTANT),
                new Date(DATECONSTANT),
                1,
                "www.amazon.com");
        EmployeeCertification employeeCertification1 = new EmployeeCertification(
                "101",
                "2",
                new Date(DATECONSTANT),
                new Date(DATECONSTANT),
                2,
                "www.TEKsystems.com");

        List<EmployeeCertification> employeeCertificationList = new LinkedList<>();
        employeeCertificationList.add(employeeCertification);
        employeeCertificationList.add(employeeCertification1);
        return employeeCertificationList;
    }

    private EmployeeCertification getEmployeeCertification() {
        return new EmployeeCertification(
                "101",
                "1",
                new Date(DATECONSTANT),
                new Date(DATECONSTANT),
                1,
                "www.amazon.com");
    }

    private EmployeeCertificationDomain getEmployeeCertificationDomain() {

        CertificationDomain certificationDomainToPass = new CertificationDomain(
                "1",
                "1",
                "AWS-Beginner",
                "AWS");

        return new EmployeeCertificationDomain(
                "101",
                certificationDomainToPass,
                new Date(DATECONSTANT),
                new Date(DATECONSTANT),
                1,
                "www.amazon.com");
    }

    private Certification certificate1() {
        return new Certification(
                "1",
                "1",
                "AWS-Beginner",
                "AWS");
    }

    private Certification certificate2() {
        return new Certification(
                "2",
                "2",
                "AWS-Intermediate",
                "AWS");
    }
}