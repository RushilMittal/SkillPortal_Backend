package com.teksystems.skillportal.controller.test;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.teksystems.skillportal.domain.EmployeeSkillPlaceholderDomain;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.teksystems.skillportal.controller.EmployeeSkillController;
import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.service.EmployeeSkillService;


@RunWith(SpringRunner.class)

public class EmployeeSkillControllerTest {

	@Mock
    EmployeeSkillService employeeSkillService;
	
    @InjectMocks
    EmployeeSkillController controllerUnderTest;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();

    }

    @Test
    public void testGetSubSkillsBySkill() throws Exception{
        List<SubSkillDomain> subSkillDomains = Arrays.asList(new SubSkillDomain("1","Lambda","Lambda","AWS","Cloud","ADM",2),
                                                             new SubSkillDomain("2","EBS","EBS","AWS","Cloud","ADM",1)
                                                            );
        given(employeeSkillService.getAllUnassignedSubSkills("101","AWS")).willReturn(subSkillDomains);
        mockMvc.perform(get("/skill/getSubSkillsBySkill?empId=101&skillName=AWS"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",hasSize(2)))
            .andExpect(jsonPath("$[0].subSkill",is("Lambda")))
            .andExpect(jsonPath("$[1].subSkill",is("EBS")));

    }
    @Test
	public void testAdd() throws Exception {


		doNothing().when(employeeSkillService).addNew("103", "4", 3);

		mockMvc.perform(post("/skill/add?empId=103&subSkillId=4&rating=3"))
		.andExpect(status().isOk());
	}

	@Test
    public void testGetEmployeeSkillPlaceholder() throws Exception{

        given(employeeSkillService.getEmployeeSkillPlaceHolderDomain("101")).willReturn(
                new EmployeeSkillPlaceholderDomain(2,"Lambda",3, new int[]{0, 1, 1}));

        mockMvc.perform(get("/skill/getEmployeeSkillPlaceholder?empId=101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("numberOfSkillRated",is(2)))
                .andExpect(jsonPath("higestRatedSkill",is("Lambda")))
                .andExpect(jsonPath("highestRating",is(3)))
                .andExpect(jsonPath("lastUpdatedPeriod[0]",is(0)))
                .andExpect(jsonPath("lastUpdatedPeriod[1]",is(1)))
                .andExpect(jsonPath("lastUpdatedPeriod[2]",is(1)));
    }
    @Test
    public void testGetEmployeeSkills() throws Exception{
        SubSkillDomain temp = new SubSkillDomain("1","Lambda","Lambda","AWS","Cloud","ADM",2);
        SubSkillDomain temp2 = new SubSkillDomain("2","EBS","EBS","AWS","Cloud","ADM",1);

        List<EmployeeSkillDomain> employeeSkillDomains = Arrays.asList(
                new EmployeeSkillDomain("101",temp,3,new Date()),
                new EmployeeSkillDomain("101",temp2,2,new Date())

        );
        given(employeeSkillService.getAll("101")).willReturn(employeeSkillDomains);

        mockMvc.perform(get("/skill/getEmployeeSkills?empId=101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].employeeId",is("101")))
                .andExpect(jsonPath("$[0].rating",is(3)))
                .andExpect(jsonPath("$[1].employeeId",is("101")))
                .andExpect(jsonPath("$[1].rating",is(2)));



    }



//	@Test
//	public void testGetById() throws Exception {
//
//		SubSkillDomain skill1 = new SubSkillDomain("1","Perl","2",4);
//
//		given(employeeSkillService.getEmployeeSkillByObjectId("5aa76b7a13ff77043025183e")).willReturn(new EmployeeSkillDomain("101",skill1,3,new Date()));
//		given(employeeSkillService.getEmployeeSkillByObjectId("5aa76b7a13ff77043025183f")).willReturn(new EmployeeSkillDomain("102",skill1,3,new Date()));
//
//	    mockMvc.perform(get("/skill/getById?id=5aa76b7a13ff77043025183e"))
//	    .andExpect(status().isOk())
//        .andExpect(jsonPath("employeeId", is("101")));
//	}
//
//	@Test
//	public void testReadAll() throws Exception {
//
//		SubSkillDomain skill1 = new SubSkillDomain("1","Perl","2",4);
//		SubSkillDomain skill2 = new SubSkillDomain("4","CSS","3",6);
//
//		List<EmployeeSkillDomain> empDomains = Arrays.asList(new EmployeeSkillDomain("101",skill1,3,new Date()),
//				                                             new EmployeeSkillDomain("101",skill2,4,new Date()));
//
//		given(employeeSkillService.getAll("101")).willReturn(empDomains);
//
//		mockMvc.perform(get("/skill/getEmployeeSkills?empId=101"))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$", hasSize(2)))
//        .andExpect(jsonPath("$[0].employeeId", is("101")))
//        .andExpect(jsonPath("$[1].employeeId", is("101")));
//	}
//
//	@Test
//	public void testUpdate() throws Exception {
//
//        SubSkillDomain skill1 = new SubSkillDomain("4","CSS","3",5);
//
//		given(employeeSkillService.update("103", "4", 3)).willReturn(new EmployeeSkillDomain("103",skill1,3,new Date()));
//
//		mockMvc.perform(post("/skill/update?empId=103&subSkillId=4&rating=3"))
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("employeeId", is("103")))
//		.andExpect(jsonPath("rating", is(3)));
//	}
//

//	@Test
//	public void testDelete() throws Exception {
//
//
//		doNothing().when(employeeSkillService).deleteSubSkill("103", "4");
//
//		mockMvc.perform(post("/skill/delete?empId=103&subSkillId=4"))
//		.andExpect(status().isOk());
//	}
//
//
//	@Test
//	public void testGetBySkillIdCount() throws Exception {
//
//       given(employeeSkillService.getSubSkillCount("1")).willReturn(10);
//
//		mockMvc.perform(get("/skill/getSkillCount?skillId=1"))
//		.andExpect(status().isOk());
//	}
//
//
//	@Test
//	public void testGetBySubSkillIdCount() throws Exception {
//
//		given(employeeSkillService.getSubSkillCount("2")).willReturn(5);
//
//		mockMvc.perform(get("/skill/getSubSkillCount?subSkillId=2"))
//		.andExpect(status().isOk());
//	}
//
//	 @Test
//	    public void getBySubSkillIdExcept() throws Exception
//	    {
//	    	List<SubSkillDomain> skill1 = Arrays.asList(new SubSkillDomain("1","Perl","2",4),
//	    			                                    new SubSkillDomain("2","Java","2",7));
//	    	 given(employeeSkillService.getAllUnassignedSubSkills("101", "2")).willReturn(skill1);
//
//	         mockMvc.perform(get("/skill/getSubSkillsBySkillId?empId=101&skillId=2"))
//	                .andExpect(status().isOk())
//	                .andExpect(jsonPath("$", hasSize(2)))
//	                .andExpect(jsonPath("$[0].name", is("Perl")));;
//	    }
//
//

}
