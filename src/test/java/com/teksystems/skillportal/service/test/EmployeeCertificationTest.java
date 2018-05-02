package com.teksystems.skillportal.service.test;


import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.model.EmployeeCertification;
import com.teksystems.skillportal.repository.EmployeeCertificationRepository;
import com.teksystems.skillportal.service.EmployeeCertificationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(JUnit4.class)
@SpringBootTest
public class EmployeeCertificationTest {

    @Mock
    EmployeeCertificationRepository employeeCertificateRepo;

    @InjectMocks
    EmployeeCertificationService employeeCertificationService;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addCertificate()
    {
        CertificationDomain certificationDomain = new CertificationDomain("a","2","abc","ABC");

        EmployeeCertificationDomain employeeCertificationDomain =new EmployeeCertificationDomain("101",certificationDomain, new Date(),new Date(),1011,"abc.xyz");
        this.employeeCertificationService.addNewCertificate(employeeCertificationDomain);

        verify(employeeCertificateRepo,times(1)).save(any(EmployeeCertification.class));

    }


}