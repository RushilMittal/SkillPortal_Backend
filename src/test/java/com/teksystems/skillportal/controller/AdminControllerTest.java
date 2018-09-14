package com.teksystems.skillportal.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.service.AdminService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTest {

    @Mock
    TokenValidationService tokenValidationService;
    @Mock
    AdminService adminService;

    @InjectMocks
    AdminController adminController;
    private MockMvc mockMvc;

    private Long DATECONSTANT = 1532677775148L;
    public static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn("101");
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void checkAdmninTest() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class))).thenReturn(true);

        boolean expected = adminController.checkAdmin(request,response);
        assertThat(true, is(expected));
    }
    @Test
    public void checkAdmninTestUnAuthorized() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn(null);
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class))).thenReturn(true);

        boolean expected = adminController.checkAdmin(request,response);
        assertThat(false, is(expected));
    }
    @Test
    public void checkAdmninTestNotAnAdmin() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class))).thenReturn(false);

        boolean expected = adminController.checkAdmin(request,response);
        assertThat(false, is(expected));
    }

    @Test(expected = IOException.class)
    public void checkAdmninTestIoException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(false);

        doThrow(IOException.class).when(response).sendError(anyInt(),anyString());
        adminController.checkAdmin(request,response);
    }


    @Test
    public void getAllAdminSkill() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(adminService.getAllAdminSkills()).thenReturn(getAllSubSkill());

        ResultActions resultAction = mockMvc.perform(
                get("/admin/getAllAdminSkills")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].subSkill", is("Basic Java")))
                .andExpect(jsonPath("$[1].subSkill", is("Generics")));
    }
    @Test
    public void getAllAdminSkillInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(adminService.getAllAdminSkills()).thenReturn(getAllSubSkill());

        ResultActions resultAction = mockMvc.perform(
                get("/admin/getAllAdminSkills")

        );
        resultAction.andExpect(status().isUnauthorized());
    }
    @Test
    public void getAllAdminSkillMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        when(adminService.getAllAdminSkills()).thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/getAllAdminSkills")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());
    }

    @Test
    public void updateSkill() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doNothing().when(adminService).updateNewSkill(any(SubSkill.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/updateNewSkill")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getSubSkill()))

        );
        resultAction.andExpect(status().isOk());
    }
    @Test
    public void updateSkillInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doNothing().when(adminService).updateNewSkill(any(SubSkill.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/updateNewSkill")

                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getSubSkill()))

        );
        resultAction.andExpect(status().isUnauthorized());
    }
    @Test
    public void updateSkillMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doThrow(MongoException.class).when(adminService).updateNewSkill(any(SubSkill.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/updateNewSkill")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getSubSkill()))

        );
        resultAction.andExpect(status().isInternalServerError());

    }


    @Test
    public void addNewSkill() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doNothing().when(adminService).addNewSkill(any(SubSkill.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/addNewSkill")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getSubSkill()))

        );
        resultAction.andExpect(status().isOk());
    }
    @Test
    public void addNewSkillInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doNothing().when(adminService).addNewSkill(any(SubSkill.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/addNewSkill")

                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getSubSkill()))

        );
        resultAction.andExpect(status().isUnauthorized());
    }
    @Test
    public void addNewSkillMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doThrow(MongoException.class).when(adminService).addNewSkill(any(SubSkill.class));


        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/addNewSkill")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getSubSkill()))

        );
        resultAction.andExpect(status().isInternalServerError());
    }

    @Test
    public void postNewUniqueEntry() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doNothing().when(adminService).postNewCertification(any(CertificationDomain.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/add_new")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getCertificationDomain()))

        );
        resultAction.andExpect(status().isOk());

    }
    @Test
    public void postNewUniqueEntryInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doNothing().when(adminService).postNewCertification(any(CertificationDomain.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/add_new")

                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getCertificationDomain()))

        );
        resultAction.andExpect(status().isUnauthorized());

    }
    @Test
    public void postNewUniqueEntryMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doThrow(MongoException.class).when(adminService).postNewCertification(any(CertificationDomain.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/add_new")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getCertificationDomain()))

        );
        resultAction.andExpect(status().isInternalServerError());

    }

    @Test
    public void updateCertificate() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doNothing().when(adminService).updateCertificate(any(CertificationDomain.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/updateCertificate")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getCertificationDomain()))

        );
        resultAction.andExpect(status().isOk());
    }
    @Test
    public void updateCertificateINvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doNothing().when(adminService).updateCertificate(any(CertificationDomain.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/updateCertificate")

                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getCertificationDomain()))

        );
        resultAction.andExpect(status().isUnauthorized());
    }
    @Test
    public void updateCertificateMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
                .thenReturn(true);

        doThrow(MongoException.class).when(adminService).updateCertificate(any(CertificationDomain.class));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                get("/admin/updateCertificate")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getCertificationDomain()))

        );
        resultAction.andExpect(status().isInternalServerError());
    }

    @Test
    public void uploadSkillCsv() throws Exception {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
//        when(tokenValidationService.validateAdminRole(any(HttpServletRequest.class),any(HttpServletResponse.class)))
//                .thenReturn(true);
//
////        FileInputStream fis = new FileInputStream("\\sa.csv");
////        BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
////
////        when(any(MultipartFile.class).getInputStream()).thenReturn(fis);
////
//////        when(adminController.getFileData(any(MultipartFile.class))).thenReturn(bf);
////
////        when(adminService.skilluploadcsv(bf)).thenReturn(true);
////
////
////
////        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);
////
////        HashMap<String, String> contentTypeParams = new HashMap<String, String>();
////        contentTypeParams.put("boundary", "265001916915724");
////        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);
////
////        ResultActions resultAction = mockMvc.perform(
////                post("/admin/uploadskillcsv")
////                        .header("Authorization", "empId:101")
////                        .content(multipartFile.getBytes())
////                        .contentType(mediaType)
////        );
////
////        MvcResult result = resultAction.andReturn();
////        System.out.println(result.getResponse().getStatus());
////        System.out.println(result.getResponse().getErrorMessage());
//
//        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
//        MockMultipartFile secondFile = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());
//        MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"json\": \"someValue\"}".getBytes());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/admin/uploadskillcsv")
//                .file(firstFile)
//                .file(secondFile)
//                .file(jsonFile)
//                .param("some-random", "4"))
//                .andExpect(status().is(200))
//                .andExpect(content().string("success"));


    }

    @Test
    public void uploadCertificateCsv() {
    }
    List<SubSkill> getAllSubSkill(){
        List<SubSkill> toReturnList = new ArrayList<>();
        toReturnList.add(getSubSkill());
        toReturnList.add(getSubSkill1());
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
                "practice");
    }
    CertificationDomain getCertificationDomain(){
        return new CertificationDomain("1","1","AWS-Beginner","AWS");
    }
}
