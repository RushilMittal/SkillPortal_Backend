package com.teksystems.skillportal.service.test;


import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.EmployeeSkillPlaceholderDomain;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.model.EmployeeSkill;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.EmployeeSkillRepository;
import com.teksystems.skillportal.repository.SubSkillRepository;
import com.teksystems.skillportal.service.EmployeeSkillService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
@SpringBootTest
public class EmployeeSkillServiceTest
{
    @Mock
    private EmployeeSkillRepository employeeSkillRepository;
    @Mock
    SubSkillRepository subskillRepository;
    @InjectMocks
    EmployeeSkillService employeeSkillService;
    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void getAllTest() throws Exception
    {
        List<EmployeeSkill> employeeSkillList = new ArrayList<EmployeeSkill>();
        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setEmpId("115");
        employeeSkill.setSubSkillId("8");
        employeeSkill.setRating(4);
        employeeSkillList.add(employeeSkill);
        EmployeeSkill employeeSkill2 = new EmployeeSkill();
        employeeSkill2.setEmpId("115");
        employeeSkill2.setSubSkillId("9");
        employeeSkill2.setRating(5);
        employeeSkillList.add(employeeSkill2);
        when(employeeSkillRepository.findByEmpId(anyString())).thenReturn(employeeSkillList);
        //when(employeeSkillRepository.findAll()).thenReturn(employeeSkillDomainList);
        SubSkill subSkill1 = new SubSkill();
        subSkill1.setId("8");
        subSkill1.setSubSkill("myskill1");
        subSkill1.setSubSkillDesc("myskill1");
        SubSkill subSkill2 = new SubSkill();
        subSkill2.setId("9");
        subSkill2.setSubSkill("myskill2");
        subSkill2.setSubSkillDesc("myskill2");
        when(subskillRepository.findById(anyString())).thenReturn(subSkill1).thenReturn(subSkill2);
        List<com.teksystems.skillportal.domain.EmployeeSkillDomain> result = employeeSkillService.getAll("115");
        assertEquals(2, result.size());
  }
    @Test
    public void testGetEmployeeSkillPlaceHolderDomain() throws Exception{

        List<EmployeeSkill> employeeSkillList = new ArrayList<EmployeeSkill>();
        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setEmpId("101");
        employeeSkill.setSubSkillId("8");
        employeeSkill.setRating(4);
        Date current = new Date();
        employeeSkill.setLastModifiedDate(current);
        employeeSkillList.add(employeeSkill);
        EmployeeSkill employeeSkill2 = new EmployeeSkill();
        employeeSkill2.setEmpId("101");
        employeeSkill2.setSubSkillId("9");
        employeeSkill2.setRating(5);
        employeeSkill2.setLastModifiedDate(current);
        employeeSkillList.add(employeeSkill2);
        when(employeeSkillRepository.findByEmpId(anyString())).thenReturn(employeeSkillList);
        //when(employeeSkillRepository.findAll()).thenReturn(employeeSkillDomainList);
        SubSkill subSkill1 = new SubSkill();
        subSkill1.setId("8");
        subSkill1.setSubSkill("Java");
        subSkill1.setSubSkillDesc("java");
        SubSkill subSkill2 = new SubSkill();
        subSkill2.setId("9");
        subSkill2.setSubSkill("C++");
        subSkill2.setSubSkillDesc("C++");
        when(subskillRepository.findById(anyString())).thenReturn(subSkill1).thenReturn(subSkill2);



        EmployeeSkill employeeSkill3 = new EmployeeSkill("101","8",4,current);

        when(employeeSkillRepository.findTopByEmpIdOrderByLastModifiedDateDesc("101")).thenReturn(employeeSkill3);


        EmployeeSkillPlaceholderDomain result = employeeSkillService.getEmployeeSkillPlaceHolderDomain("101");

        assertEquals(2,result.getNumberOfSkillRated());
        assertEquals("C++",result.getHigestRatedSkill());
        assertEquals(5,result.getHighestRating());
        assertEquals(0,result.getLastUpdatedPeriod()[0]);
        assertEquals(0,result.getLastUpdatedPeriod()[1]);
        assertEquals(0,result.getLastUpdatedPeriod()[2]);

    }
//
//
//    @Test
//    public void deleteSubSkillTest()
//    {
//        EmployeeSkill employeeSkill = new EmployeeSkill();
//        employeeSkill.setEmpId("115");
//        employeeSkill.setSubSkillId("8");
//        employeeSkill.setRating(3);
//        List<EmployeeSkill> employeeSkills = new ArrayList<>();
//        employeeSkills.add(employeeSkill);
//        when(this.employeeSkillRepository.findByEmpIdAndSubSkillId(anyString(),anyString())).thenReturn(employeeSkills);
//        employeeSkillService.deleteSubSkill("115","8");
//        verify(employeeSkillRepository, times(1)).delete(employeeSkill);
//    }
//    @Test
//    public void updateTest()
//    {
//        SubSkill subSkill=new SubSkill();
//        subSkill.setId("8");
//        subSkill.setName("subskill1");
//        subSkill.setSkillId("2");
//        when(subskillRepository.findById(anyString())).thenReturn(subSkill);
//        EmployeeSkillDomain result = employeeSkillService.update("114","8",5);
//        assertEquals("114", result.getEmployeeId());
//        assertEquals("8",result.getSubSkill().getId());
//        assertEquals("subskill1",result.getSubSkill().getName());
//        assertEquals("2",result.getSubSkill().getSkillId());
//        assertEquals(5, result.getRating());
//    }
//    @Test
//    public void addNewTest()
//    {
//        EmployeeSkill employeeSkill = new EmployeeSkill();
//        employeeSkill.setEmpId("114");
//        employeeSkill.setSubSkillId("8");
//        employeeSkill.setRating(5);
//        employeeSkillService.addNew("114","8",5);
//        verify(employeeSkillRepository,times(1)).save(any(EmployeeSkill.class));
//    }
//    @Test
//    public void testGetEmployeeSkillByObjectId() {
//        EmployeeSkill employeeSkill = new EmployeeSkill();
//        employeeSkill.setId("5a95294513ff77251c3abd59");
//        employeeSkill.setEmpId("101");
//        employeeSkill.setSubSkillId("4");
//        employeeSkill.setRating(4);
//        when(employeeSkillRepository.findById(anyString())).thenReturn(employeeSkill);
//        SubSkill subskill = new SubSkill();
//        subskill.setId("4");
//        subskill.setName("Java");
//        subskill.setSkillId("2");
//        when(subskillRepository.findById("4")).thenReturn(subskill);
//        EmployeeSkillDomain result= employeeSkillService.getEmployeeSkillByObjectId(anyString());
//        assertEquals("101",result.getEmployeeId());
//        assertEquals("4",result.getSubSkill().getId());
//        assertEquals("Java",result.getSubSkill().getName());
//        assertEquals("2",result.getSubSkill().getSkillId());
//        assertEquals(4,result.getRating());
//    }

}