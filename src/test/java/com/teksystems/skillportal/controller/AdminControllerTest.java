//package com.teksystems.skillportal.controller;
//
//import com.teksystems.skillportal.model.SubSkill;
//import com.teksystems.skillportal.repository.SubSkillRepository;
//import com.teksystems.skillportal.service.AdminService;
//import com.teksystems.skillportal.service.TokenValidationService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import javax.servlet.http.HttpServletRequest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//public class AdminControllerTest {
//
//    @Mock
//    TokenValidationService tokenValidationService;
//    @Mock
//    AdminService adminService;
//
//    @Mock
//    SubSkillRepository subSkillRepository;
//
//    @InjectMocks
//    AdminController adminController;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//
//        when(tokenValidationService.ExtractEmployeeId(Mockito.any(HttpServletRequest.class))).thenReturn("101");
//        this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
//    }
//
//    @Test
//    public void getAllAdminSkill() throws Exception {
//        when(adminService.isAdmin(anyString())).thenReturn(true);
//        when(subSkillRepository.findAll()).thenReturn(getSubSkillList());
//
//        ResultActions resultAction = mockMvc.perform(
//                get("/admin/getAllAdminSkills")
//                        .header("Authorization", "empId:101")
//                        .header("Token","SAMPLE TOKEN")
//        );
//        resultAction.andExpect(status().isOk());
//    }
//
//    @Test
//    public void updateSkill() {
//    }
//
//    @Test
//    public void addNewSkill() {
//    }
//
//    @Test
//    public void postNewUniqueEntry() {
//    }
//
//    @Test
//    public void updateCertificate() {
//    }
//
//    @Test
//    public void uploadSkillCsv() {
//    }
//
//    @Test
//    public void uploadCertificateCsv() {
//    }
//
//    public List<SubSkill> getSubSkillList(){
//        List<SubSkill> toReturnList = new ArrayList<>();
//        toReturnList.add(getSubSkill());
//        toReturnList.add(getSubSkill1());
//        return toReturnList;
//
//    }
//
//    public SubSkill getSubSkill(){
//        return new SubSkill("1",
//                "Basic Java",
//                "Basic java Skills",
//                "Java",
//                "Programming Language",
//                "ADM");
//    }
//
//    public SubSkill getSubSkill1(){
//        return new SubSkill("2",
//                "Generics",
//                "Basic generics in Java",
//                "Java",
//                "skillGroup",
//                "practice");
//    }
//}