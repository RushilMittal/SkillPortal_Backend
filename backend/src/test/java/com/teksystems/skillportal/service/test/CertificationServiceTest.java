//package com.teksystems.skillportal.service.test;
//
//import com.teksystems.skillportal.domain.CertificationDomain;
//import com.teksystems.skillportal.model.Certification;
//import com.teksystems.skillportal.repository.CertificationRepository;
//import com.teksystems.skillportal.service.CertificationService;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class CertificationServiceTest {
//
//    //mocks repo struct
//    @Mock
//    private CertificationRepository certificationRepository;
//
//    //mocks service and injects
//    @InjectMocks
//    private CertificationService certificationService;
//
//    //to initialise mockito setup
//    @Before
//    public void setup()
//    {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    //Test for getAllCertifications to check if all objects are being retrieved
//    @Test
//    public void testGetAllCertifications(){
//        List<Certification> certifications = new ArrayList<>();
//        Certification certification = new Certification();
//
//        certification.setCertificationName("AWS-Beginner");
//        certification.setInstitution("AWS");
//        certifications.add(certification);
//
//        certification.setCertificationName("AWS-Intermediate");
//        certification.setInstitution("AWS");
//        certifications.add(certification);
//
//        when(certificationRepository.findAll()).thenReturn(certifications);
//    }
//
//    //Test for postNewCertification to check selective post function
//    @Test
//    public void testPostNewCertification()
//    {
//        List<Certification> certifications = new ArrayList<>();
//        Certification certification = new Certification();
//        Certification certification1 = new Certification();
//
//
//        certification.setId("1105");
//        certification.setSkillId("2");
//        certification.setCertificationName("AWS-Beginner");
//        certification.setInstitution("AWS");
//        certifications.add(certification);
//
//        certification1.setId("1106");
//        certification1.setSkillId("2");
//        certification1.setCertificationName("AWS-Intermediate");
//        certification1.setInstitution("AWS");
//        certifications.add(certification1);
//
//        CertificationDomain certificationDomain = new CertificationDomain
//                ("1107", "2", "AWS-Pro", "AWS");
//
//        when(certificationRepository.findAll()).thenReturn(certifications);
//
//        this.certificationService.postNewCertification(certificationDomain);
//
//        verify(certificationRepository,times(1)).save(any(Certification.class));
//    }
//
//    @Test
//    public void testPostNewCertification1()
//    {
//        List<Certification> certifications = new ArrayList<>();
//        Certification certification = new Certification();
//        Certification certification1 = new Certification();
//
//        certification.setId("1105");
//        certification.setSkillId("2");
//        certification.setCertificationName("AWS-Beginner");
//        certification.setInstitution("AWS");
//        certifications.add(certification);
//
//        certification1.setId("1106");
//        certification1.setSkillId("2");
//        certification1.setCertificationName("AWS-Intermediate");
//        certification1.setInstitution("AWS");
//        certifications.add(certification1);
//
//        CertificationDomain certificationDomain = new CertificationDomain
//                ("1105", "2", "AWS-Beginner", "AWS");
//
//        when(certificationRepository.findAll()).thenReturn(certifications);
//
//        this.certificationService.postNewCertification(certificationDomain);
//
//        verify(certificationRepository,times(0)).save(any(Certification.class));
//    }
//}
